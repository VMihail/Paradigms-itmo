(defn create [f] (fn [first second]
                   (if (vector? first)
                     (mapv (create f) first second)
                     (f first second)
                     )
                   ))

(def t+ (create +))
(def t- (create -))
(def t* (create *))
(def td (create /))

(def v+ t+)
(def v- t-)
(def v* t*)
(def vd td)

"(def a [1 2])
(def b [1 3])

(println (v+ a b))
(println (v- a b))
(println (v* a b))
(println (vd a b))"

(def m+ t+)
(def m- t-)
(def m* t*)
(def md td)

(def s+ t+)
(def s- t-)
(def s* t*)
(def sd td)
"(println (s* [[1 2] 3] [[4 5] 6]))"

(defn scalar [a b] (apply + (mapv * a b)))

(defn vect [a b] [(- (* (nth a 1) (nth b 2)) (* (nth b 1) (nth a 2)))
                  (- (* (nth b 0) (nth a 2)) (* (nth a 0) (nth b 2)))
                  (- (* (nth a 0) (nth b 1)) (* (nth b 0) (nth a 1)))]
  )

"(def a [[1 2] [3 4]])
(def b [[1 4] [3 2]])
(println (m+ a b))
(println (m* a b))"

(defn procedure [f] (fn [a b] (mapv (fn [n] (f n b)) a)))
(def v*s (procedure *))
(def m*s (procedure v*s))
(def m*v (procedure scalar))
(defn transpose [m] (apply mapv vector m))
(defn m*m [a b] (mapv (partial m*v (transpose b)) a))

"(println (m*m a b))"
