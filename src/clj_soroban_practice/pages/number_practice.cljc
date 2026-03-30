(ns clj-soroban-practice.pages.number-practice
  (:require
   [clj-soroban-practice.components.soroban :as soroban]))

;; TODO fix number ranges
(defn render [state]
  (let [practice (:number-practice state)
        {:keys [columns number]} practice]
    [:div
     [:h2 "Number Practice"]
     [:label "Columns"]
     [:input {:type :number
              :value columns
              :on {:input [[:effects/save [:number-practice :columns] [:fmt/number [:event.target/value]]]]}}]

     [:label "Number"]
     [:input {:type :number
              :value number
              :on {:input [[:effects/save [:number-practice :number] [:fmt/number [:event.target/value]]]]}}]

     [:br]
     [:br]

     [:div {:style {:display :flex}}
      (soroban/render number columns)]]))