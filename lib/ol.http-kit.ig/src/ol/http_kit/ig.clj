(ns ol.http-kit.ig
  (:require
    [org.httpkit.server :as http-kit]
    [integrant.core :as ig]
    ))

(defmethod ig/init-key ::listener [_ {:keys [handler port]}]
  (let [server-stop (http-kit/run-server handler {:port port})]
    server-stop))

(defmethod ig/halt-key! ::listener [_ server-stop]
  (server-stop))
