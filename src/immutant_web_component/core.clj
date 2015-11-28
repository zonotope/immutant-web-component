(ns immutant-web-component.core
  (:require [com.stuartsierra.component :as component]
            [immutant.web :as web])
  (:import java.net.ConnectException))

(defrecord ImmutantWebServerComponent [config handler server]
  component/Lifecycle
  (start [component]
    (if server
      component
      (let [host (:host config)
            port (:port config)
            server (do (-> (str "Starting web server. Listening on host: %s "
                                "and port: %d")
                           (format host port)
                           (println))
                       (web/run (:handler handler) config))]
        (assoc component :server server))))

  (stop [component]
    (if server
      (do (web/stop server)
          (assoc component :server nil))
      component)))

(defn immutant-web-component [config]
  "Creates a new immutant web server component that depends on a Ring handler
   component with key :app for the handler function."
  (map->ImmutantWebServerComponent {:config config}))
