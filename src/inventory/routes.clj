(ns inventory.routes
  (:require [inventory.handlers :as handlers]
            [inventory.middlewares :as middlewares]))

(def routes
  [["/ok" {:name ::ok
           :get  {:handler (fn [_] {:status 200})}}]
   ["/inventory" {:name ::inventory
                  :get  {:middleware [[middlewares/db-middleware]]
                         :handler handlers/handle-inventory-info}}]])