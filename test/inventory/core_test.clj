(ns inventory.core-test
  (:require [clojure.test :refer :all]
            [inventory.core :refer :all]
            [muuntaja.core :as m]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest handler-test
  (testing "Handler response should return status 200."
    (let [request {:request-method :get :uri "/ok"}
          response (handler request)
          expected 200]
      (is (= (:status response) expected))))
  (testing "Handler response should return status 200 and not have an empty body."
    (let [request {:request-method :get :uri "/inventory"}
          response (handler request)
          expected-status 200
          body (m/decode-response-body response)]
      (is
        (and
          (= (:status response) expected-status)
          (contains? body :inventory-items)
          (contains? body :inventory-value))))))