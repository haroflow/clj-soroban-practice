(ns clj-soroban-practice.pages.clock 
  (:require
   [clj-soroban-practice.components.soroban :as soroban]
   [clojure.string :as string]))

(defn render [state]
  [:div.inline-flex.flex-row-gap
   (let [now (-> (:now state)
                 .toLocaleTimeString
                 (string/replace #":" ""))]
     (soroban/render now 6 nil))])