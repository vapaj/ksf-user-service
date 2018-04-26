(ns ksf-user-service.user-view
  (:require [ksf-user-service.user :as user]
            [ksf-user-service.util :as util]
            [goog.functions]))

(def update-address! (goog.functions.debounce #(user/update-address! %) 300))

(defn handle-address-changes [input-value]
  (let [new-address (-> input-value .-target .-value)]
    (reset! user/user-address new-address)
    (update-address! new-address)))

(defn user-attribute-input [name value & [optional-attrs]]
  (let [input-default-attrs {:type "text"
                             :class "form-control"
                             :value @value
                             :disabled true}]
    [:div.row {:class "col-sm-7 mx-auto"}
     [:div.col {:class "col-sm-3 user__user-attribute"} name]
     [:div.col {:class "col-sm-7"}
      [:input (merge input-default-attrs optional-attrs)]]]))

(defn address-input []
  (let [address-input-attrs {:disabled false :on-change handle-address-changes}]
    [:div
     (conj
      (user-attribute-input "Adress" user/user-address address-input-attrs)
      [:div {:class "user__user-attribute user__attr-updated"}
       (if @user/user-address-saved "✓")])]))

(defn user-details []
  [:div
   (util/page-header "Användare profil")
   (user-attribute-input "Namn" user/user-name)
   (address-input)
   (user-attribute-input "Email" user/user-email)])

(defn user-view []
  (user/initialize-user!)
  [user-details])
