(ns ksf-user-service.prod
  (:require [ksf-user-service.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
