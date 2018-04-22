(ns ksf-user-service.util)

(defn input [id type placeholder value]
 (fn []
   [:input {:id id
            :type type
            :placeholder placeholder
            :class "form-control"
            :on-change #(reset! value (-> % .-target .-value))}]))

(defn page-header [header-text]
  [:div.row {:class "page-header"}
   [:div.col
    [:h2.ksf-heading
     [:span.page-header header-text]]]])
