(ns inventory.logic)

(defn add-item
  "Adds the item in the inventory map."
  [inventory item]
  (assoc inventory (:id item) item))

(defn remove-item
  "Removes the item of the inventory map."
  [inventory item-id]
  (dissoc inventory item-id))

(defn item-total
  "Returns the item price times the item quantity."
  [item]
  (* (:price item) (:quantity item)))

(defn item-with-total-price
  "Returns the item with the total price."
  [inventory item-id]
  (let [item (get inventory item-id)
        total (item-total item)]
    (-> item
        (assoc :total-price total))))

(defn inventory-info
  "Returns a map containing the quantity of the inventory items
   and the total value of the inventory."
  [inventory]
  (let [each-item (vals inventory)
        each-total (map item-total each-item)
        total (reduce + each-total)]
    {:inventory-items (count inventory)
     :inventory-value total}))

(defn high-amount?
  [item-quantity purchase-amount]
  (< item-quantity purchase-amount))

(defn item-purchase
  "Returns the updated inventory with the updated Item."
  [inventory purchase]
  (let [id (:item-id purchase)
        item (get inventory id)
        purchase-amount (:amount purchase)
        item-quantity (:quantity item)]
    (if (high-amount? item-quantity purchase-amount)
      {:inventory inventory :success? false}
      {:inventory (update-in inventory [id :quantity] - purchase-amount) :success? true})))

(defn item-update
  "Returns the inventory with the updated Item."
  [inventory item-id to-update]
  (update inventory item-id merge to-update))