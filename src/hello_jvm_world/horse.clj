(ns hello-jvm-world.horse
	(:require [hello-jvm-world.permutations :as perm]))

(defmacro horse_ways_macro []
	(perm/make_permutations [-1, -2, 1, 2] 2 (fn [coll] (== (reduce + (map (fn [el] (Math/abs el)) coll)) 3))) )
(defn horse_ways [] (horse_ways_macro))