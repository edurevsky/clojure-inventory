(ns inventory.logic)

(defn add-item
  [inventory item]
  (assoc inventory (:id item) item))

(defn remove-item
  [inventory item-id]
  (dissoc inventory item-id))

(defn item-total
  [item]
  (* (:price item) (:quantity item)))

(defn item-with-total-price
  [inventory item-id]
  (let [item (get inventory item-id)
        total (item-total item)]
    (-> item
        (assoc :total-price total))))

(defn inventory-info
  [inventory]
  (let [each-item (vals inventory)
        each-total (map item-total each-item)
        total (reduce + each-total)]
    {:inventory-items (count inventory)
     :inventory-value total}))