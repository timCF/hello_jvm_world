(ns hello-jvm-world.horse
	(:require [hello-jvm-world.permutations :as perm])
	(:use [defun :only [defun]]))

(defmacro horse_ways_macro []
	(perm/make_permutations [-1, -2, 1, 2] 2 (fn [coll] (= (reduce + (map #(Math/abs %) coll)) 3))))
(defn horse_ways [] (horse_ways_macro))

(defrecord Position [x y])
(defrecord GameState [cur_pos, history])

(defn generate_new_state [old_state way]
	(->	(update-in old_state [:history]
			(fn [old_history] (conj old_history (:cur_pos old_state))))
		(update-in [:cur_pos :x]
			(fn [x] (+ x (first way))))
		(update-in [:cur_pos :y]
			(fn [y] (+ y (last way))))))

(defun game 
	([_, []] [])
	([limit, lst]
		(case (= (dec (* limit limit)) (count (:history (first lst))))
			true lst
			false (->>	(map (fn [old_state] 
							(map #(generate_new_state old_state way) horse_ways))
						lst)
						(flatten)
						(filter is_valid_state)
						(game limit)
						(flatten)
						(vec)))))