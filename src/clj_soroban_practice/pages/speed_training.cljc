(ns clj-soroban-practice.pages.speed-training 
  (:require
   [clj-soroban-practice.components.inputs :as inputs]))

(defn render [state]
  (let [practice (:speed-training state)
        {:keys [digits how-many-numbers one-per-line active-session]} practice]
    [:div
     [:p "Improve your calculation speed with randomly generated numbers and voice dictation."]
     [:div.flex-row-gap
      [:label "Digits"
       (inputs/numeric digits 1 8 [:speed-training :digits])]
      [:label "How many numbers"
       (inputs/numeric how-many-numbers 2 20 [:speed-training :how-many-numbers])]]
     
     [:div.flex-row-gap
      [:label "Voice" (:voice practice)
       (inputs/select (:voice practice)
                      (:voices state)
                      #(.-name %)
                      #(str (.-name %) " - " (.-lang %))
                      [:speed-training :voice])]

      [:label "One per line"
       (inputs/checkbox one-per-line [:speed-training :one-per-line])]]
     
     (if (or (nil? (:numbers active-session))
             (:show-answer active-session))
       [:button {:on {:click [[:actions/start-speed-training]]}} "Start! (Space key)"]
       [:button {:on {:click [[:actions/show-answer]]}} "Check Answer! (Space key)"])
     
     (when (:numbers active-session)
       (list
        [:h3 "Numbers"]
        (if one-per-line
          (for [n (:numbers active-session)]
            [:p "+ " n])
          [:p (interpose " + " (:numbers active-session))])
        (when (:show-answer active-session)
          [:h4 "Result: " (:sum active-session)])))]))