(ns hello-jvm-world.horse
	(:require [hello-jvm-world.permutations :as perm]))

(defn horse_ways []
	(perm/permutations_macro [-1, -2, 1, 2] 2 (fn [coll] (== (reduce + (map (fn [el] (Math/abs el)) coll)) 3))) )