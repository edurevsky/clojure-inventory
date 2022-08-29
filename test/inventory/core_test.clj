(ns inventory.core-test
  (:require [clojure.test :refer :all]
            [inventory.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest handler-test
  (testing "Handler response should return status 200."
    (let [request {:request-method :get :uri "/ok"}
          response (handler request)
          expected 200]
      (is (= (:status response) expected)))))