(ns immutant.component.web
  (:require [com.stuartsierra.component :as component]
            [immutant.web :as web]))

(defrecord ImmutantWebServer [config handler server]
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
                       (web/run (:app handler) config))]
        (assoc component :server server))))

  (stop [component]
    (if server
      (do (web/stop server)
          (assoc component :server nil))
      component)))

(defn immutant-web-server [config]
  "Creates a new immutant web server component that depends on a Ring handler
   component with key :app for the handler function."
  (map->ImmutantWebServer {:config config}))
