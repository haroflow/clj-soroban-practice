(ns clj-soroban-practice.pages.free-mode
  (:require
   [clj-soroban-practice.components.inputs :as inputs]
   [clj-soroban-practice.components.soroban :as soroban]))

(defn render [state]
  (let [practice (:free-mode state)
        {:keys [columns number]} practice]
    [:div
     [:div.inline-flex.flex-row-gap
      [:label "Columns"
       (inputs/numeric columns 3 15 [:free-mode :columns])]

      [:label "Number"
       (inputs/numeric number 0 999999999999999 [:free-mode :number])]]

     [:button {:on {:click [[:effects/save [:free-mode :number] 0]]}} "Clear"]

     [:div.full-width-centered.mt-1rem
      (soroban/render number columns)]]))