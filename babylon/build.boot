(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.8.2" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "3.0.7")
(def +version+ (str +lib-version+ "-0"))

	
(task-options!
 pom  {:project     'cljsjs/babylon
       :version     +version+
       :description "Microsoft's Babylon.js 3d game engine"
       :url         "http://www.babylonjs.com/"
       :license     {"Apache" "http://www.apache.org/licenses/LICENSE-2.0"}
       :scm         {:url "https://github.com/cljsjs/packages"}})

(deftask package []
  (comp
    (download :url (str "https://unpkg.com/babylonjs@" +lib-version+ "/dist/preview%20release/babylon.js"))
    (sift :move {#"babylon.js" "cljsjs/babylon/development/babylon.inc.js"})
    (download :url (str "https://unpkg.com/babylonjs@" +lib-version+ "/dist/preview%20release/babylon.max.js"))
    (sift :move {#"babylon.js" "cljsjs/babylon/production/babylon.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.babylon")
    (pom)
    (jar)))
