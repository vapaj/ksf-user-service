(ns ^:figwheel-no-load ksf-user-service.dev
  (:require
    [ksf-user-service.core :as core]
    [devtools.core :as devtools]))

(devtools/install!)

(enable-console-print!)

(core/init!)
