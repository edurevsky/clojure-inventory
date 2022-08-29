(ns inventory.routes
  (:require [inventory.handlers :as handlers]
            [inventory.middlewares :as middlewares]
            [schema.core :as s]
            [inventory.models :as models]))

(def routes
  [["/ok" {:name ::ok
           :get  {:handler (fn [_] {:status 200})}}]
   ["/inventory" {:name ::inventory
                  :get  {:middleware [[middlewares/db-middleware]]
                         :responses  {200 {:body {:inventory-items s/Int
                                                  :inventory-value s/Num}}}
                         :handler    handlers/handle-inventory-info}}]
   ["/items" {:name ::items
              :post {:middleware [[middlewares/db-middleware]]
                     :parameters {:body models/NewItem}
                     :responses  {201 {:body models/Item}}
                     :handler    handlers/handle-add-item}}]])