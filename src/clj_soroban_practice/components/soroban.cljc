(ns clj-soroban-practice.components.soroban 
  (:require
   [clj-soroban-practice.logic :as logic]))

(defn- bead [bead-value column-data column-index]
  [:div {:style {:background-color "#b14809"
                 :width 50
                 :height 25
                 :margin 2
                 :border-radius 20}
         :on {:click [[:actions/bead-click bead-value column-data column-index]]}}])

(defn- expander [active]
  [:div {:style {:flex-grow (if active 1 0)
                 :transition "flex-grow 0.2s"}}])

(defn- soroban-column [column-index number column-data]
  [:div
   [:div
    {:style {:border "5px solid #352424"
             :width :fit-content
             :display :flex
             :flex-direction :column
             :gap 2
             :position :relative}}
    ;; Rod
    [:div {:style {:background-color "#503b3b"
                   :position :absolute
                   :z-index -1
                   :width 20
                   :top 0
                   :left 17
                   :height "100%"}}]

    ;; Top section
    [:div {:style {:height 60
                   :display :flex
                   :flex-direction :column}}
     (expander (:5 column-data))
     (bead :5 column-data column-index)]

    ;; Separator
    [:div {:style {:background-color "#352424"
                   :width "100%"
                   :height 10}}]

    ;; Bottom section
    [:div {:style {:height 155
                   :display :flex
                   :flex-direction :column}}
     (expander (#{0 5} number))
     (bead :1 column-data column-index)
     (expander (#{1 6} number))
     (bead :2 column-data column-index)
     (expander (#{2 7} number))
     (bead :3 column-data column-index)
     (expander (#{3 8} number))
     (bead :4 column-data column-index)]]

   ;; Number indicator
   [:p {:style {:text-align :center :margin-top 3}} number]])

;; TODO add id or something else to allow multiple sorobans in the same page... maybe just a path to a number?
;; TODO add reset button
(defn render [number max-columns]
  (let [number (if (pos? number) number 0)
        max-columns (if (pos? max-columns) max-columns 1)
        columns (logic/number->columns number max-columns)]
    [:div {:style {:display :flex}}
     (for [[i column] (map-indexed vector columns)]
       (soroban-column i (logic/column-data->number column) column))]))
