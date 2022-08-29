(ns inventory.routes)

(def routes
  [["/ok" {:name ::ok
           :get  {:handler (fn [_] {:status 200})}}]])