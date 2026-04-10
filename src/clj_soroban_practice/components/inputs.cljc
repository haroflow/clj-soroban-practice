(ns clj-soroban-practice.components.inputs)

(defn numeric [value min max path]
  [:input {:type :number
           :value value
           :min min
           :max max
           :on {:input [[:effects/save path
                         [:fmt/number-clamped [:event.target/value] min max]]]}}])

(defn checkbox [checked path]
  [:input {:type :checkbox
           :checked checked
           :on {:input [[:effects/save path [:event.target/checked]]]}}])

(defn select [value options value-fn label-fn path show-none]
  [:select
   {:on {:input [[:effects/save path [:event.target/value]]]}}
   (when show-none
     [:option {:value ""} "None"])
   (for [option options]
     (let [v (value-fn option)]
       [:option {:value v
                 :selected (when (= v value) true)}
        (label-fn option)]))])

(defn range [value min-val max-val step path]
  [:input {:type :range
           :min min-val
           :max max-val
           :step step
           :value value
           :on {:input [[:effects/save path [:event.target/value]]]}}])