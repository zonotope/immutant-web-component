# immutant-web-component

A [component](https://github.com/stuartsierra/component) for the
[Immutant](http://immutant.org/)
[web server](http://immutant.org/documentation/current/apidoc/immutant.web.html).
It is an adapter for Immutant web to be used in
[reloadable applications](http://thinkrelevance.com/blog/2013/06/04/clojure-workflow-reloaded).

## Installation
Add the dependency to your `project.clj`:

[![Clojars Project](http://clojars.org/immutant-web-component/latest-version.svg)](http://clojars.org/immutant-web-component)

## Usage
First require the libraries:

```Clojure
(require '[immutant-web-component.core :refer [immutant-web-server]]
         '[com.stuartsierra.component :as component])
```

Then you can create a new Immutant web server component. It wil depend on a Ring
handler component with an `:handler` key holding a handler function. Then you
can start and stop it:

```Clojure
(def web-server
  (atom (immutant-web-server {:host "localhost" :port "8080"})))

(let [handler-component {:handler (constantly
                                    {:status 200
                                     :body "Just handling business."})}]
  (swap! web-server #(assoc % :handler handler-component))
  (swap! web-server #(component/start %))
  ;; Make some requests to localhost:8080
  (swap! web-server #(component/stop %)))

```

Or, you can use it as part of a reloadable system:

```Clojure
(def handler-fn
  (constantly {:status 200 :body "I'm still here!"}))

(def system
  (component/system-map
    :handler {:handler handler-fn}
    :server (component/using
              (immutant-web-server {:host "localhost" :port "8080"})
              [:handler])))

(alter-var-root #'system component/start)
;; do stuff
(alter-var-root #'system component/stop)
```

## License

Copyright © 2015 ben lamothe

Distributed under the MIT License
