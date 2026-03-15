(ns clj-soroban-practice.actions
  (:require
   [nexus.registry :as nxr]))

(nxr/register-action!
 ::initialize-db
 (fn [_ started-at]
   [[:effects/save [:started-at] started-at]
    [:effects/save [:max-columns] 6]
    [:effects/save [:number] 0]]))

;; TODO move to logic namespace?
(defn column-data->number [column-data]
  (+ (when (:1 column-data) 1)
     (when (:2 column-data) 1)
     (when (:3 column-data) 1)
     (when (:4 column-data) 1)
     (when (:5 column-data) 5)))

;; TODO this is a duplicate
(defn pad-zero [n len]
  #?(:clj  (String/format (str "%0" len "d") (into-array [n]))
     :cljs (.padStart (.toString n) len "0")))

;; TODO refactor, it's too long
(nxr/register-action!
 ::bead-click
 (fn [state bead-value column-data column-index]
   (let [kw (keyword (str bead-value))
         active? (get column-data kw)
         new-column-data (if active?
                           (case kw
                             :1 (assoc column-data :1 false :2 false :3 false :4 false)
                             :2 (assoc column-data :2 false :3 false :4 false)
                             :3 (assoc column-data :3 false :4 false)
                             :4 (assoc column-data :4 false)
                             :5 (assoc column-data :5 false))
                           (case kw
                             :1 (assoc column-data :1 true)
                             :2 (assoc column-data :1 true :2 true)
                             :3 (assoc column-data :1 true :2 true :3 true)
                             :4 (assoc column-data :1 true :2 true :3 true :4 true)
                             :5 (assoc column-data :5 true)))
         old-number (:number state)
         old-number-padded (pad-zero old-number (:max-columns state))
         old-number-str (vec old-number-padded)
         new-number-at-index (column-data->number new-column-data)
         new-number-str (apply str (assoc old-number-str column-index new-number-at-index))
         new-number (parse-long new-number-str)]
     [[:effects/save [:number] new-number]])))