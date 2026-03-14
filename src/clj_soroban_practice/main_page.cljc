(ns clj-soroban-practice.main-page)

(defn bead [active]
  [:div {:style {:background-color "#b14809"
                 :width 50
                 :height 25
                 :margin 2
                 :visibility (if active :visible :hidden)
                 :border-radius 20}}])

(defn soroban-column [number]
  [:div
   [:div
    {:style {:border "5px solid #352424"
             :width :fit-content
             :display :flex
             :flex-direction :column
             :gap 2
             :position :relative}}
    [:div {:style {:background-color "#503b3b"
                   :position :absolute
                   :z-index -1
                   :width 20
                   :top 0
                   :left 17
                   :height "100%"}}]
    (bead (< number 5))
    (bead (>= number 5))
    [:div {:style {:background-color "#352424"
                   :width "100%"
                   :height 10}}]
    (bead (#{1 2 3 4 6 7 8 9} number))
    (bead (#{0 2 3 4 5 7 8 9} number))
    (bead (#{0 1 3 4 5 6 8 9} number))
    (bead (#{0 1 2 4 5 6 7 9} number))
    (bead (#{0 1 2 3 5 6 7 8} number))]
   [:p {:style {:text-align :center :margin-top 3}} number]])

(defn render [state]
  [:div
   [:h1 "Soroban Practice"]

   [:input {:type :number
            :value (:number state)
            :on {:input [[:effects/save [:number] [:fmt/number [:event.target/value]]]]}}]
   [:p "Number:" (:number state)]

   [:div {:style {:display :flex}}
    (for [i (.toString (or (:number state) 0))]
      (soroban-column (parse-long i)))]
   
   [:p "Started at: " (:started-at state)]])