(ns hello-jvm-world.permutations
	(:use [defun :only [defun]])
	(:require clojure.test))

(defn make_permutations_process [inp_set take_num condition res]
	(cond
		(= (count res) take_num) 
			(case (condition res) true res false :failed )
		(< (count res) take_num) 
			(map (fn [el] (make_permutations_process (filter #(not= % el) inp_set) take_num condition (conj res el))) inp_set )))

(defun flatten_proc
	([0 res] res)
	([take_num res]	(flatten_proc (dec take_num) (apply concat res))))

(defun make_permutations
	([	(inp_set :guard #(and (not= [] %) (vector? %)))
		(take_num :guard #(and (integer? %) (> % 0)))
		(condition :guard clojure.test/function?) ]
		(case (<= take_num (count inp_set)) true
			(->> 	(make_permutations_process inp_set take_num condition [])
					(flatten_proc (dec take_num))
					(filter #(not= % :failed))
					(vec)))))