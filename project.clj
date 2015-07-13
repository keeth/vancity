(defproject vancity "0.1.0"
  :description "Download OFX transactions from Vancity bank account or Vancity VISA account"
  :url "https://github.com/keeth/vancity"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [clj-webdriver "0.6.1"]
                 [clj-http "1.1.2"]]
  :main ^:skip-aot vancity.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
