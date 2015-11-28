(ns immutant-web-component.core-test
  (:require [clojure.test :refer :all]
            [com.stuartsierra.component :as component]
            [immutant-web-component.core :refer :all]
            [clj-http.client :as http])
  (:import java.net.ConnectException))

(deftest test-immutant-web-component
  (let [response {:status 200 :headers {} :body "test response"}
        handler-component {:handler (constantly response)}
        server (assoc (immutant-web-component {:host "localhost" :port 4800})
                      :handler handler-component)]
    (testing "component starts"
      (let [server (component/start server)]
        (try (let [test-response (http/get "http://localhost:4800")]
               (is (= (:status test-response) (:status response)))
               (is (= (:body test-response) (:body response))))
             (finally
               (component/stop server)))))
    (testing "component stops"
      (is (thrown? ConnectException (http/get "http://localhost:4800"))))))
