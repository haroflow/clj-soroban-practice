(ns clj-soroban-practice.main-page 
  (:require
   [clj-soroban-practice.actions :as actions]))

(defn bead [bead-value column-data column-index]
  [:div {:style {:background-color "#b14809"
                 :width 50
                 :height 25
                 :margin 2
                 :border-radius 20}
         :on {:click [[::actions/bead-click bead-value column-data column-index]]}}])

(defn expander [active]
  [:div {:style {:flex-grow (if active 1 0)
                 :transition "flex-grow 0.2s"}}])

(defn number->column-data [number]
  {:1 (>= (mod number 5) 1)
   :2 (>= (mod number 5) 2)
   :3 (>= (mod number 5) 3)
   :4 (>= (mod number 5) 4)
   :5 (>= number 5)})

(defn soroban-column [column-index number column-data]
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
     (bead 5 column-data column-index)]
    
    ;; Separator
    [:div {:style {:background-color "#352424"
                   :width "100%"
                   :height 10}}]
    
    ;; Bottom section
    [:div {:style {:height 155
                   :display :flex
                   :flex-direction :column}}
     (expander (#{0 5} number))
     (bead 1 column-data column-index)
     (expander (#{1 6} number))
     (bead 2 column-data column-index)
     (expander (#{2 7} number))
     (bead 3 column-data column-index)
     (expander (#{3 8} number))
     (bead 4 column-data column-index)]]
   
   ;; Number indicator
   [:p {:style {:text-align :center :margin-top 3}} number]])

;; TODO alternatives?
(defn pad-zero [n len]
  #?(:clj  (String/format (str "%0" len "d") (into-array [n]))
     :cljs (.padStart (.toString n) len "0")))

(defn render [state]
  [:div
   [:h1 "Soroban Practice"]

   [:label "Max Columns"]
   [:input {:type :number
            :value (:max-columns state)
            :on {:input [[:effects/save [:max-columns] [:fmt/number [:event.target/value]]]]}}]
   
   [:label "Number"]
   [:input {:type :number
            :value (:number state)
            :on {:input [[:effects/save [:number] [:fmt/number [:event.target/value]]]]}}]

   [:div {:style {:display :flex}}
    (for [i (range (or (:max-columns state) 1))] ;; TODO refactor
      (let [max-columns (:max-columns state)
            padded (pad-zero (or (:number state) 0) max-columns)
            n (parse-long (get padded i))]
        (soroban-column i n (number->column-data n))))]])