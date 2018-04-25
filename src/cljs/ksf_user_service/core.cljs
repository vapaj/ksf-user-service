(ns ksf-user-service.core
  (:require-macros [cljs.core.async.macros :refer [go]])
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [ksf-user-service.user-api :as api]
              [ksf-user-service.login-view :as login]
              [ksf-user-service.user-view :as user]))

(defn user-management-page []
  [:div
   [:div.container
    (if @api/auth-token
      [user/user-view]
      [login/login-view])]])

(defonce page (atom #'user-management-page))

(defn current-page []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'user-management-page))

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "user-management-container")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
