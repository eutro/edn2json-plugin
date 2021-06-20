(ns edn2json-plugin.core
  (:require [clojure.edn :as edn])
  (:import (com.google.gson JsonElement JsonObject JsonArray JsonPrimitive JsonNull)))

(defn or-func [f & gs]
  (reduce (fn [f g]
            (fn [x]
              (or (f x) (g x))))
          f gs))

(defn edn->json
  ^JsonElement
  [obj]
  (condp #(%1 %2) obj
    map?
    (reduce
      (fn [^JsonObject jsobj [k v]]
        (.add jsobj
              (if (keyword? k) (name k) (str k))
              (edn->json v))
        jsobj)
      (JsonObject.)
      obj)

    (or-func list? vector? set?)
    (reduce
      (fn [^JsonArray jsarr o]
        (.add ^JsonArray jsarr (edn->json o))
        jsarr)
      (JsonArray.)
      obj)

    boolean? (JsonPrimitive. ^Boolean obj)
    number? (JsonPrimitive. ^Number obj)
    nil? JsonNull/INSTANCE

    (JsonPrimitive. (if (keyword? obj) (name obj) (str obj)))))
