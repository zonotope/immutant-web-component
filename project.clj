(defproject immutant-web-component "0.2.1-SNAPSHOT"
  :description "A component for the Immutant Web Server."
  :url "http://github.com/zonotope/immutant-web-component"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [com.stuartsierra/component "0.3.1"]
                 [org.immutant/web "2.1.2"]]

  :profiles {:dev {:dependencies [[clj-http "2.0.0"]]}})
