(ns clj-soroban-practice.components.soroban 
  (:require
   [clj-soroban-practice.logic :as logic]))

(defn- bead [bead-value column-data column-index]
  [:div.bead-click-area {:on {:click [[:actions/bead-click bead-value column-data column-index]]}}
   [:div.bead]])

(defn- expander [active]
  [:div {:style {:flex-grow (if active 1 0)
                 :transition "flex-grow 0.2s"}}])

(defn- soroban-column [column-index number column-data]
  [:div.soroban-column
   [:div.soroban-section
    [:div.rod]
    [:div.top-section
     (expander (:5 column-data))
     (bead :5 column-data column-index)]
    [:div.separator]
    [:div.bottom-section
     (expander (#{0 5} number))
     (bead :1 column-data column-index)
     (expander (#{1 6} number))
     (bead :2 column-data column-index)
     (expander (#{2 7} number))
     (bead :3 column-data column-index)
     (expander (#{3 8} number))
     (bead :4 column-data column-index)]]
   [:p.number-indicator number]])

;; TODO add id or something else to allow multiple sorobans in the same page... maybe just a path to a number?
;; TODO add reset button
(defn render [number max-columns]
  (let [number (if (pos? number) number 0)
        max-columns (if (pos? max-columns) max-columns 1)
        columns (logic/number->columns number max-columns)]
    [:div.soroban
     (for [[i column] (map-indexed vector columns)]
       (soroban-column i (logic/column-data->number column) column))]))
