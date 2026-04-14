(ns clj-soroban-practice.core
  (:require
   [clj-soroban-practice.actions]
   [clj-soroban-practice.effects]
   [clj-soroban-practice.pages.main-page :as main-page]
   [clj-soroban-practice.placeholders]
   [clojure.string :as string]
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

(defn- sync-route []
  (let [new-route (-> js/window.location.hash (string/split "#/") second keyword)]
    (nxr/dispatch store nil [[:actions/navigate new-route]])))

(defn- load-voices []
  (let [voices (.getVoices js/speechSynthesis)]
    (nxr/dispatch store nil [[:effects/save [:voices] voices]])))

(defn ^:export init []
  (println "init")
  (.addEventListener js/speechSynthesis "voiceschanged" (fn [_event] (load-voices)))
  (.addEventListener js/window "hashchange" (fn [_event] (sync-route)))
  (render)
  (nxr/dispatch store nil [[:effects/initialize-db (.getTime (js/Date.))]])
  (sync-route))

(defn ^:export ^:dev/after-load reload []
  (println "reload")
  (render)
  (nxr/dispatch store nil [[:effects/save [:started-at] (.getTime (js/Date.))]]))