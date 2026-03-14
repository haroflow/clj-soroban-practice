(ns clj-soroban-practice.effects 
  (:require
   [nexus.registry :as nxr]))

(nxr/register-effect!
 :effects/save
 (fn [_ store path value]
   (swap! store assoc-in path value)))

