(ns ksf-user-service.users-api
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [>! <! chan split]]
            [reagent.core :as reagent :refer [atom]]))

(def api-base-url "https://frontend-test.api.ksfmedia.fi")
(def api-login-endpoint (str api-base-url "/login"))
(def api-user-endpoint (str api-base-url "/users/me"))
(def api-user-address-endpoint (str api-user-endpoint "/address"))

(def auth-token (atom nil))
(def is-logged-in? (atom false))

(defn- set-token! [new-token]
  (reset! auth-token new-token)
  (reset! is-logged-in? true))

(defn handle-unsuccessful-request [http-req]
  "Handles HTTP errors.
  TODO: Implement it :~)"
  (go (let [response (<! http-req)
            status (:status response)]
        (cond
          (<= 400 status 500) (prn "Bad request")
          (>= status 500) (prn "Server error")))))

(defn- is-successful-request? [http-response]
  (= 200 (:status http-response)))

(defn make-http-req [http-fn url params]
  "Makes an HTTP request and splits the response in successful and failed responses.
  The successful request is returned, while the failed response is handled elsewhere."
  (let [http-req (http-fn url (assoc params :with-credentials? false))
        [successful-req failed-req] (split is-successful-request? http-req)]
    (handle-unsuccessful-request failed-req)
    (cljs.core.async/map (fn [res] (:body res)) [successful-req])))

(defn login [username password]
  (let [params {:json-params
                {:username username :password password}}]
    (go
      (let [response (<! (make-http-req http/post api-login-endpoint params))]
        (set-token! (:token response))))))

(defn fetch-user-info []
  (let [params {:headers {"Authorization" (str "Token " @auth-token)}}]
    (make-http-req http/get api-user-endpoint params)))

(defn update-address [new-address]
  (let [params {:headers {"Authorization" (str "Token " @auth-token)}
                :query-params {"new-address" new-address}}]
    (make-http-req http/put api-user-address-endpoint params)))
