(ns clj-soroban-practice.pages.main-page 
  (:require
   [clj-soroban-practice.pages.number-practice :as number-practice]
   [clj-soroban-practice.pages.speed-practice :as speed-practice]))

(defn render [state]
  [:div.main-container
   [:a.main-title {:href "#"
                   :on {:click [[:effects/navigate :main]]}}
    [:h1 "Soroban Practice"]]
   [:div.navbar
    [:a {:class (when (= :number-practice (:route state)) "active")
         :href "#"
         :on {:click [[:effects/navigate :number-practice]]}}
     "Number practice"]
    [:a {:class (when (= :speed-practice (:route state)) "active")
         :href "#"
         :on {:click [[:effects/navigate :speed-practice]]}}
     "Speed practice"]]
   (case (:route state)
     :main [:h1 "Welcome"]
     :number-practice (number-practice/render state)
     :speed-practice (speed-practice/render state))])