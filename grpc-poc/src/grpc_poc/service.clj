(ns grpc-poc.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [ring.util.response :as ring-resp]
            [grpc_poc.proto.translate.Translation.server :as grpc]
            #_[protojure.pedestal.interceptors.grpc :as a]
            [protojure.pedestal.core :as protojure.pedestal]
            [protojure.pedestal.routes :as proutes]
            [protojure.grpc.status :as status]
            [grpc-poc.core :as core]
            [clojure.java.io :as io]
            [clojure.core.async :as async]))

(defn about-page
  [request]
  (ring-resp/response
    (format (-> "version-info.txt" io/resource slurp)
      (clojure-version)
      (route/url-for ::about-page)
      "test")))

(def common-interceptors [(body-params/body-params) http/html-body])


(def routes #{["/" :get (conj common-interceptors `about-page)]})

(deftype TranslationService []
  grpc/Service
  ;;GRPC parameters are associated with the request-map as
  ;; :grpc-params, similar to how the pedestal body-param module
  ;; injects other type like :json-params :edn-params, etc..
  (translate
    [this {{:keys [text target-language] :as req} :grpc-params :as request}]
    (if-let [_ (core/supported-languages (keyword target-language))]
      {:status 200
       :body (core/translate req)
       :content-type "application/grpc"}
      {:status 200
       :content-type "application/grpc"
       :body (status/error :invalid-argument
                (format "language not supported - %s" target-language))}))

  #_(translations
    [this {{:keys [text] :as req} :grpc-params :as request}]
    (let [resp-chan (:grpc-out request)]
          (async/thread
            (doseq [lang core/supported-languages]
              (async/>!! resp-chan
                         (core/translate (:text text :target-language (name lang)))))
            (async/close! resp-chan))
          {:status 200
           :body resp-chan}
          ))
  )

(def grpc-routes
  (-> routes
      (into (proutes/->tablesyntax
              {:rpc-metadata     grpc/rpc-metadata
               :interceptors     common-interceptors
               :callback-context (TranslationService.)})))
  )


(def service {:env :prod
              ::http/routes grpc-routes
              #_#_::http/allowed-origins "S"
              ;; -- PROTOC-GEN-CLOJURE --
              ;; We override the chain-provider with one provided by protojure.protobuf
              ;; and based on the Undertow webserver.  This provides the proper support
              ;; for HTTP/2 trailers, which GRPCs rely on.  A future version of pedestal
              ;; may provide this support, in which case we can go back to using
              ;; chain-providers from pedestal.
              ::http/type protojure.pedestal/config
              ::http/chain-provider protojure.pedestal/provider

              ;;::http/host "localhost"
              ::http/port 8080})



(comment

  (in-ns 'protojure.pedestal.interceptors.grpc)

  )