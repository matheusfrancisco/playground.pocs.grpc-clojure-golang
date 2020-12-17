(ns clj-grpc-demo.server2
  (:gen-class)
  (:import [io.grpc.scraper TranslationResponse]
           [io.grpc Server ServerBuilder]))

(defonce server nil)

(def server-port 8080)

(defn create-service []
  (proxy [io.grpc.translate.TranslationGrpc$TranslationImplBase] []
    (translate [^io.grpc.translate.TranslationRequest req
                ^io.grpc.translate.TranslationResponse res]
      (let [result res
            request req]
        (prn "entrou")

        ))
    ))

(defn start-server [port]
  (let [server (-> (ServerBuilder/forPort port)
                   (.addService (create-service))
                   (.build)
                   (.start))]
    (-> (Runtime/getRuntime)
        (.addShutdownHook
          (Thread. (fn []
                     (if (not (nil? server))
                       (.shutdown server))))))
    (alter-var-root #'server (constantly server))
    server))

(defn stop-server
  []
  (.shutdown server))

(defn main
  [& args]
  (let [port (or (first args) server-port)]
    (start-server port)))

(main)



