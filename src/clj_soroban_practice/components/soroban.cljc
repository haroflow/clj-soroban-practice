(ns clj-soroban-practice.components.soroban 
  (:require
   [clj-soroban-practice.utils :as utils]))

(defn- bead [bead-value padded-number column-index value-path]
  [:div.bead-click-area
   {:on {:click [[:actions/bead-click bead-value padded-number column-index value-path]]}}
   [:div.bead]])

(defn- expander [active]
  [:div {:style {:flex-grow (if active 1 0)
                 :transition "flex-grow 0.2s"}}])

(defn- soroban-column [padded-number column-index value-path]
  (let [number (parse-long (nth padded-number column-index))]
    [:div.soroban-column
     [:div.soroban-section
      [:div.rod]
      [:div.top-section
       (expander (>= number 5))
       (bead :5 padded-number column-index value-path)]
      [:div.separator]
      [:div.bottom-section
       (expander (#{0 5} number))
       (bead :1 padded-number column-index value-path)
       (expander (#{1 6} number))
       (bead :2 padded-number column-index value-path)
       (expander (#{2 7} number))
       (bead :3 padded-number column-index value-path)
       (expander (#{3 8} number))
       (bead :4 padded-number column-index value-path)]]
     [:p.number-indicator number]]))

;; TODO add reset button
(defn render [number max-columns value-path]
  (let [number (if (pos? number) number 0)
        max-columns (if (pos? max-columns) max-columns 1)
        padded-number (utils/pad-zero number max-columns)
        columns (max max-columns (count padded-number)) ; avoids hiding numbers
        ]
    [:div.soroban
     (for [i (range columns)]
       (soroban-column padded-number i value-path))]))
