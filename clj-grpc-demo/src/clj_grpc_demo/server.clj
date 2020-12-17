(ns clj-grpc-demo.server
  (:gen-class)
  (:import [io.grpc.scraper
            AuthResponse]
           [io.grpc Server ServerBuilder]))

(defonce server nil)

(def server-port 8080)

(defn create-service []
  (proxy [io.grpc.scraper.ScraperGrpc$ScraperImplBase] []

    (authenticate [^io.grpc.scraper.AuthRequest request
                   ^io.grpc.scraper.AuthResponse response]
      (let [branch (.getAccountBranch request)
            account (.getAccountType  request)
            oper (.getOper request)
            password (.getPassword request)
            builder  (-> (AuthResponse/newBuilder)
                         (.setToken (str "authtokezinho")))]
        (prn branch, account, oper, password, "=======Auth informations")
        (.onNext response (.build builder))
        (.onCompleted response)
        ))
    (accounts [^io.grpc.scraper.AccountsRequest request
               ^io.grpc.scraper.AccountsResponse response]
      (prn "Opaaaa" request))
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

(comment
  (main)
  (stop-server)
  )



