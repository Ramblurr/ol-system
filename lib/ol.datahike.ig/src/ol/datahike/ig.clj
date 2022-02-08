(ns ol.datahike.ig
  (:require [clojure.tools.logging :as log]
            [integrant.core :as ig]
            [clojure.java.io :as io]
            [datahike.api :as d]))


(defn file->datahike-db-uri [file]
  (let [f (io/file file)
        _ (io/make-parents f)]
    (str "datahike:" (io/as-url f))))


(defmethod ig/init-key ::database
  [_ {:keys [db-uri db-file initial-tx schema-on-read temporal-index]
      :as   config}]
  (if-some [uri (or db-uri (file->datahike-db-uri db-file))]
    (let [args (cond-> [uri]
                       initial-tx (conj :initial-tx initial-tx)
                       schema-on-read (conj :schema-on-read schema-on-read)
                       temporal-index (conj :temporal-index temporal-index))]
      (log/debug "Creating Datahike DB database.")
      (when-not (d/database-exists? uri)
        (apply d/create-database args))
      (assoc config :db-uri uri))
    (log/error "No uri provided for database")))

(defmethod ig/halt-key! ::database [_ {:keys [db-uri delete-on-halt?]}]
  (when delete-on-halt?
    (log/debug "Deleting Datahike DB database.")
    (d/delete-database db-uri)))

(defmethod ig/init-key ::connection [_ {:keys [db-uri db-config]}]
  (if-some [uri (or db-uri (:db-uri db-config))]
    (do
      (log/debug "Creating Datahike DB connection.")
      (d/connect uri))
    (log/error "No db-uri provided for Datahike connection")))

(defmethod ig/halt-key! ::connection [_ connection]
  (if connection
    (do
      (log/debug "Releasing Datahike DB connection.")
      (d/release connection))
    (log/warn "No Datahike connection to release!")))


