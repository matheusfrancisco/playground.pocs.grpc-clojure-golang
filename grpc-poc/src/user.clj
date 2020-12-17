(ns user
  (:require [protojure.grpc.client.providers.http2 :as grpc.http2]
            [protojure.protobuf :as proto]
            [grpc_poc.proto.translate.Translation.client :as translation.client]
            [translator.proto.translation :as translation]))


(comment

  (def client @(grpc.http2/connect
                 {:uri "http://localhost:8080"
                  :idle-timeout -1}))

  @(translation.client/translate client
      {:target-language "de"
       :text "Hello, World"})
  )