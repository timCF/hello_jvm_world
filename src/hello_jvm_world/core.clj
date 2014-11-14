(ns hello-jvm-world.core
  (:gen-class)
  (:require hello-jvm-world.horse))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (println "GOT PATHS : " (count (time (hello-jvm-world.horse/init 3 3 5)) )))