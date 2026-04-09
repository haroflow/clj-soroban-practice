(ns clj-soroban-practice.actions
  (:require
   [clj-soroban-practice.logic :as logic]
   [clj-soroban-practice.utils :as utils]
   [clojure.string :as string]
   [nexus.registry :as nxr]))

(nxr/register-action!
 :actions/bead-click
 (fn [state bead-value column-data column-index]
   (let [number-practice (:free-mode state)
         new-column-data (logic/toggle-bead bead-value column-data)
         columns (logic/number->columns (:number number-practice) (:columns number-practice))
         new-columns (assoc columns column-index new-column-data)
         new-number (logic/columns->number new-columns)]
     [[:effects/save [:free-mode :number] new-number]])))

(defn generate-practice! [digits how-many-numbers]
  ; impure, has random generation and getTime
  (let [random-numbers (repeatedly how-many-numbers #(logic/gen-random-number digits))
        sum (reduce + random-numbers)
        now (utils/now)]
    {:started-at now
     :numbers random-numbers
     :sum sum
     :speak (string/join " . " random-numbers)
     :show-answer false}))

;; TODO Maybe add a global shortcut (spacebar) to show answer
;; TODO Show elapsed time or time taken
;; TODO Add option for addition/subtraction
;; TODO Subtraction should not give a negative number
;; TODO Add language option... english / pt-br
;; TODO add interrupt
(nxr/register-action!
 :actions/start-speed-training
 (fn [state]
   (let [speed-training (:speed-training state)
         digits (:digits speed-training)
         how-many-numbers (:how-many-numbers speed-training)
         practice (generate-practice! digits how-many-numbers)]
     [[:effects/save [:speed-training :active-session] practice]
      [:effects/speak (:speak practice)]])))

(nxr/register-action!
 :actions/show-answer
 (fn [state]
   [[:effects/save [:speed-training :active-session :show-answer] true]
    [:effects/speak (str "Result: " (-> state :speed-training :active-session :sum))]]))