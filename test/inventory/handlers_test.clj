(ns inventory.handlers-test
  (:require [clojure.test :refer :all]
            [inventory.handlers :refer :all]
            [schema.core :as s]
            [inventory.models :as m]))

(deftest handle-get-item-by-id-test
  (testing "Should return map with status 404."
    (let [mocked-request {:db          (atom {})
                          :path-params {:id "id"}}
          expected-status 404]
      (is (:status (handle-get-item-by-id mocked-request) expected-status))))
  (testing "Should return map with status 200 and body that is valid as an Item."
    (let [gen-id (random-uuid)
          mocked-request {:db          (atom {gen-id {:id gen-id :name "name" :price 0.1 :quantity 1}})
                          :path-params {:id (str gen-id)}}
          resp (handle-get-item-by-id mocked-request)
          expected-status 200]
      (is
        (and
          (= (:status resp) expected-status)
          (s/validate m/Item (:body resp)))))))

(deftest handle-delete-item-by-id-test
  (testing "Should return map with status 404."
    (let [mocked-request {:db          (atom {})
                          :path-params {:id "id"}}
          resp (handle-delete-item-by-id mocked-request)
          expected-status 404]
      (is (= (:status resp) expected-status))))
  (testing "Should return map with status 204."
    (let [gen-id (random-uuid)
          mocked-request {:db          (atom {gen-id {:id gen-id :name "name" :price 0.1 :quantity 1}})
                          :path-params {:id (str gen-id)}}
          resp (handle-delete-item-by-id mocked-request)
          expected-status 204]
      (is (= (:status resp) expected-status)))))