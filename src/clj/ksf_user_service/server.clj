(ns ksf-user-service.server
  (:require [ksf-user-service.handler :refer [app]]
            [config.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

 (defn -main [& args]
   (let [env-port (or (env :port) "3000")]
     (if (= (type env-port) java.lang.Long)
       (run-jetty app {:port env-port :join? false})
       (run-jetty app {:port (Integer/parseInt env-port) :join? false}))))
