(defproject probprog20 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [nstools "0.2.4"]
                 [dtolpin/anglican "1.2.1"]
                 [dtolpin/anglican-user "1.2.1"]
                 [clojure-csv/clojure-csv "2.0.2"]
                 [org.nfrac/cljbox2d.testbed "0.5.0"]
                 [org.nfrac/cljbox2d "0.5.0"]
                 [org.clojure/data.priority-map "1.0.0"]
                 [net.mikera/core.matrix "0.62.0"]
                 [net.mikera/core.matrix.stats "0.7.0"]
                 [net.mikera/vectorz-clj "0.48.0"]]
  :plugins [[org.clojars.benfb/lein-gorilla "0.7.0"]]
  :resource-paths ["programs"]
  :main ^:skip-aot anglican.core)
