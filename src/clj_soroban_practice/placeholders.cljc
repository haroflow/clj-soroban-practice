(ns clj-soroban-practice.placeholders 
  (:require
   [clj-soroban-practice.utils :as utils]
   [nexus.registry :as nxr]))

(nxr/register-placeholder!
 :event.target/value
 (fn [{:replicant/keys [dom-event]}]
   (some-> dom-event .-target .-value)))

(nxr/register-placeholder!
 :event.target/checked
 (fn [{:replicant/keys [dom-event]}]
   (some-> dom-event .-target .-checked)))

;; WARN parse-long cant parse "9999999999999999"
(nxr/register-placeholder!
 :fmt/number
 (fn [_ value]
   (or (some-> value parse-long) 0)))

(nxr/register-placeholder!
 :fmt/number-clamped
 (fn [_ value min-val max-val]
   (utils/clamp (or (some-> value parse-long) 0)
                min-val
                max-val)))