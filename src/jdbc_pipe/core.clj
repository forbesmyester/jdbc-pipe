(ns jdbc-pipe.core
  (:require
    [cli-matic.core :refer [run-cmd]]
    [jdbc.core :as jdbc]
    [clojure.data.csv :as csv]
    [clojure.data.json :as json])
  (:gen-class))


(defn get-db [opts]
	(cond 
		(get opts :uri false) (:uri opts)
		:else opts))


(defn query-ndjson [args]
  (let [db (get-db args)
        sql (slurp *in*)
        result (jdbc/fetch db [sql])
        println-json (fn [x] (json/write x *out* :value-fn (fn [_ b] (str b))) (println ""))]
    (doseq [row result] (println-json row))))


(defn query-edn [args]
  (let [db (get-db args)
        sql (slurp *in*)
        result (jdbc/fetch db [sql])]
    (doseq [row result] (println row))))


(defn query-xsv [args separator]
  (let [db (get-db args)
        sql (slurp *in*)
        result (jdbc/fetch db [sql])
        ]
    (csv/write-csv *out* [(map #(-> (key %) str (subs 1)) (first result))] :separator separator)
    (csv/write-csv *out* (map vals result) :separator separator)
    (println "")
    ))


(defn query-tsv [args] (query-xsv args (.charAt "\t" 0)))


(defn query-csv [args] (query-xsv args \,))


(def CONFIGURATION
	{:app         {:command     "jdbc-pipe"
                 :description "Quick JDBC queries respecting UNIX conventions (environmental variables, STDIN, STDOUT etc)"
                 :version     "0.0.1"}
	 :global-opts [{:option "classname" :env "JDBC_PIPE_CLASSNAME"
									:as "JDBC Driver Class" :type :string}
								 {:option "subprotocol" :env "JDBC_PIPE_SUBPROTOCOL"
									:as "JDBC Sub Protocol / Vendor" :type :string}
								 {:option "subname" :env "JDBC_PIPE_SUBNAME"
									:as "JDBC Sub Name / Database Name" :type :string}
								 {:option "user" :short "u" :env "JDBC_PIPE_USER"
									:as "JDBC User" :type :string}
								 {:option "password" :short "p" :env "JDBC_PIPE_PASSWORD"
									:as "JDBC Password" :type :string}
								 {:option "name" :env "JDBC_PIPE_NAME"
									:as "JDBC Name" :type :string}
								 {:option "host" :env "JDBC_PIPE_HOST"
									:as "JDBC Host" :type :string}
								 {:option "port" :env "JDBC_PIPE_PORT"
									:as "JDBC Port" :type :string}
								 {:option "vendor" :env "JDBC_PIPE_VENDOR"
									:as "JDBC Vendor" :type :string}
								 {:option "schema" :env "JDBC_PIPE_SCHEMA"
									:as "JDBC Schema" :type :string}
								 {:option "read-only" :env "JDBC_PIPE_READ_ONLY"
									:as "JDBC Read Only" :type :string}
								 {:option "uri" :short "r" :env "JDBC_PIPE_URI"
									:as "JDBC URI or Connection String" :type :string}]
   :commands    [{:command     "ndjson"
                  :description ["Runs a query outputting JSON"]
                  :runs        query-ndjson}
                 {:command     "edn"
                  :description ["Runs a query outputting EDN"]
                  :runs        query-edn}
                 {:command     "csv"
                  :description ["Runs a query outputting csv"]
                  :runs        query-csv}
                 {:command     "tsv"
                  :description ["Runs a query outputting tsv"]
                  :runs        query-tsv}]})


(defn -main
  "Quick JDBC queries respecting UNIX conventions (environmental variables, STDIN, STDOUT etc)"
  [& args]
  (run-cmd args CONFIGURATION))
