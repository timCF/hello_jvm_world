(ns hello-jvm-world.permutations)


(defn make_permutations_process [inp_set take_num condition res]
		(println res )
		(cond
			(== (count res) take_num) 
				(case (condition res) true res false :failed )
			(< (count res) take_num) 
				(map 	(fn [el] (make_permutations_process (filter (fn [elem] (not= elem el)) inp_set) take_num condition (conj res el)))
						inp_set )))

(defn flatten_proc [take_num res]
	(case take_num
		0 res
		(flatten_proc (dec take_num) (apply concat res))))

(defn make_permutations [inp_set take_num condition]
	(->> 	(make_permutations_process inp_set take_num condition [])
			(flatten_proc (dec take_num))
			(filter (fn [el] (not= el :failed)))
			(vec)))

(defmacro permutations_macro [input_set size_of_perms condition]
	(make_permutations input_set size_of_perms condition ))