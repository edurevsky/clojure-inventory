(ns inventory.core-test
  (:require [clojure.test :refer :all]
            [inventory.core :refer :all]
            [muuntaja.core :as m]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest handler-test
  (testing "Should return status 200."
    (let [request {:request-method :get :uri "/ok"}
          response (handler request)
          expected 200]
      (is (= (:status response) expected))))
  (testing "Should return status 200 and not have an empty body."
    (let [request {:request-method :get :uri "/inventory"}
          response (handler request)
          expected-status 200
          body (m/decode-response-body response)]
      (is
        (and
          (= (:status response) expected-status)
          (contains? body :inventory-items)
          (contains? body :inventory-value)))))
  (testing "Should return status 201 and body id must not be nil."
    (let [request {:request-method :post
                   :uri            "/items"
                   :body-params    {:name     "name"
                                    :quantity 1
                                    :price    0.1}}
          response (handler request)
          expected-status 201
          body (m/decode-response-body response)]
      (is
        (and
          (= (:status response) expected-status)
          (not (nil? (:id body)))))))
  (testing "Should return status 400 when requesting with an invalid body."
    (let [request {:request-method :post
                   :uri            "/items"
                   :body-params    {:quantity 1
                                    :price    0.1}}
          response (handler request)
          expected-status 400]
      (is (= (:status response) expected-status)))))