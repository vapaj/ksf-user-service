(ns ksf-user-service.user-api
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<! chan split pipe]]
            [reagent.core :as reagent :refer [atom]]))

(def api-base-url "https://frontend-test.api.ksfmedia.fi")
(def api-login-endpoint (str api-base-url "/login"))
(def api-user-endpoint (str api-base-url "/users/me"))
(def api-user-address-endpoint (str api-user-endpoint "/address"))

(def auth-token (atom nil))

(defn- set-token! [new-token]
  (reset! auth-token new-token))

(defn make-http-req [http-fn url params]
  "Makes an HTTP request."
  (http-fn url (assoc params :with-credentials? false)))

(defn- is-successful-request? [http-response]
  (= 200 (:status http-response)))

(defn- filter-successful-requests [http-req]
  (let [[successful-req failed-req] (split is-successful-request? http-req)]
    (cljs.core.async/map #(:body %) [successful-req])))

(defn login [username password]
  "Logs in and sets `auth-token` if success. Returns `http-req` to the caller.
  NOTE: For some reason `http-req` needs to be defined inside `go` block,
  so pipe it to `http-req-to-caller`."
  (let [params {:json-params
                {:username username :password password}}
        http-req-to-caller (chan)]
    (go
      (let [http-req (make-http-req http/post api-login-endpoint params)
            response (<! http-req)]
        (do
          (pipe http-req http-req-to-caller)
          (if (is-successful-request? response) (set-token! (-> response :body :token))))))
    http-req-to-caller))

(defn fetch-user-info []
  (let [params {:headers {"Authorization" (str "Token " @auth-token)}}]
    (->
     (make-http-req http/get api-user-endpoint params)
     (filter-successful-requests))))

(defn update-address [new-address]
  (let [params {:headers {"Authorization" (str "Token " @auth-token)}
                :query-params {"new-address" new-address}}]
    (->
     (make-http-req http/put api-user-address-endpoint params)
     (filter-successful-requests))))
