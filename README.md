# clj-soroban-practice

This is a small app for practicing Soroban.

The goal was to learn more about ClojureScript using **Replicant** and **Nexus**, and this project seemed like a perfect fit for that.

Currently it features 3 modes:
- **Free mode:** a digital soroban for learning the basics.
- **Speed training:** practice on a physical soroban with voice dictation.
- **Clock:** a fun visualization of a clock using a soroban, this was primarily an exercise in handling timeouts/intervals in Replicant.

## Pre-requisites

- npm (tested on 10.8.2)
- node (tested on v20.19.5)

You might need these:
- Java JDK
- Clojure CLI (tested on 1.12.4)

## How to run for development

Run in watch mode:
```
npx shadow-cljs watch app
```

Open browser at http://localhost:8080

## Known problems

The available voices are dependent on the OS installation. On Linux, voices might not be configured by default.