(ns clj-soroban-practice.pages.number-practice
  (:require
   [clj-soroban-practice.components.soroban :as soroban]))

(def ^:private min-columns 3)
(def ^:private max-columns 15)
(def ^:private min-number 0)
(def ^:private max-number 999999999999999)

(defn render [state]
  (let [practice (:number-practice state)
        {:keys [columns number]} practice]
    [:div
     [:label "Columns"]
     [:input {:type :number
              :value columns
              :min min-columns
              :max max-columns
              :on {:input [[:effects/save [:number-practice :columns]
                            [:fmt/number-clamped [:event.target/value] min-columns max-columns]]]}}]
     [:label "Number"]
     [:input {:type :number
              :value number
              :min min-number
              :max max-number
              :on {:input [[:effects/save [:number-practice :number]
                            [:fmt/number-clamped [:event.target/value] min-number max-number]]]}}]
     [:button {:on {:click [[:effects/save [:number-practice :number] 0]]}} "Clear"]
     [:div.full-width-centered.mt-1rem
      (soroban/render number columns)]]))