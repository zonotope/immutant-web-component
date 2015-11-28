(defproject immutant-web-component "0.1.0-SNAPSHOT"
  :description "A component for the Immutant Web Server."
  :url "http://github.com/zonotope/immutant-web-component"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [com.stuartsierra/component "0.3.0"]
                 [org.immutant/web "2.1.1"]]

  :profiles {:dev {:dependencies [[clj-http "2.0.0"]]}})
