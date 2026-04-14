(ns clj-soroban-practice.pages.main-page 
  (:require
   [clj-soroban-practice.pages.clock :as clock]
   [clj-soroban-practice.pages.free-mode :as free-mode]
   [clj-soroban-practice.pages.speed-training :as speed-training]))

(defn- menu-button [label route current-route]
  [:a {:class (when (= route current-route) "active")
       :href (str "#/" (name route))}
   label])

(defn render [state]
  [:div.main-container
   [:a.main-title {:href "#/"}
    [:h1 "Soroban Practice"]]
   
   [:div.navbar
    (menu-button "Free Mode" :free-mode (:route state))
    (menu-button "Speed Training" :speed-training (:route state))
    (menu-button "Learn" :learn (:route state))
    (menu-button "Clock" :clock (:route state))]
   
   (case (:route state)
     nil [:h1 "Welcome"]
     :free-mode (free-mode/render state)
     :speed-training (speed-training/render state)
     :clock (clock/render state)
     [:h1 "Page not found"])])