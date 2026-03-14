(ns clj-soroban-practice.placeholders 
  (:require
   [nexus.registry :as nxr]))

(nxr/register-placeholder!
 :event.target/value
 (fn [{:replicant/keys [dom-event]}]
   (some-> dom-event .-target .-value)))

(nxr/register-placeholder!
 :fmt/number
 (fn [_ value]
   (or (some-> value parse-long) 0)))