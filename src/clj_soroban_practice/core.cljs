(ns clj-soroban-practice.core
  (:require
   [clj-soroban-practice.actions :as actions]
   [clj-soroban-practice.effects]
   [clj-soroban-practice.main-page :as main-page]
   [clj-soroban-practice.placeholders]
   [nexus.registry :as nxr]
   [replicant.dom :as r]))

(defonce store (atom {}))

(defn- render []
  (nxr/register-system->state! deref)
  (r/set-dispatch! #(nxr/dispatch store %1 %2))
  (add-watch store ::render #(r/render js/document.body (main-page/render %4)))
  (nxr/dispatch store nil [[::actions/initialize-db (.getTime (js/Date.))]]))

(defn ^:export init []
  (println "init")
  (render))

(defn ^:export ^:dev/after-load reload []
  (println "reload")
  (render))