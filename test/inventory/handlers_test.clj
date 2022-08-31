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

(deftest handle-item-purchase-test
  (let [id #uuid"b894ffbc-f586-47a2-a7ee-77ad775d96b4"
        item {:id id :name "?" :price 0.01 :quantity 2321}
        initial-value {id item}]
    (testing "That returns a map with status 400 when requesting with an non existing id."
      (let [mocked-request {:db          (atom initial-value)
                            :body-params {:item-id (str (random-uuid)) :amount 1}}
            resp (handle-item-purchase mocked-request)]
        (is (= 400
               (:status resp)))))
    (testing "That returns a map with status 400 when requesting an amount that is higher than the available."
      (let [mocked-request {:db          (atom initial-value)
                            :body-params {:item-id (str id) :amount 2322}}
            resp (handle-item-purchase mocked-request)]
        (is (= 400
               (:status resp)))))
    (testing "That returns a map with status 204 when requesting an amount that is the same as the available."
      (let [mocked-request {:db          (atom initial-value)
                            :body-params {:item-id (str id) :amount 2321}}
            resp (handle-item-purchase mocked-request)]
        (is (= 204
               (:status resp)))))
    (testing "That returns a map with status 204 when requesting an amount that is below the available."
      (let [mocked-request {:db          (atom initial-value)
                            :body-params {:item-id (str id) :amount 2320}}
            resp (handle-item-purchase mocked-request)]
        (is (= 204
               (:status resp))))))
  (testing "That returns a map with status 204."
    (let [id #uuid"fdc8d2eb-ea52-4cf0-81c2-b5392afe822f"
          item {:id       id
                :name     "xpto"
                :price    0.99
                :quantity 10}
          mocked-request {:db          (atom {id item})
                          :body-params {:item-id "fdc8d2eb-ea52-4cf0-81c2-b5392afe822f" :amount 1}}
          resp (handle-item-purchase mocked-request)]
      (is (= 204
             (:status resp))))))