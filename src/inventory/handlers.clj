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
     :body   (logic/item-with-total-price @db (:id item))}))

(defn handle-get-item-by-id
  [{:keys [db path-params]}]
  (let [id (parse-uuid (:id path-params))]
    (if (contains? @db id)
      {:status 200 :body (logic/item-with-total-price @db id)}
      {:status 404})))

(defn handle-delete-item-by-id
  [{:keys [db path-params]}]
  (let [id (parse-uuid (:id path-params))]
    (if (not (contains? @db id))
      {:status 404}
      (do
        (swap! db logic/remove-item id)
        {:status 204}))))

(defn handle-item-purchase
  [{:keys [db body-params]}]
  (try
    (swap! db logic/item-purchase (update body-params :item-id parse-uuid))
    {:status 204}
    (catch Exception _
      {:status 400
       :body {:message "The product does not have the requested amount to be purchased."}})))