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
(base) mihildrozdov@MacBook-Pro-Mihil clojure-solutions % cat expression.clj 
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
(base) mihildrozdov@MacBook-Pro-Mihil clojure-solutions % cat /Users/mihildrozdov/Downloads/pars.clj
(defn CreateParser [cnst varr funcc]
  (fn parse [exp]
    (cond
      (number? exp) (cnst exp)
      (symbol? exp) (varr (str exp))
      :else (apply (funcc (first exp)) (map parse (rest exp))))))

; Dz10

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

(def m+ t+)
(def m- t-)
(def m* t*)
(def md td)

(def s+ t+)
(def s- t-)
(def s* t*)
(def sd td)

(defn scalar [a b] (apply + (mapv * a b)))

(defn vect [a b] [(- (* (nth a 1) (nth b 2)) (* (nth b 1) (nth a 2)))
                  (- (* (nth b 0) (nth a 2)) (* (nth a 0) (nth b 2)))
                  (- (* (nth a 0) (nth b 1)) (* (nth b 0) (nth a 1)))]
  )

(defn procedure [f] (fn [a b] (mapv (fn [n] (f n b)) a)))
(def v*s (procedure *))
(def m*s (procedure v*s))
(def m*v (procedure scalar))
(defn transpose [m] (apply mapv vector m))
(defn m*m [a b] (mapv (partial m*v (transpose b)) a))


; Dz11

; This file should be placed in clojure-solutions
; You may use it via (load-file "proto.clj")

(defn proto-get
  "Returns object property respecting the prototype chain"
  ([obj key] (proto-get obj key nil))
  ([obj key default]
   (cond
     (contains? obj key) (obj key)
     (contains? obj :prototype) (proto-get (obj :prototype) key default)
     :else default)))

(defn proto-call
  "Calls object method respecting the prototype chain"
  [this key & args]
  (apply (proto-get this key) this args))

(defn field [key]
  "Creates field"
  (fn
    ([this] (proto-get this key))
    ([this def] (proto-get this key def))))

(defn method
  "Creates method"
  [key] (fn [this & args] (apply proto-call this key args)))

(defn constructor
  "Defines constructor"
  [ctor prototype]
  (fn [& args] (apply ctor {:prototype prototype} args)))

