(ns testing.core
	(:use ring.adapter.jetty)
	(:require [testing.web :as web]))

;;(comment

(defn hello []
(println "hello world"))

(defn -main [& args]
  (println "Welcome to my project! These are your args:" args)
  (run-jetty #'web/app {:port 8080}))


  ;;hello)

(comment
;; Datomic
(require '[datomico.core :as dc])

; Define a model's database key once and don't think of it again while
; interacting with your model
(def model-namespace :user)

; Build schemas easily without needing to think of partitions and a number of
; internal schema attributes. Basically you don't have to pour over
; http://docs.datomic.com/schema.html
(def schema (dc/build-schema model-namespace
  [[:username :string]
  [:password :string]]))

; Starting in a repl is easy;  a uri, db and connection will be auto-generated
(ns user)
(require '[datomico.core :as dc])
(dc/start {:dynamic-vars true
           :schemas [models.user/schema]})
)
