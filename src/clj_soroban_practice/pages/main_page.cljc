(ns clj-soroban-practice.pages.main-page 
  (:require
   [clj-soroban-practice.pages.number-practice :as number-practice]
   [clj-soroban-practice.pages.speed-practice :as speed-practice]))

(defn render [state]
  [:div
   [:a {:href "#"
        :on {:click [[:effects/navigate :main]]}}
    [:h1 "Soroban Practice"]]

   [:button {:on {:click [[:effects/navigate :number-practice]]}}
    "Number practice"]
   [:button {:on {:click [[:effects/navigate :speed-practice]]}}
    "Speed practice"]
   [:br]
   [:br]

   (case (:route state)
     :main [:h1 "Welcome"]
     :number-practice (number-practice/render state)
     :speed-practice (speed-practice/render state))])