(defmacro deffield
  "Defines field"
  [name]
  (let [key (keyword (subs (str name) 1))]
    `(defn ~name
       ([this#] (proto-get this# ~key))
       ([this# def#] (proto-get this# ~key def#)))))

(defmacro deffields
  "Defines multiple fields"
  [& names]
  `(do ~@(mapv (fn [name] `(deffield ~name)) names)))

(defmacro defmethod
  "Defines method"
  [name]
  (let [key (keyword (subs (str name) 1))]
    `(defn ~name [this# & args#] (apply proto-call this# ~key args#))))

(defmacro defmethods
  "Defines multiple methods"
  [& names]
  `(do ~@(mapv (fn [name] `(defmethod ~name)) names)))

(defn to-symbol [name] (symbol (str "_" name)))
(defmacro defconstructor
  "Defines constructor"
  [name fields prototype]
  `(do
     (deffields ~@(map to-symbol fields))
     (defn ~name ~fields
       (assoc {:prototype ~prototype}
         ~@(mapcat (fn [f] [(keyword f) f]) fields)))))

(defmacro defclass
  "Defines class"
  [name super fields & methods]
  (let [-name (fn [suffix] (fn [class] (symbol (str class "_" suffix))))
        proto-name (-name "proto")
        fields-name (-name "fields")
        method (fn [[name args body]] [(keyword name) `(fn ~(apply vector 'this args) ~body)])
        base-proto (if (= '_ super) {} {:prototype (proto-name super)})
        prototype (reduce (fn [m nab] (apply assoc m (method nab))) base-proto methods)
        public-prototype (proto-name name)
        public-fields (fields-name name)
        all-fields (vec (concat (if (= '_ super) [] (eval (fields-name super))) fields))]
    `(do
       (defmethods ~@(map (comp to-symbol first) methods))
       (deffields ~@(map to-symbol fields))
       (def ~public-prototype ~prototype)
       (def ~public-fields (quote ~all-fields))
       (defconstructor ~name ~all-fields ~public-prototype))))

(def _value (field :value))
(def _valueName (field :valueName))

(def evaluate (method :evaluate))
(def toString (method :toString))
(def diff (method :diff))

(def _op (field :op))
(def _opName (field :opName))
(def _opArgs (field :opArgs))
(def _diffFunction (field :diffFunction))

(def toStringSuffix (method :toStringSuffix))

(defn createExpressionProto [evaluate toString diff toStringSuffix]
  {:evaluate       evaluate
   :toString       toString
   :diff           diff
   :toStringSuffix toStringSuffix
   })

(declare ZERO)
(def Constant (constructor
                (fn [this value] (assoc this :value value))
                (createExpressionProto
                  (fn [this vars] (_value this))
                  (fn [this] (format "%.1f" (_value this)))
                  (fn [this valueDiff] ZERO)
                  toString)))

(def ZERO (Constant 0.0))
(def ONE (Constant 1.0))

(def Variable
  (constructor
    (fn [this valueName] (assoc this :valueName valueName))
    (createExpressionProto
      (fn [this vars] (get vars (clojure.string/lower-case (subs (_valueName this) 0 1))))
      (fn [this] (_valueName this))
      (fn [this valueDiff] (if (= (_valueName this) valueDiff) ONE ZERO))
      toString)))

(def Operation
  (constructor
    (fn [this op opName diffFunction] (assoc this :op op :opName opName :diffFunction diffFunction))
    (createExpressionProto
      (fn [this vars] (apply (_op this) (mapv #(evaluate % vars) (_opArgs this))))
      (fn [this] (str "(" (_opName this) " " (clojure.string/join " " (mapv toString (_opArgs this))) ")"))
      (fn [this diffVariable] ((_diffFunction this) (_opArgs this) (mapv #(diff % diffVariable) (_opArgs this))))
      (fn [this] (str "(" (clojure.string/join " " (mapv toStringSuffix (_opArgs this))) " " (_opName this) ")")))))

(defn createOperation [op opName diffFunction]
  (constructor
    (fn [this & opArgs]
      (assoc this :opArgs opArgs))
    (Operation op opName diffFunction)))

(defn createDiff [func]
  (fn [args args']
    (func args')))

(def Add
  (createOperation + "+" (createDiff #(apply Add %))))
(def Subtract
  (createOperation - "-" (createDiff #(apply Subtract %))))
(def Negate
  (createOperation - "negate" (createDiff #(apply Negate %))))

(def Multiply
  (createOperation * "*"
                   (fn [args args']
                      (Add (Multiply (nth args' 0) (nth args 1))
                           (Multiply (nth args 0) (nth args' 1))))))

(defn divDouble [a b]
  (/ (double a) b))

(def Divide
  (createOperation divDouble "/"
                   (fn [args args']
                      (Divide (Subtract (Multiply (nth args' 0) (nth args 1))
                                        (Multiply (nth args 0) (nth args' 1)))
                              (Multiply (nth args 1) (nth args 1))))))


;(def BitAnd (createOperation #(bit-and %1 %2) "&" nil))
;(def BitOr (createOperation #(bit-or %1 %2) "|" nil))
(def BitAnd (createOperation #(Double/longBitsToDouble (bit-and (Double/doubleToLongBits %1) (Double/doubleToLongBits %2))) "^" nil))
(def BitOr (createOperation #(Double/longBitsToDouble (bit-or (Double/doubleToLongBits %1) (Double/doubleToLongBits %2))) "^" nil))
(def BitXor (createOperation #(Double/longBitsToDouble (bit-xor (Double/doubleToLongBits %1) (Double/doubleToLongBits %2))) "^" nil))

;long a = Double.doubleToLongBits(5);
;long b = Double.doubleToLongBits(6);
;long c = a ^ b;
;System.out.println(a + " " + b + " " + c);
;double r = Double.longBitsToDouble(c);
;System.out.println(r);


;(println (evaluate (BitOr (Constant 5) (Constant 6)) {}))

(def Operators
  {'+ Add
   '- Subtract
   '* Multiply
   '/ Divide
   'negate Negate
   (symbol "&") BitAnd
   (symbol "|") BitOr
   (symbol "^") BitXor
   })

(defn parseObject [s]
  ((CreateParser Constant Variable Operators)
                       (read-string s)))

; Dz12

; This file should be placed in clojure-solutions
; You may use it via (load-file "parser.clj")

(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)


(defn _empty [value] (partial -return value))

(defn _char [p]
  (fn [[c & cs]]
    (if (and c (p c)) (-return c cs))))

(defn _map [f]
  (fn [result]
    (if (-valid? result)
      (-return (f (-value result)) (-tail result)))))

(defn _combine [f a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar)
        ((_map (partial f (-value ar)))
         ((force b) (-tail ar)))))))

(defn _either [a b]
  (fn [str]
    (let [ar ((force a) str)]
      (if (-valid? ar) ar ((force b) str)))))

(defn _parser [p]
  (fn [input]
    (-value ((_combine (fn [v _] v) p (_char #{\u0001})) (str input \u0001)))))
(mapv (_parser (_combine str (_char #{\a \b}) (_char #{\x}))) ["ax" "ax~" "bx" "bx~" "" "a" "x" "xa"])



(defn +char [chars] (_char (set chars)))
(defn +char-not [chars] (_char (comp not (set chars))))
(defn +map [f parser] (comp (_map f) parser))
(def +ignore (partial +map (constantly 'ignore)))

(defn iconj [coll value]
  (if (= value 'ignore) coll (conj coll value)))

(defn +seq [& ps]
  (reduce (partial _combine iconj) (_empty []) ps))

(defn +seqf [f & ps] (+map (partial apply f) (apply +seq ps)))

(defn +seqn [n & ps] (apply +seqf (fn [& vs] (nth vs n)) ps))

(defn +or [p & ps]
  (reduce (partial _either) p ps))

(defn +opt [p]
  (+or p (_empty nil)))

(defn +star [p]
  (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec)))

(defn +plus [p] (+seqf cons p (+star p)))

(defn +str [p] (+map (partial apply str) p))

(def +parser _parser)


(defn +collect [defs]
  (cond
    (empty? defs) ()
    (seq? (first defs)) (let [[[key args body] & tail] defs]
                          (cons
                            {:key key :args args :body body}
                            (+collect tail)))
    :else (let [[key body & tail] defs]
            (cons
              {:key key :args [] :synth true :body body}
              (+collect tail)))))

(defmacro defparser [name & rules]
  (let [collected (+collect rules)
        keys (set (map :key (filter :synth collected)))]
    (letfn [(rule [{key :key, args :args, body :body}] `(~key ~args ~(convert body)))
            (convert [value]
              (cond
                (seq? value) (map convert value)
                (char? value) `(+char ~(str value))
                (keys value) `(~value)
                :else value))]
      `(def ~name (letfn ~(mapv rule collected) (+parser (~(:key (last collected)))))))))


(def *space
  (+char " \t\n\r"))

(def *wSpace
  (+ignore (+star *space)))

(def *digits
  (+char "0123456789"))

(def *numbers
  (+map read-string (+str (+seq (+opt (+char "-")) (+str (+plus *digits)) (+char ".") *digits))))

(def *Chars
  (mapv char (range 32 128)))

(def *letters
  (+char (apply str (filter #(Character/isLetter %) *Chars))))

(defn *parseExpr [p]
  (+seqn 1 *wSpace (+char "(") (+seqf cons *wSpace p (+star (+seqn 0 *wSpace p))) *wSpace (+char ")")))

(def *OperaitonsChars
  (+or *letters (+char "+-*/&|^")))

(def *namesChar
  (+char "xyzXYZ"))

(def *Variables
  (+str (+seqf cons *wSpace *namesChar (+star *namesChar))))

(def *iOperations
  (+str (+seqf cons *wSpace *OperaitonsChars (+star *OperaitonsChars))))

(def parseObjectSuffix
  (letfn [(*expression []
            (delay (+or
                     (+map Constant *numbers)
                     (+map (comp Variable str) *Variables)
                     (+map (comp #(Operators %) symbol) *iOperations)
                     (+map #(apply (last %) (drop-last %)) (*parseExpr (*expression)))
                     )))]
    (+parser (+seqn 0 *wSpace (*expression) *wSpace))))
