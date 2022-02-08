(ns ol.hikari-cp.ig
  (:require
    [integrant.core :as ig]
    [hikari-cp.core :as cp]
    ;[next.jdbc.connection :as connection]
    [ol.system.ig-utils :as ig-utils]
    )
  (:import (com.zaxxer.hikari HikariDataSource)))

(defmethod ig/init-key ::hikari-connection
  [_ pool-spec]
  ;(connection/->pool HikariDataSource pool-spec)
  (cp/make-datasource pool-spec))

(defmethod ig/suspend-key! ::hikari-connection [_ _])

(defmethod ig/halt-key! ::hikari-connection
  [_ conn]
  (cp/close-datasource conn))

(defmethod ig/resume-key ::hikari-connection
  [key opts old-opts old-impl]
  (ig-utils/resume-handler key opts old-opts old-impl))





(comment
  ;; pool-spec-example
  {:auto-commit        true
   :read-only          false
   :connection-timeout 30000
   :validation-timeout 5000
   :idle-timeout       600000
   :max-lifetime       1800000
   :minimum-idle       10
   :maximum-pool-size  10
   :pool-name          "ds-pool"
   :username           "root"
   :password           "123456"
   :jdbc-url           "jdbc:mysql://1270.0.01:3306/test_db?characterEncoding=utf8"
   :driver-class-name  "com.mysql.jdbc.Driver"
   ;; :adapter            "mysql"
   ;; :database-name      "test_db"
   ;; :server-name        "192.210.170.12"
   ;; :port-number        3306
   :register-mbeans    false}

  )
