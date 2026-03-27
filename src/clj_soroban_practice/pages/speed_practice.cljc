(ns clj-soroban-practice.pages.speed-practice)

(defn render [state]
  [:div
   [:h2 "Speed Practice"]
   [:p "Practice your speed on a real soroban!"]

   [:label "Digits"]
   [:input {:type :number
            :value (:speed-practice/digits state)
            :on {:input [[:effects/save [:speed-practice/digits] [:fmt/number [:event.target/value]]]]}}]

   [:label "How many numbers"]
   [:input {:type :number
            :value (:speed-practice/how-many-numbers state)
            :on {:input [[:effects/save [:speed-practice/how-many-numbers] [:fmt/number [:event.target/value]]]]}}]

   [:label "One per line"]
   [:input {:type :checkbox
            :checked (:speed-practice/one-per-line state)
            :on {:input [[:effects/save [:speed-practice/one-per-line] [:event.target/checked]]]}}]

   (if (or (nil? (:random-numbers state))
           (:show-answer state))
     [:button {:on {:click [[:actions/start-speed-practice]]}} "Start! (Space key)"]
     [:button {:on {:click [[:actions/show-answer]]}} "Check Answer! (Space key)"])

   (when (:random-numbers state)
     (list
      [:h3 "Numbers"]
      (if (:speed-practice/one-per-line state)
        (for [n (:random-numbers state)]
          [:p "+ " n])
        [:p (interpose " + " (:random-numbers state))])
      (when (:show-answer state)
        [:h4 "Sum: " (:random-numbers-sum state)])))])