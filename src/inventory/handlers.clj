(ns inventory.handlers
  (:require [inventory.logic :as logic]))

(defn handle-inventory-info
  [{:keys [db]}]
  {:status 200
   :body   (logic/inventory-info @db)})

(defn handle-add-item
  [{:keys [db body-params]}]
  (let [item (assoc body-params :id (random-uuid))]
    (swap! db logic/add-item item)
    {:status 201
     :body   (get @db (:id item))}))