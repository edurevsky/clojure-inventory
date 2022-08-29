(ns inventory.handlers
  (:require [inventory.logic :as logic]))

(defn handle-inventory-info
  [{:keys [db]}]
  {:status 200
   :body (logic/inventory-info @db)})