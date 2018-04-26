(ns ksf-user-service.util)

(defn input [id type placeholder value]
  [:input {:id id
          :type type
          :placeholder placeholder
          :class "form-control"
          :on-change #(reset! value (-> % .-target .-value))}])

(defn page-header [header-text]
  [:div.row {:class "page-header__container"}
   [:div.col
    [:h2.ksf-heading
     [:span.page-header__header header-text]]]])
