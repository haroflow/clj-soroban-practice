(ns clj-soroban-practice.core
  (:require
   [clj-soroban-practice.actions]
   [clj-soroban-practice.effects]
   [clj-soroban-practice.pages.main-page :as main-page]
   [clj-soroban-practice.placeholders]
   [nexus.registry :as nxr]
   [replicant.dom :as r]))

(defonce ^:private store (atom {}))

(defn- render []
  (nxr/register-system->state! deref)
  (r/set-dispatch! #(nxr/dispatch store %1 %2))
  (let [app (.getElementById js/document "app")]
    (add-watch store ::render
               (fn [_ _ _ new-state]
                 (r/render app (main-page/render new-state))))))

(defn ^:export init []
  (println "init")
  (render)
  (nxr/dispatch store nil [[:effects/initialize-db (.getTime (js/Date.))]]))

(defn ^:export ^:dev/after-load reload []
  (println "reload")
  (render)
  (nxr/dispatch store nil [[:effects/save [:started-at] (.getTime (js/Date.))]]))