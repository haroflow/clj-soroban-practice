(ns clj-soroban-practice.pages.speed-training 
  (:require
   [clj-soroban-practice.components.inputs :as inputs]))

(defn render [state]
  (let [data (:speed-training state)
        {:keys [digits how-many-numbers one-per-line active-session]} data]
    [:div
     [:p "Improve your calculation speed with randomly generated numbers and voice dictation."]
     [:div.flex-row-gap
      [:label "Digits"
       (inputs/numeric digits 1 8 [:speed-training :digits])]
      [:label "How many numbers"
       (inputs/numeric how-many-numbers 2 20 [:speed-training :how-many-numbers])]]
     
     [:div.flex-row-gap
      [:label "Voice"
       (inputs/select (:voice data)
                      (:voices state)
                      #(.-name %)
                      #(str (.-name %) " - " (.-lang %))
                      [:speed-training :voice]
                      :show-none)]

      [:label "One per line"
       (inputs/checkbox one-per-line [:speed-training :one-per-line])]
      #_[:label "Show Soroban"
       (inputs/checkbox false [])]
      [:label "Subtraction"
       (inputs/checkbox (:subtraction data) [:speed-training :subtraction])]]
     
     (if (or (nil? (:numbers active-session))
             (:show-answer active-session))
       [:button {:on {:click [[:actions/start-speed-training]]}} "Start! (Space key)"]
       [:button {:on {:click [[:actions/show-answer]]}} "Check Answer! (Space key)"])
     
     (when (:numbers active-session)
       (let [numbers (map-indexed vector (:numbers active-session))
             format-number (fn [index number]
                             (cond
                               (zero? index) number
                               (neg? number) (str " - " (abs number))
                               :else (str " + " number)))]
         (list
          [:h3 "Numbers"]
          (if one-per-line
            (for [[i n] numbers]
              [:p (format-number i n)])
            [:p (for [[i n] numbers]
                  (format-number i n))])
          (when (:show-answer active-session)
            [:h4 "Result: " (:sum active-session)]))))]))