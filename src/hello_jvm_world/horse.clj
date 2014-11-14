(ns hello-jvm-world.horse
	(:require [hello-jvm-world.permutations :as perm])
	(:use [defun :only [defun]]))

(defmacro horse_ways_macro []
	(perm/make_permutations [-1, -2, 1, 2] 2 (fn [coll] (= (reduce + (pmap #(Math/abs %) coll)) 3))))
(def horse_ways (horse_ways_macro))

(defrecord Position [x y])
(defrecord GameState [cur_pos, history])

(def printer (agent 0))

(defn generate_new_state [old_state way]
	(case (rand-int 10000) 
		1 (send printer (fn [_] (println (count (:history old_state)) (:history old_state) )))
		:ok)
	(->	(update-in old_state [:history]
			(fn [old_history] (conj old_history (:cur_pos old_state))))
		(update-in [:cur_pos :x]
			(fn [x] (+ x (first way))))
		(update-in [:cur_pos :y]
			(fn [y] (+ y (last way))))))
(defn is_valid_state [limit some_state]
	(let [x (:x (:cur_pos some_state)) y (:y (:cur_pos some_state))]
		(and (> x 0) (> y 0) (<= x limit) (<= y limit) (not-any? #(= (:cur_pos some_state) %) (:history some_state)))))

(defun game 
	([_, []] [])
	([limit, lst]
		(case (= (dec (* limit limit)) (count (:history (first lst))))
			true lst
			false (->>	(pmap (fn [old_state] 
							(pmap #(generate_new_state old_state %) horse_ways ))
						lst)
						(flatten)
						(filter #(is_valid_state limit %))
						(vec)
						(game limit)
						(flatten)
						(vec)))))

(defn init [x y limit]
	(case (and (integer? x) (integer? y) (integer? limit) (> x 0) (> y 0) (<= x limit) (<= y limit)) true
		(game limit [(->GameState (->Position x y) [])] )))