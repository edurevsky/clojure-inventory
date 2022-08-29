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

(defn handle-get-item-by-id
  [{:keys [db path-params]}]
  (let [id (parse-uuid (:id path-params))]
    (if-let [item (get @db id)]
      {:status 200 :body item}
      {:status 404})))