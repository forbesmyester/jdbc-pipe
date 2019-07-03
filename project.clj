(defproject jdbc-pipe "0.0.1"
  :description "Quick JDBC queries respecting UNIX conventions (environmental variables, STDIN, STDOUT etc)"
  :url "http://example.com/FIXME"
  :license {:name "MIT" :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/data.json "0.2.6"]
                 [cli-matic "0.3.7"]
                 [funcool/clojure.jdbc "0.9.0"]
                 [org.clojure/data.csv "0.1.4"]]
  :resource-paths []
  :main ^:skip-aot jdbc-pipe.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
