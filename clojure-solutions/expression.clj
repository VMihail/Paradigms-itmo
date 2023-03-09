; DZ10

(defn init [function]
  (fn [& Args]
    (fn [args]
      (apply function (map #(% args) Args)))))

(def add
  (init +))

(def subtract
  (init -))

(def multiply
  (init *))

(def negate
  (init -))

(def divide
  (init (fn ([c]
             (/ 1.0 c))
          ([e & args] (reduce #(/ (double %1) %2) e args)))))

(defn logarifm [a b]
  (/ (Math/log10 (Math/abs (double b))) (Math/log10 (Math/abs (double a)))))

(def log
  (init #(logarifm %1 %2)))

(def pow
  (init #(Math/pow %1 %2)))

(defn variable [name]
  (fn [args] (double (args name))))

(defn constant [value]
  (fn [args] (double value)))

(def operations
  {'+ add
   '- subtract
   '* multiply
   '/ divide
   'negate negate
   'log log
   'pow pow
   })

(defn parse [value]
  (cond
    (sequential? value) (apply (get operations (first value)) (map parse (rest value)))
    (symbol? value) (variable (str value))
    :else (constant value)))

(defn parseFunction [expression]
  (parse (read-string expression)))

(def expr (divide (constant 5) (constant 2)))
;(println (expr {}))
(def expr (add (variable "x") (negate (constant 2))))
;(println (expr {"x" 5}))
(def exr (pow (constant 2) (constant 3)))
;(println (exr {}))
(def expr (constant 10))
;(println (expr {}))
