(ns clj-soroban-practice.pages.speed-practice)

;; TODO fix number ranges
(defn render [state]
  (let [practice (:speed-practice state)
        {:keys [digits how-many-numbers one-per-line active-session]} practice]
    [:div
     [:h2 "Speed Practice"]
     [:p "Practice your speed on a real soroban!"]

     [:div
      [:label "Digits"]
      [:input {:type :number
               :value digits
               :on {:input [[:effects/save [:speed-practice :digits] [:fmt/number [:event.target/value]]]]}}]]

     [:div
      [:label "How many numbers"]
      [:input {:type :number
               :value how-many-numbers
               :on {:input [[:effects/save [:speed-practice :how-many-numbers] [:fmt/number [:event.target/value]]]]}}]]

     [:div
      [:label "One per line"]
      [:input {:type :checkbox
               :checked one-per-line
               :on {:input [[:effects/save [:speed-practice :one-per-line] [:event.target/checked]]]}}]]
     
     [:div
      [:label "Voice"]
      [:select
       {:on {:input [[:effects/save [:speed-practice :voice] [:event.target/value]]]}}
       (for [voice (:voices state)]
         (let [voice-name (.-name voice)
               lang (.-lang voice)]
           [:option {:value voice-name} (str voice-name " - " lang)]))]]
     
     (if (or (nil? (:numbers active-session))
             (:show-answer active-session))
       [:button {:on {:click [[:actions/start-speed-practice]]}} "Start! (Space key)"]
       [:button {:on {:click [[:actions/show-answer]]}} "Check Answer! (Space key)"])

     (when (:numbers active-session)
       (list
        [:h3 "Numbers"]
        (if one-per-line
          (for [n (:numbers active-session)]
            [:p "+ " n])
          [:p (interpose " + " (:numbers active-session))])
        (when (:show-answer active-session)
          [:h4 "Sum: " (:sum active-session)])))]))