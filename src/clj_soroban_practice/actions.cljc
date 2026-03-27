(ns clj-soroban-practice.actions
  (:require
   [clj-soroban-practice.logic :as logic]
   [nexus.registry :as nxr]))

(nxr/register-action!
 :actions/bead-click
 (fn [state bead-value column-data column-index]
   (let [new-column-data (logic/toggle-bead bead-value column-data)
         columns (logic/number->columns (:number state) (:max-columns state))
         new-columns (assoc columns column-index new-column-data)
         new-number (logic/columns->number new-columns)]
     [[:effects/save [:number] new-number]])))

(nxr/register-action!
 :actions/start-speed-practice
 (fn [_state]
   ;; TODO I can't generate the random number here? i dont think so... because these actions are supposed to be pure...
   ;; what I could do is generate based on seed... then it becomes pure.
   [[:effects/start-speed-practice]]))

(nxr/register-action!
 :actions/show-answer
 (fn [state]
   [[:effects/save [:show-answer] true]
    [:effects/speak (str "Result: " (:random-numbers-sum state))]]))