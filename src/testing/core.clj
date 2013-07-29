(ns testing.core
	(:use ring.adapter.jetty)
	(:require [testing.web :as web]))

(defn -main [& args]
  (println "Welcome to my project! These are your args:" args)
  (run-jetty #'web/app {:port 8080}))