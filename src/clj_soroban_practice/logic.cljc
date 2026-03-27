(ns clj-soroban-practice.logic
  (:require
   [clj-soroban-practice.utils :as utils]
   [clojure.math :as math]))

(defn number->column-data [number]
  {:1 (>= (mod number 5) 1)
   :2 (>= (mod number 5) 2)
   :3 (>= (mod number 5) 3)
   :4 (>= (mod number 5) 4)
   :5 (>= number 5)})

(defn column-data->number [column-data]
  (+ (when (:1 column-data) 1)
     (when (:2 column-data) 1)
     (when (:3 column-data) 1)
     (when (:4 column-data) 1)
     (when (:5 column-data) 5)))

(defn number->columns [number max-columns]
  (->> (utils/pad-zero number max-columns)
       (mapv number->column-data)))

(defn columns->number [columns]
  (->> (map column-data->number columns)
       (apply str)
       parse-long))

(defn gen-random-number [digits]
  (let [mult (math/pow 10 (dec digits))
        max (* 9 mult)
        rnd (rand-int max)
        max-ranged (+ (* 1 mult) rnd)]
    max-ranged))

(defn- add-bead [bead-value column-data]
  (case bead-value
    :1 (assoc column-data :1 true)
    :2 (assoc column-data :1 true :2 true)
    :3 (assoc column-data :1 true :2 true :3 true)
    :4 (assoc column-data :1 true :2 true :3 true :4 true)
    :5 (assoc column-data :5 true)))

(defn- remove-bead [bead-value column-data]
  (case bead-value
    :1 (assoc column-data :1 false :2 false :3 false :4 false)
    :2 (assoc column-data :2 false :3 false :4 false)
    :3 (assoc column-data :3 false :4 false)
    :4 (assoc column-data :4 false)
    :5 (assoc column-data :5 false)))

(defn toggle-bead [bead-value column-data]
  (let [active? (get column-data bead-value)]
    (if active?
      (remove-bead bead-value column-data)
      (add-bead bead-value column-data))))