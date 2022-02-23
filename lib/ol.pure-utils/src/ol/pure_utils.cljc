(ns ol.pure-utils)

(defn conj-some
  "conj-some is very similar to conj but it will do nothing if x is nil."
  [coll x]
  (if (some? x) (conj coll x) coll))

(defn conj-truthy
  "conj-truthy is very similar to conj but it will do nothing if x is logical false"
  [coll x]
  (if x (conj coll x) coll))
