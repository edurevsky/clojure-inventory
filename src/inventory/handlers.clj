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

(defn- item-purchase
  [db body-params]
  (let [result (logic/item-purchase @db body-params)]
    (if (not (:success? result))
      {:status 400
       :body   {:message "ERROR"}}
      (do
        (reset! db (:inventory result))
        {:status 204}))))

(defn handle-item-purchase
  [{:keys [db body-params]}]
  (let [body-params (update body-params :item-id parse-uuid)]
    (if (contains? @db (:item-id body-params))
      (item-purchase db body-params)
      {:status 400
       :body   {:message "Item not found."}})))

(defn handle-update-item-by-id
  [{:keys [db path-params body-params]}]
  (let [id (parse-uuid (:id path-params))]
    (if (contains? @db id)
      (do
        (swap! db logic/item-update id body-params)
        {:status 200
         :body   (logic/item-with-total-price @db id)})
      {:status 404})))