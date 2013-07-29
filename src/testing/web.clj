;; Attempt to port Raptor to Clojure
;; (C) Johan Astborg, 2013

(ns testing.web
  (:use compojure.core)
  (:use compojure.route)
  ;;(:use ring.middleware.json-params)
  ;;(:use ring.middleware.json)
  (:use ring.middleware.json-params)
  ;;(:use testing.gen)
  (:use hiccup.core)
  (:use hiccup.page)
  (:require [testing.model :as orm])
  (:require [clj-json.core :as json])
  (:require [testing.elem :as elem]))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defn html-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "text/html"}
   :body data})

;; UUID generator 
(defn uuid [] (str (java.util.UUID/randomUUID)))
(comment
;; pages
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
)

(defn crudresource [] 
  [:ul (for [x (range 1 4)] [:li x])]
)

;; Start page for Raptor
(defn raptorhome []
  (html5
   [:head
    [:title "Gecemmo Raptor"]]
   [:body
    [:h1 "Gecemmo Raptor"]
    [:div {:id "content"} "Welcome to Gecemmo Raptor v.0.1"]]))

(defn common [title & body]
  (html5
    [:head
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1, maximum-scale=1"}]
      [:title title]

     (include-css "/www/stylesheets/base.css"
               "/www/stylesheets/skeleton.css"
               "/www/stylesheets/screen.css")
  (include-css "http://fonts.googleapis.com/css?family=Roboto:400,700,300,900")]
  [:body
    [:div {:id "header"}
      [:h1 {:class "container"} "Gecemmo Raptor"]]
    [:div {:id "content" :class "container"} body]]))

(defn four-oh-four []
  (common "Page Not Found"
          [:div {:id "four-oh-four"}
          "The page you requested could not be found!"]))

;; handlers

(defrecord Resource [name methods])
(defrecord Method [kind url])

(def myresources (Resource. "Elements" [(Method. "GET" "/elems") (Method. "GET" "/elems/:id")]))
(:name 

;;(map println (:methods endpoints))

(defroutes handler

  ;; Operations for user resource
  (GET "/elems" [] (json-response (orm/find-all)))
  (GET "/elems/:id" [id] (json-response (orm/find-id id)))
  (POST "/elems" {params :params} (json-response (orm/create params)))
;;(POST "/elems" [username password] (json-response (orm/create {:username username :password password})))
  (DELETE "/elems/:id" [id] (json-response ((orm/delete id) {:status "OK"})))

  ;; Autogen CRUD-operations

  ;; Raptor
  (GET "/" [] (html-response (common "Welcome to Raptor" [:div "Welcome to Raptor, the next generation REST-toolkit!"])))

  ;;(GET "/" [] (slurp "wwwroot/index.html"))
  (GET "/tojan" [] (html-response (html [:span {:class "foo"} "bar"])))
  (GET "/johan" [] (json-response {"hello" "Johan"}))

  ;; Static files
  (files "/www" {:root "wwwroot"})

  ;; Error page
  (not-found (four-oh-four)))
  ;;(GET "/html" [] indexpage))

(comment
;; TESTING
(use 'hiccup.core)
(html [:span {:class "foo"} "bar"])
)

;; main
(def app
  (-> handler
    wrap-json-params))
