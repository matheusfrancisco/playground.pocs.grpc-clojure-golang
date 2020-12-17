(ns grpc-poc.main
  (:gen-class)
  (:require [io.pedestal.http :as server]
            [io.pedestal.http.route :as route]
            [grpc-poc.service :as service]
            [taoensso.timbre :as log]
            [taoensso.timbre.appenders.core :as appenders]
            [clojure.tools.namespace.repl :as tn]
            [mount.core :as mount]))

#_(log/set-config!
    {:level :debug
     :ns-whitelist  ["translation.*"]
     :appenders {:println (appenders/println-appender {:stream :auto})}})


(mount/defstate translations-server
  :start (-> service/service
           server/create-server
           server/start)
  :stop (server/stop translations-server))


(defn go []
  (mount/start)
  :ready)

(defn stop []
  (mount/stop))

(defn reset []
  (mount/stop)
  (tn/refresh :after 'go))

(defonce runnable-service (server/create-server service/service))

(defn run-dev
  "The entry-point for 'lein run-dev'"
  [& args]
  (println "\nCreating your [DEV] server...")
  (-> service/service ;; start with production configuration
      (merge {:env :dev
              ;; do not block thread that starts web server
              ::server/join? false
              ;; Routes can be a function that resolve routes,
              ;;  we can use this to set the routes to be reloadable
              ::server/routes #(route/expand-routes (deref #'service/routes))
              ;; all origins are allowed in dev mode
              ::server/allowed-origins {:creds true :allowed-origins (constantly true)}
              ;; Content Security Policy (CSP) is mostly turned off in dev mode
              ::server/secure-headers {:content-security-policy-settings {:object-src "none"}}})
      ;; Wire up interceptor chains
      server/default-interceptors
      server/dev-interceptors
      server/create-server
      server/start))

(defn main
  "The entry-point for 'lein run'"
  [& args]
  (println "\nCreating your server...")
  (server/start runnable-service))

(defn stop-dev []
  (server/stop runnable-service))

(comment

  (main)
  (run-dev)
  (go)

  (stop)
  ;; -- REPL
  )
