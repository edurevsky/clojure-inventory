(ns inventory.middlewares
  (:require [inventory.db :as db]))

(defn db-middleware
  [handler]
  (fn [request]
    (-> request
        (assoc :db db/inventory)
        (handler))))