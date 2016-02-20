(ns immutant.web-component
  (:require [com.stuartsierra.component :as component]
            [immutant.web :as web])
  (:import java.net.ConnectException))

(defrecord ImmutantWebComponent []
  component/Lifecycle
  (start [component]
    (if-not (:server component)
      (let [host (or (:host component)
                     "0.0.0.0")
            port (Integer. (or (:port component)
                               8080))
            config {:host host :port port}
            handler (:app component)
            server (do (-> (str "Starting web server. Listening on host: %s "
                                "and port: %d")
                           (format host port)
                           (println))

                       (web/run (:handler handler) config))]

        (assoc component
               :server server
               :host host
               :port port))

      component))

  (stop [component]
    (if-let [server (:server component)]
      (do (-> (str "Stopping web server on host: %s and port: %d")
              (format (:host component) (:port component))
              (println))
          (web/stop server)
          (dissoc component :server))

      component)))

(defn immutant-web-component [config]
  "Creates a new immutant web server component that depends on a Ring handler
   component with key :app for the handler function."
  (map->ImmutantWebComponent config))
