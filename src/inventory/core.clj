(ns inventory.core
  (:require [reitit.ring :as ring]
            [reitit.coercion.schema :as rcs]
            [muuntaja.core :as m]
            [reitit.ring.middleware.parameters :as rrmp]
            [reitit.ring.middleware.muuntaja :as rrmm]
            [reitit.ring.middleware.exception :as rrme]
            [reitit.ring.coercion :as rrc]
            [org.httpkit.server :refer [run-server]]))

(def handler
  (ring/ring-handler
    (ring/router
      [["/ok" {:name ::ok
               :get  {:handler (fn [_] {:status 200})}}]])
    {:data
     {:coercion rcs/coercion
      :muuntaja m/instance
      :middleware [rrmp/parameters-middleware
                   rrmm/format-negotiate-middleware
                   rrmm/format-response-middleware
                   rrme/exception-middleware
                   rrmm/format-request-middleware
                   rrc/coerce-exceptions-middleware
                   rrc/coerce-request-middleware
                   rrc/coerce-response-middleware]}}))

(defonce service (atom nil))

(defn stop-service!
  []
  (when-not (nil? service)
    (@service :timeout 100)
    (reset! service nil)))

(defn start-service!
  []
  (reset! service (run-server handler {:port (int 8888)})))

(defn -main
  []
  (start-service!))

(comment
  (stop-service!)
  (-main))