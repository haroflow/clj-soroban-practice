(ns clj-soroban-practice.actions
  (:require
   [nexus.registry :as nxr]))

(nxr/register-action!
 ::initialize-db
 (fn [_ started-at]
   [[:effects/save [:started-at] started-at]]))