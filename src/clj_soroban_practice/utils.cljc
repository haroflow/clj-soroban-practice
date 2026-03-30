(ns clj-soroban-practice.utils)

(defn pad-zero [n len]
  #?(:clj  (String/format (str "%0" len "d") (into-array [n]))
     :cljs (.padStart (.toString n) len "0")))

(defn speak [phrase]
  #?(:cljs (.speak js/speechSynthesis (js/SpeechSynthesisUtterance. phrase))
     :clj (println "Speaking: " phrase)))

(defn now []
  #?(:cljs (.getTime (js/Date.))
     :clj (System/currentTimeMillis)))