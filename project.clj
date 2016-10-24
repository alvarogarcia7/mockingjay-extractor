(defproject mockingjay-extractor "0.2.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-yaml "0.4.0"]
                 [clj-http "2.3.0"]]
  :main ^:skip-aot mockingjay_extractor.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:plugins [[lein-midje "3.1.3"]]
                   :dependencies [[midje "1.6.3"]]}})
