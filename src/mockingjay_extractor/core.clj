(ns mockingjay_extractor.core
  (:gen-class))

(require '[clj-yaml.core :as yaml])
(require '[clojure.data.json :as json])
(require '[clj-http.client :as client])

(defn 
  read-from
  [filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (json/read rdr)))

(defn
  write-to 
  [filename text]
  (with-open [w (clojure.java.io/writer  filename)]
    (.write w text)))

(defn
  at-server
  [prefix uri]
  (str prefix uri))

(defn 
  perform-query
  [uri]
  (do
    (println (str "accessing URL = " uri))
    (let [
          body (get (client/get uri) :body)
          x (println (str "body = " body))
         ]
      (println (str "body = " body))
      body)))

(defn
  process-single
  [server-prefix request]
  (let [uri (get request "URI")]
    {
      "name" "test"
      "request" {
        "uri" uri 
        "method" (get request "Method")
      }
      "response" {
        "code" 200
        "body" (->> uri (at-server server-prefix) perform-query)
      }
    }))

(defn
  dedup-by-url
  [requests]
  (let [urls (map #(get % "URI") requests)
        unique-urls (set urls)
        first-payload-by-url (fn [url] (first (filter #(= (get % "URI") url) requests)))]
      (map first-payload-by-url unique-urls)))
  

;; returns a clj structure
(defn process
  [server-prefix json]
  (map #(process-single server-prefix %) (dedup-by-url json)))

(defn to-yaml
  [structure]
  (yaml/generate-string structure :dumper-options {:flow-style :block}))

(defn -main
  [filename output server-prefix & rest]
  (->> filename
    read-from
    (process server-prefix)
    to-yaml
    (write-to output)))
