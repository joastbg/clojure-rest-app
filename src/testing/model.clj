;; Datomic model
;; (C) Johan Astborg, 2013

(ns testing.model
  (:require [datomico.core :as dc])
  (:require [datomico.model :as model]))

(def model-ns :user)

(def schema (dc/build-schema model-ns
  [[:username :string]
  [:password :string]]))

(dc/start {:dynamic-vars true
           :schemas [schema]})

;; Models
;;(model/create-model-fn :find-id model-ns)

;; Generate mappings
(model/create-model-fn :find-id model-ns)
(model/create-model-fn :find-all model-ns)
(model/create-model-fn :find-first model-ns)
(model/create-model-fn :delete model-ns)
(model/create-model-fn :delete-all model-ns)
(model/create-model-fn :delete-all-tx model-ns)
(model/create-model-fn :find-or-create model-ns)
(model/create-model-fn :update model-ns)
(model/create-model-fn :update-tx model-ns)
(model/create-model-fn :create model-ns)
(model/create-model-fn :create-tx model-ns)
(model/create-model-fn :delete-value model-ns)
(model/create-model-fn :delete-value-tx model-ns)

;; Sample data
(create {:username "johan" :password "lazer1234"})
(create {:username "nisse" :password "nisse rocks"})

;;(delete 17592186045418)
