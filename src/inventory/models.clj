(ns inventory.models
  (:require [schema.core :as s]))

(def Item
  {:id       s/Uuid
   :name     s/Str
   :quantity s/Int
   :price    s/Num})