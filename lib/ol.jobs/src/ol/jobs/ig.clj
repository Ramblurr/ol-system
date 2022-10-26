(ns ol.jobs.ig
  (:require [ol.jobs-util :as jobs]
            [integrant.core :as ig]))

(defmethod ig/init-key :ol.ig/jobs
  [_ system]
  (jobs/start-jobs job-defs system))

(defmethod ig/halt-key! :ol.ig/jobs
  [_ _]
  (jobs/stop-all-schedules))
