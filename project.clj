(defproject
  subscribe "0.1.1"
  :url "https://github.com/etalab/subscribe"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ancient "0.6.14"]]
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async "0.4.490"
                  :exclusions [org.clojure/tools.reader]]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [ring "1.7.1"]
                 [ring/ring-defaults "0.3.2"]
                 [http-kit "2.3.0"]
                 [cheshire "5.8.1"]
                 [clj-http "3.9.1"]
                 [com.taoensso/tempura "1.2.1"]
                 [io.replikativ/datahike "0.1.3"]
                 [com.draines/postal "2.0.3"]]
  :description "Subscribe to a mailgun mailing list."
  :main subscribe.handler
  :profiles {:uberjar {:aot :all}})
