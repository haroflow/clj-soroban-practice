(ns clj-soroban-practice.actions
  (:require
   [clj-soroban-practice.logic :as logic]
   [clojure.string :as string]
   [nexus.registry :as nxr]))

(nxr/register-action!
 :actions/bead-click
 (fn [_state bead-value padded-number column-index value-path]
   (let [columns (mapv logic/number->column-data padded-number)
         new-columns (update columns column-index #(logic/toggle-bead bead-value %))
         new-number (logic/columns->number new-columns)]
     [[:effects/save value-path new-number]
      [:effects/play-sound]])))

(defn generate-practice! [digits how-many-numbers subtraction]
  ; impure, has random generation
  (let [random-numbers (if subtraction
                         (logic/gen-numbers-with-subtraction how-many-numbers digits)
                         (repeatedly how-many-numbers #(logic/gen-random-number digits)))
        sum (reduce + random-numbers)]
    {:numbers random-numbers
     :sum sum
     :speak (string/join " . " random-numbers)
     :show-answer false}))

;; TODO Maybe add a global shortcut (spacebar) to show answer
;; TODO Show elapsed time or time taken
(nxr/register-action!
 :actions/start-speed-training
 (fn [state]
   (let [speed-training (:speed-training state)
         digits (:digits speed-training)
         how-many-numbers (:how-many-numbers speed-training)
         subtraction (:subtraction speed-training)
         practice (generate-practice! digits how-many-numbers subtraction)
         voice-selected? (not (clojure.string/blank? (:voice speed-training)))]
     (cond-> [[:effects/save [:speed-training :active-session] practice]]
       voice-selected? (conj [:effects/speak (:speak practice)])))))

(nxr/register-action!
 :actions/show-answer
 (fn [state]
   (let [speed-training (:speed-training state)
         voice-selected? (not (clojure.string/blank? (:voice speed-training)))]
     (cond-> [[:effects/save [:speed-training :active-session :show-answer] true]]
       voice-selected? (conj [:effects/speak (str "Result: " (-> state :speed-training :active-session :sum))])))))

(nxr/register-action!
 :actions/navigate
 (fn [_state new-route]
   (cond-> [[:effects/cancel-speech]
            [:effects/now]
            [:effects/save [:route] new-route]]
     (= :clock new-route) (concat [[:effects/start-clock]])
     (not= :clock new-route) (concat [[:effects/stop-clock]]))))