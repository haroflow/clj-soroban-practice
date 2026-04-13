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
          :free-mode {:columns 6
                      :number 0}
          :speed-training {:digits 3
                           :one-per-line false
                           :how-many-numbers 5
                           :voice-rate 1
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
   (utils/cancel-speech)
   (swap! store assoc :route view-kw)))

;; TODO we're using [:speed-training :voice], but this effect is global.
(nxr/register-effect!
 :effects/speak
 (fn [_ store phrase]
   (let [selected-voice (-> @store :speed-training :voice)
         voices (@store :voices)
         voice (first (filter #(= selected-voice (.-name %)) voices))
         rate (-> @store :speed-training :voice-rate)]
     (utils/speak phrase voice rate))))
