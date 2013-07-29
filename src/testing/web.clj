; Attempt to port Raptor to Clojure
; (C) Johan Astborg, 2013

(ns testing.web
  (:use compojure.core)
  (:use ring.middleware.json-params)
  (:use testing.gen)
  (:require [clj-json.core :as json])
  (:require [testing.elem :as elem]))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

; UUID generator 
(defn uuid [] (str (java.util.UUID/randomUUID)))

; pages
(def indexpage (str 
    (html 
      (head 
        (title "HTML-gen using Clojure")
      ) 
    (body 
      (h1 "Welcome to HTML-gen using Clojure"))
      (p "Very nice page")
      (ahref "/elems" "My page")
      (footer
        (hr)
        (small "Start page")
      )
    )
  )
)

; handlers
(defroutes handler
  (GET "/elems" []
    (json-response (elem/list)))

  (GET "/elems/:id" [id]
    (json-response (elem/get id)))

  (PUT "/elems/:id" [id attrs]
    (json-response (elem/put id attrs)))

  (DELETE "/elems/:id" [id]
    (json-response (elem/delete id)))

  (GET "/johan" []
    (json-response {"hello" "johan"}))

  (GET "/html" []
    indexpage))

; main
(def app
  (-> handler
    wrap-json-params))