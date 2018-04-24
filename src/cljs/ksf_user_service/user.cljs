(ns ksf-user-service.user
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [>! <! chan]]
            [reagent.core :as reagent :refer [atom]]
            [ksf-user-service.user-api :as api]))

(def user-id (atom nil))
(def user-name (atom nil))
(def user-email (atom nil))
(def user-address (atom nil))
(def user-address-saved (atom false))

(defn- update-user-address-saved [_ _ old-addr new-addr]
  "Hides `user-address-saved` on `user-address` changes."
  (if (not= old-addr new-addr)
    (reset! user-address-saved false)))

(add-watch user-address :user-address-watch update-user-address-saved)

(defn- set-user! [{:keys [id name email address]}]
  (reset! user-id id)
  (reset! user-name name)
  (reset! user-email email)
  (reset! user-address address))

(defn initialize-user! []
  (go
    (let [user (<! (api/fetch-user-info))]
      (set-user! user))))

(defn update-address! [new-address]
  (go
    (let [user (<! (api/update-address new-address))]
      (set-user! user)
      (reset! user-address-saved true))))
