(ns clj-soroban-practice.effects
  (:require
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

(nxr/register-effect!
 :effects/cancel-speech
 (fn [_ _]
   (.cancel js/speechSynthesis)))

(defn speak [phrase voice rate]
  (.cancel js/speechSynthesis)
  (let [utter (js/SpeechSynthesisUtterance. phrase)]
    (set! (.-voice utter) voice)
    (set! (.-rate utter) (or rate 1))
    (.speak js/speechSynthesis utter)))

(nxr/register-effect!
 :effects/speak
 (fn [_ store phrase]
   (let [selected-voice (-> @store :speed-training :voice)
         voices (@store :voices)
         voice (first (filter #(= selected-voice (.-name %)) voices))
         rate (-> @store :speed-training :voice-rate)]
     (speak phrase voice rate))))

(nxr/register-effect!
   :effects/start-clock
   (fn [{:keys [dispatch]} store]
     (let [clock-id (js/setInterval 
                     #(dispatch [[:effects/save [:now] (js/Date.)]
                                 [:effects/play-sound]])
                     1000)]
       (swap! store assoc :clock-id clock-id))))

(nxr/register-effect!
 :effects/stop-clock
 (fn [_ store]
   (when-let [clock-id (:clock-id @store)]
     (js/clearInterval clock-id)
     (swap! store assoc :clock-id nil))))

(nxr/register-effect!
 :effects/now
 (fn [_ store]
   (swap! store assoc :now (js/Date.))))

(nxr/register-effect!
 :effects/play-sound
 (fn [_ _store]
   (.play (js/document.getElementById "click-sound"))))