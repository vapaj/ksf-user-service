(ns ksf-user-service.login-view
  (:require [ksf-user-service.util :as util]
            [ksf-user-service.user-api :as api]))

(def username (atom nil))
(def password (atom nil))

(defn username-input [username]
  (util/input "username" "text" "Användarnamn" username))

(defn password-input [password]
  (util/input "password" "password" "Lösenord" password))

(defn login-button []
  [:button
   {:type "button"
    :on-click #(api/login @username @password)
    :class "btn login-button"}
   "Logga in"])

(defn login-form []
  [:div
   (util/page-header "Logga in")
   [:form {:class "container login-form col-sm-6"}
    [:div.row
     [:div {:class "mx-auto col-sm-8 username-input"}
      [username-input username]]]
    [:div.row
     [:div {:class "mx-auto col-sm-8"}
      [password-input password]]]
    [:div.row
     [:div.col
      [login-button]]]]])

(defn login-view []
  [login-form])
