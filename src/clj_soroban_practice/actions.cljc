(ns clj-soroban-practice.actions
  (:require
   [clj-soroban-practice.logic :as logic]
   [clj-soroban-practice.utils :as utils]
   [clojure.string :as string]
   [nexus.registry :as nxr]))

(nxr/register-action!
 :actions/bead-click
 (fn [state bead-value column-data column-index]
   (let [new-column-data (logic/toggle-bead bead-value column-data)
         columns (logic/number->columns (:number state) (:max-columns state))
         new-columns (assoc columns column-index new-column-data)
         new-number (logic/columns->number new-columns)]
     [[:effects/save [:number] new-number]])))

(defn generate-practice! [digits how-many-numbers]
  ; impure, has random generation and getTime
  (let [random-numbers (repeatedly how-many-numbers #(logic/gen-random-number digits))
        sum (reduce + random-numbers)
        now (utils/now)]
    {:numbers random-numbers
     :sum sum
     :now now
     :speak (string/join ", " random-numbers)}))

;; TODO Maybe add a global shortcut (spacebar) to show answer
;; TODO Show elapsed time or time taken
;; TODO Add option for addition/subtraction
;; TODO Subtraction should not give a negative number
;; TODO Add language option... english / pt-br
;; TODO add interrupt
(nxr/register-action!
 :actions/start-speed-practice
 (fn [state]
   (let [digits (:speed-practice/digits state)
         how-many-numbers (:speed-practice/how-many-numbers state)
         practice (generate-practice! digits how-many-numbers)]
     [[:effects/save [:random-numbers-started-at] (:now practice)]
      [:effects/save [:show-answer] false]
      [:effects/save [:random-numbers] (:numbers practice)]
      [:effects/save [:random-numbers-sum] (:sum practice)]
      [:effects/speak (:speak practice)]])))

(nxr/register-action!
 :actions/show-answer
 (fn [state]
   [[:effects/save [:show-answer] true]
    [:effects/speak (str "Result: " (:random-numbers-sum state))]]))