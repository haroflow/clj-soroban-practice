(ns clj-soroban-practice.pages.number-practice
  (:require
   [clj-soroban-practice.components.soroban :as soroban]))

(defn render [state]
  [:div
   [:h2 "Number Practice"]
   [:label "Max Columns"]
   [:input {:type :number
            :value (:max-columns state)
            :on {:input [[:effects/save [:max-columns] [:fmt/number [:event.target/value]]]]}}]

   [:label "Number"]
   [:input {:type :number
            :value (:number state)
            :on {:input [[:effects/save [:number] [:fmt/number [:event.target/value]]]]}}]

   [:br]
   [:br]

   [:div {:style {:display :flex}}
    (soroban/render (:number state) (:max-columns state))]])