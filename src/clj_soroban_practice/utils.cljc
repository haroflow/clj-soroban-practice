(ns clj-soroban-practice.utils)

(defn pad-zero [n len]
  #?(:clj  (String/format (str "%0" len "d") (into-array [n]))
     :cljs (.padStart (.toString n) len "0")))

(defn speak [phrase voice]
  #?(:cljs
     (do (.cancel js/speechSynthesis)
         (let [utter (js/SpeechSynthesisUtterance. phrase)]
           (set! (.-voice utter) voice)
           (.speak js/speechSynthesis utter)))
     :clj (println "Speaking: " phrase voice)))

(defn now []
  #?(:cljs (.getTime (js/Date.))
     :clj (System/currentTimeMillis)))

(defn rand-int-between
  "Returns a random integer between `a` (inclusive) and `b` (exclusive)."
  [a b]
  {:pre [(< a b)]}
  (let [diff (- b a)
        r (rand-int diff)]
    (+ r a)))

(defn clamp [n min-val max-val]
  (max min-val (min max-val n)))