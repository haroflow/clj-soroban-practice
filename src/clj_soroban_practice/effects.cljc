(ns clj-soroban-practice.effects
  (:require
   [clj-soroban-practice.utils :as utils]
   [nexus.registry :as nxr]))

(nxr/register-effect!
 :effects/initialize-db
 (fn [_ store started-at]
   (swap! store assoc
          :started-at started-at
          :route :main

          :number-practice {:columns 6
                            :number 0}

          :speed-practice {:digits 3
                           :one-per-line true
                           :how-many-numbers 5
                           :active-session nil})))

;; TODO batch
(nxr/register-effect!
 :effects/save
 (fn [_ store path value]
   (swap! store assoc-in path value)))

;; TODO change url? maybe add an action for this too? idk
(nxr/register-effect!
 :effects/navigate
 (fn [_ store view-kw]
   (swap! store assoc :route view-kw)))

;; TODO we're using [:speed-practice :voice], but this effect is global.
(nxr/register-effect!
 :effects/speak
 (fn [_ store phrase]
   (let [selected-voice (-> @store :speed-practice :voice)
         voices (@store :voices)
         voice (first (filter #(= selected-voice (.-name %)) voices))]
     (utils/speak phrase voice))))
