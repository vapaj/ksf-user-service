(ns ksf-user-service.user-view
  (:require [ksf-user-service.user :as user]
            [ksf-user-service.util :as util]))

(defn user-attribute [name value is-disabled?]
  [:div.row {:class "col-sm-7 mx-auto"}
    [:div.col {:class "col-sm-3 user-attribute"} name]
    [:div.col {:class "col-sm-7"}
     [:input {:type "text"
              :class "form-control"
              :value @value
              :on-change #(reset! value (-> % .-target .-value))
              :disabled is-disabled?}]]])

(defn user-details []
  [:div
   (util/page-header "Användare profil")
   (user-attribute "Namn" user/user-name true)
   [:div
    (conj
     (user-attribute "Adress" user/user-address false)
     [:div.attr-updated
      (if @user/user-address-saved "Uppdaterat!")])]
   (user-attribute "Email" user/user-email true)
   [:div.row
    [:div.col
     [:button {:class "btn update-profile-button"
               :on-click #(user/update-address!)} "Uppdatera"]]]])

(defn user-view []
  (user/initialize-user!)
  [user-details])
