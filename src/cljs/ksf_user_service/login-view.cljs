(ns ksf-user-service.login-view
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent :refer [atom]]
            [ksf-user-service.util :as util]
            [ksf-user-service.user-api :as api]))

(def username (atom nil))
(def password (atom nil))
(def is-login-success? (atom true))

(defn login [username password]
  (go
    (let [{status :status} (<! (api/login username password))]
      (if (= status 200)
        (reset! is-login-success? true)
        (reset! is-login-success? false)))))

(defn username-input [username]
  (util/input "username" "text" "Användarnamn" username))

(defn password-input [password]
  (util/input "password" "password" "Lösenord" password))

(defn login-text-input-row [])

(defn login-button []
  [:button
   {:type "button"
    :on-click #(login @username @password)
    :class "btn login-button"}
   "Logga in"])

(defn validate-input [[elem attrs]]
  "Changes class of input fields according to `is-login-success`."
  (if @is-login-success?
    [elem attrs]
    [elem (update attrs :class #(str % " is-invalid"))]))

(defn login-form []
  [:div
   (util/page-header "Logga in")
   [:form {:class "container login-form col-sm-6"}
    [:div.row {:class "username-input-row"}
     [:div#username-input {:class "mx-auto col-sm-8"}
      (validate-input (username-input username))]]
    [:div.row
     [:div#password-input {:class "mx-auto col-sm-8"}
      (validate-input (password-input password))]]
    [:div.row
     [:div.col
      [login-button]]]]])

(defn login-view []
  [login-form])
