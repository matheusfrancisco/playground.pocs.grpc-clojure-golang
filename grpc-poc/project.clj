(defproject grpc-poc "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.10.0"]
                 [io.pedestal/pedestal.service "0.5.7"]

                 [mount "0.1.16"]
                 [org.clojure/tools.namespace "1.0.0"]

                 [protojure "1.5.2"]
                 [protojure/google.protobuf "0.9.1"]

                 [org.eclipse.jetty.http2/http2-client "9.4.20.v20190813"]
                 [org.eclipse.jetty/jetty-alpn-java-client "9.4.28.v20200408"]

                 ;; -- Jetty Client Dep --
                 [org.ow2.asm/asm "8.0.1"]

                 ;; Include Undertow for supporting HTTP/2 for GRPCs
                 [io.undertow/undertow-core "2.0.28.Final"]
                 [io.undertow/undertow-servlet "2.0.28.Final"]
                 ;; logging
                 [com.fzakaria/slf4j-timbre "0.3.17"]
                 [com.taoensso/timbre "4.10.0"]

                 [ch.qos.logback/logback-classic "1.2.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.30"]
                 [org.slf4j/jcl-over-slf4j "1.7.30"]
                 [org.slf4j/log4j-over-slf4j "1.7.30"]

                 ;; -- amazonica --
                 [amazonica "0.3.153"]
                 ;; And of course, protobufs
                 [com.google.protobuf/protobuf-java "3.12.2"]

                 ]

  :resource-paths ["config", "resources"]
  :profiles {:dev {:dependencies [[io.pedestal/pedestal.service-tools "0.5.7"]]}
             :uberjar {:aot [grpc-poc.main]}}
  :repl-options {:init-ns grpc-poc.main})
