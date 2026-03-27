(ns clj-soroban-practice.effects
  (:require
   [clj-soroban-practice.logic :as logic]
   [nexus.registry :as nxr]))

(nxr/register-effect!
 :effects/initialize-db
 (fn [_ store started-at]
   (swap! store assoc
          :started-at started-at
          :route :main
          :max-columns 6
          :number 0
          :speed-practice/digits 3
          :speed-practice/one-per-line 3
          :speed-practice/how-many-numbers 5)))

(nxr/register-effect!
 :effects/save
 (fn [_ store path value]
   (swap! store assoc-in path value)))

;; TODO change url? maybe add an action for this too? idk
(nxr/register-effect!
 :effects/navigate
 (fn [_ store view-kw]
   (swap! store assoc :route view-kw)))

;; FIXME js stuff on cljc
(defn- speak [phrase]
  (.speak js/speechSynthesis (js/SpeechSynthesisUtterance. phrase)))

(nxr/register-effect!
 :effects/speak
 (fn [_ _store phrase]
   (speak phrase)))

;; TODO Maybe add a global shortcut (spacebar) to show answer
;; TODO Show elapsed time or time taken
;; TODO Add option for addition/subtraction
;; TODO Subtraction should not give a negative number
(nxr/register-effect!
 :effects/start-speed-practice
 (fn [_ store]
   (let [digits (:speed-practice/digits @store)
         how-many-numbers (:speed-practice/how-many-numbers @store)
         random-numbers (repeatedly how-many-numbers #(logic/gen-random-number digits))
         sum (reduce + random-numbers)]
     (run! (fn [number]
             (speak number))
           random-numbers)
     (swap! store assoc
            :random-numbers-started-at (.getTime (js/Date.)) ;; FIXME should not call JS from here
            :show-answer false
            :random-numbers random-numbers
            :random-numbers-sum sum))))


