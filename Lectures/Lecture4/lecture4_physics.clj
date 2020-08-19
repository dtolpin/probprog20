;; gorilla-repl.fileformat = 1

;; **
;;; # 2D physics example from lecture 4
;; **

;; **
;;; _A version with stochastic conditioning_
;; **

;; @@
;; nstools did not work for me, something is broken and I haven't succeeded in fixing so far.
;; Rewrote this fragment using plain Clojure ns form.
(ns bounce-worksheet
  (:require [gorilla-plot.core :as plot])
  (:use [anglican core emit runtime
         [state :only [get-predicts get-log-weight get-result]]])
     (:require [org.nfrac.cljbox2d.core :refer [position]]))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
;; I split the above fragment to avoid re-declaring the namespace every time
(require '[bounce :refer [create-world
                          show-world-simulation
                          simulate-world
                          display-static-world
                          balls-in-box]] 
         :reload)
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def bumper-location1 (list))

(show-world-simulation bumper-location1)
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/bumper-location1</span>","value":"#'bounce-worksheet/bumper-location1"},{"type":"html","content":"<span class='clj-unkown'>#object[quil.Applet 0x148b0a0d &quot;quil.Applet[panel0,0,0,600x500,layout=java.awt.FlowLayout]&quot;]</span>","value":"#object[quil.Applet 0x148b0a0d \"quil.Applet[panel0,0,0,600x500,layout=java.awt.FlowLayout]\"]"}],"value":"[#'bounce-worksheet/bumper-location1,#object[quil.Applet 0x148b0a0d \"quil.Applet[panel0,0,0,600x500,layout=java.awt.FlowLayout]\"]]"}
;; <=

;; @@
(def bumper-location2 (list (list -3 6) (list 7 4)))

(show-world-simulation bumper-location2)
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/bumper-location2</span>","value":"#'bounce-worksheet/bumper-location2"},{"type":"html","content":"<span class='clj-unkown'>#object[quil.Applet 0x6e1c7f84 &quot;quil.Applet[panel1,0,0,600x500,layout=java.awt.FlowLayout]&quot;]</span>","value":"#object[quil.Applet 0x6e1c7f84 \"quil.Applet[panel1,0,0,600x500,layout=java.awt.FlowLayout]\"]"}],"value":"[#'bounce-worksheet/bumper-location2,#object[quil.Applet 0x6e1c7f84 \"quil.Applet[panel1,0,0,600x500,layout=java.awt.FlowLayout]\"]]"}
;; <=

;; @@
(def bumper-location-example
  (list (list -3 6) (list 0.8 5) (list 7 4) (list 12 3)))

(show-world-simulation bumper-location-example)
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/bumper-location-example</span>","value":"#'bounce-worksheet/bumper-location-example"},{"type":"html","content":"<span class='clj-unkown'>#object[quil.Applet 0xe543edf &quot;quil.Applet[panel2,0,0,600x500,layout=java.awt.FlowLayout]&quot;]</span>","value":"#object[quil.Applet 0xe543edf \"quil.Applet[panel2,0,0,600x500,layout=java.awt.FlowLayout]\"]"}],"value":"[#'bounce-worksheet/bumper-location-example,#object[quil.Applet 0xe543edf \"quil.Applet[panel2,0,0,600x500,layout=java.awt.FlowLayout]\"]]"}
;; <=

;; @@
(def example-world
  (create-world bumper-location-example))

(def example-world-final-state 
  (simulate-world example-world))

(balls-in-box example-world-final-state)
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/example-world</span>","value":"#'bounce-worksheet/example-world"},{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/example-world-final-state</span>","value":"#'bounce-worksheet/example-world-final-state"}],"value":"[#'bounce-worksheet/example-world,#'bounce-worksheet/example-world-final-state]"},{"type":"html","content":"<span class='clj-unkown'>0</span>","value":"0"}],"value":"[[#'bounce-worksheet/example-world,#'bounce-worksheet/example-world-final-state],0]"}
;; <=

;; @@
(with-primitive-procedures
  [create-world simulate-world balls-in-box]
  (defquery physics0 []
    (let [n-bumpers 8
          f (fn [] (list 
                     (sample (uniform-continuous -5 14))
                     (sample (uniform-continuous 0 10))))
          bs (repeatedly n-bumpers f)
          w0 (create-world bs)
          w1 (simulate-world w0)
          num-balls (balls-in-box w1)]
      (list num-balls bs))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/physics0</span>","value":"#'bounce-worksheet/physics0"}
;; <=

;; @@
(def lazy-samples0 
  (doquery :importance physics0 []))
(def samples0 
  (map :result (take-nth 10 (take 2000 (drop 1000 lazy-samples0))))) 
(def best-sample0 
  (reduce (fn [acc x] (if (> (first x) (first acc)) x acc)) 
          samples0))
best-sample0
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/lazy-samples0</span>","value":"#'bounce-worksheet/lazy-samples0"},{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/samples0</span>","value":"#'bounce-worksheet/samples0"}],"value":"[#'bounce-worksheet/lazy-samples0,#'bounce-worksheet/samples0]"},{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/best-sample0</span>","value":"#'bounce-worksheet/best-sample0"}],"value":"[[#'bounce-worksheet/lazy-samples0,#'bounce-worksheet/samples0],#'bounce-worksheet/best-sample0]"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-unkown'>1</span>","value":"1"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>10.305420494927546</span>","value":"10.305420494927546"},{"type":"html","content":"<span class='clj-double'>9.588391659903625</span>","value":"9.588391659903625"}],"value":"(10.305420494927546 9.588391659903625)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>3.4844753909507107</span>","value":"3.4844753909507107"},{"type":"html","content":"<span class='clj-double'>2.0646000723020763</span>","value":"2.0646000723020763"}],"value":"(3.4844753909507107 2.0646000723020763)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>-0.9820216446274315</span>","value":"-0.9820216446274315"},{"type":"html","content":"<span class='clj-double'>1.7939724465821705</span>","value":"1.7939724465821705"}],"value":"(-0.9820216446274315 1.7939724465821705)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>2.318456538467948</span>","value":"2.318456538467948"},{"type":"html","content":"<span class='clj-double'>5.114940953226819</span>","value":"5.114940953226819"}],"value":"(2.318456538467948 5.114940953226819)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>12.933503740903923</span>","value":"12.933503740903923"},{"type":"html","content":"<span class='clj-double'>4.821983335028888</span>","value":"4.821983335028888"}],"value":"(12.933503740903923 4.821983335028888)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>8.84569982647248</span>","value":"8.84569982647248"},{"type":"html","content":"<span class='clj-double'>0.10462060838335674</span>","value":"0.10462060838335674"}],"value":"(8.84569982647248 0.10462060838335674)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>10.599481722995883</span>","value":"10.599481722995883"},{"type":"html","content":"<span class='clj-double'>9.55284853071311</span>","value":"9.55284853071311"}],"value":"(10.599481722995883 9.55284853071311)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>9.828752646592678</span>","value":"9.828752646592678"},{"type":"html","content":"<span class='clj-double'>5.1460658090056555</span>","value":"5.1460658090056555"}],"value":"(9.828752646592678 5.1460658090056555)"}],"value":"((10.305420494927546 9.588391659903625) (3.4844753909507107 2.0646000723020763) (-0.9820216446274315 1.7939724465821705) (2.318456538467948 5.114940953226819) (12.933503740903923 4.821983335028888) (8.84569982647248 0.10462060838335674) (10.599481722995883 9.55284853071311) (9.828752646592678 5.1460658090056555))"}],"value":"(1 ((10.305420494927546 9.588391659903625) (3.4844753909507107 2.0646000723020763) (-0.9820216446274315 1.7939724465821705) (2.318456538467948 5.114940953226819) (12.933503740903923 4.821983335028888) (8.84569982647248 0.10462060838335674) (10.599481722995883 9.55284853071311) (9.828752646592678 5.1460658090056555)))"}],"value":"[[[#'bounce-worksheet/lazy-samples0,#'bounce-worksheet/samples0],#'bounce-worksheet/best-sample0],(1 ((10.305420494927546 9.588391659903625) (3.4844753909507107 2.0646000723020763) (-0.9820216446274315 1.7939724465821705) (2.318456538467948 5.114940953226819) (12.933503740903923 4.821983335028888) (8.84569982647248 0.10462060838335674) (10.599481722995883 9.55284853071311) (9.828752646592678 5.1460658090056555)))]"}
;; <=

;; @@
(show-world-simulation (first (rest best-sample0)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#object[quil.Applet 0x9783874 &quot;quil.Applet[panel12,0,0,600x500,layout=java.awt.FlowLayout]&quot;]</span>","value":"#object[quil.Applet 0x9783874 \"quil.Applet[panel12,0,0,600x500,layout=java.awt.FlowLayout]\"]"}
;; <=

;; @@
(with-primitive-procedures
  [create-world simulate-world balls-in-box]
  (defquery physics1 []
    (let [n-bumpers 8
          f (fn [] (list 
                     (sample (uniform-continuous -5 14))
                     (sample (uniform-continuous 0 10))))
          bs (repeatedly n-bumpers f)
          w0 (create-world bs)
          w1 (simulate-world w0)
          num-balls (balls-in-box w1)]
      (observe (normal num-balls 1) 20)
      (list num-balls bs))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/physics1</span>","value":"#'bounce-worksheet/physics1"}
;; <=

;; @@
(def lazy-samples1
  (doquery :lmh physics1 []))
(def samples1 
  (map :result (take-nth 10 (take 2000 (drop 1000 lazy-samples1)))))
(def best-sample1
  (reduce (fn [acc x] (if (> (first x) (first acc)) x acc))
          samples1))
best-sample1
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/best-sample1</span>","value":"#'bounce-worksheet/best-sample1"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-unkown'>0</span>","value":"0"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>2.2813094250642045</span>","value":"2.2813094250642045"},{"type":"html","content":"<span class='clj-double'>5.788361603230571</span>","value":"5.788361603230571"}],"value":"(2.2813094250642045 5.788361603230571)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>10.043024359353142</span>","value":"10.043024359353142"},{"type":"html","content":"<span class='clj-double'>9.123235108954454</span>","value":"9.123235108954454"}],"value":"(10.043024359353142 9.123235108954454)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>9.157555234169347</span>","value":"9.157555234169347"},{"type":"html","content":"<span class='clj-double'>8.431794169102817</span>","value":"8.431794169102817"}],"value":"(9.157555234169347 8.431794169102817)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>8.43032961077778</span>","value":"8.43032961077778"},{"type":"html","content":"<span class='clj-double'>2.4676060204834482</span>","value":"2.4676060204834482"}],"value":"(8.43032961077778 2.4676060204834482)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>6.848086724122507</span>","value":"6.848086724122507"},{"type":"html","content":"<span class='clj-double'>2.8298333396027586</span>","value":"2.8298333396027586"}],"value":"(6.848086724122507 2.8298333396027586)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>8.61759829532771</span>","value":"8.61759829532771"},{"type":"html","content":"<span class='clj-double'>3.322702589374622</span>","value":"3.322702589374622"}],"value":"(8.61759829532771 3.322702589374622)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>7.738918971337561</span>","value":"7.738918971337561"},{"type":"html","content":"<span class='clj-double'>1.0521424135475366</span>","value":"1.0521424135475366"}],"value":"(7.738918971337561 1.0521424135475366)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>0.8920793582330697</span>","value":"0.8920793582330697"},{"type":"html","content":"<span class='clj-double'>7.541763620642823</span>","value":"7.541763620642823"}],"value":"(0.8920793582330697 7.541763620642823)"}],"value":"((2.2813094250642045 5.788361603230571) (10.043024359353142 9.123235108954454) (9.157555234169347 8.431794169102817) (8.43032961077778 2.4676060204834482) (6.848086724122507 2.8298333396027586) (8.61759829532771 3.322702589374622) (7.738918971337561 1.0521424135475366) (0.8920793582330697 7.541763620642823))"}],"value":"(0 ((2.2813094250642045 5.788361603230571) (10.043024359353142 9.123235108954454) (9.157555234169347 8.431794169102817) (8.43032961077778 2.4676060204834482) (6.848086724122507 2.8298333396027586) (8.61759829532771 3.322702589374622) (7.738918971337561 1.0521424135475366) (0.8920793582330697 7.541763620642823)))"}],"value":"[#'bounce-worksheet/best-sample1,(0 ((2.2813094250642045 5.788361603230571) (10.043024359353142 9.123235108954454) (9.157555234169347 8.431794169102817) (8.43032961077778 2.4676060204834482) (6.848086724122507 2.8298333396027586) (8.61759829532771 3.322702589374622) (7.738918971337561 1.0521424135475366) (0.8920793582330697 7.541763620642823)))]"},{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/lazy-samples1</span>","value":"#'bounce-worksheet/lazy-samples1"}],"value":"[[#'bounce-worksheet/best-sample1,(0 ((2.2813094250642045 5.788361603230571) (10.043024359353142 9.123235108954454) (9.157555234169347 8.431794169102817) (8.43032961077778 2.4676060204834482) (6.848086724122507 2.8298333396027586) (8.61759829532771 3.322702589374622) (7.738918971337561 1.0521424135475366) (0.8920793582330697 7.541763620642823)))],#'bounce-worksheet/lazy-samples1]"},{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/samples1</span>","value":"#'bounce-worksheet/samples1"}],"value":"[[[#'bounce-worksheet/best-sample1,(0 ((2.2813094250642045 5.788361603230571) (10.043024359353142 9.123235108954454) (9.157555234169347 8.431794169102817) (8.43032961077778 2.4676060204834482) (6.848086724122507 2.8298333396027586) (8.61759829532771 3.322702589374622) (7.738918971337561 1.0521424135475366) (0.8920793582330697 7.541763620642823)))],#'bounce-worksheet/lazy-samples1],#'bounce-worksheet/samples1]"},{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/best-sample1</span>","value":"#'bounce-worksheet/best-sample1"}],"value":"[[[[#'bounce-worksheet/best-sample1,(0 ((2.2813094250642045 5.788361603230571) (10.043024359353142 9.123235108954454) (9.157555234169347 8.431794169102817) (8.43032961077778 2.4676060204834482) (6.848086724122507 2.8298333396027586) (8.61759829532771 3.322702589374622) (7.738918971337561 1.0521424135475366) (0.8920793582330697 7.541763620642823)))],#'bounce-worksheet/lazy-samples1],#'bounce-worksheet/samples1],#'bounce-worksheet/best-sample1]"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-unkown'>20</span>","value":"20"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>-4.456097693810728</span>","value":"-4.456097693810728"},{"type":"html","content":"<span class='clj-double'>1.6553462064030988</span>","value":"1.6553462064030988"}],"value":"(-4.456097693810728 1.6553462064030988)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>6.935040246215556</span>","value":"6.935040246215556"},{"type":"html","content":"<span class='clj-double'>0.7519240794436888</span>","value":"0.7519240794436888"}],"value":"(6.935040246215556 0.7519240794436888)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>1.127714764912355</span>","value":"1.127714764912355"},{"type":"html","content":"<span class='clj-double'>6.211719381976934</span>","value":"6.211719381976934"}],"value":"(1.127714764912355 6.211719381976934)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>11.772572631348455</span>","value":"11.772572631348455"},{"type":"html","content":"<span class='clj-double'>8.792670218628565</span>","value":"8.792670218628565"}],"value":"(11.772572631348455 8.792670218628565)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>-1.3242913891126906</span>","value":"-1.3242913891126906"},{"type":"html","content":"<span class='clj-double'>0.43075348294855376</span>","value":"0.43075348294855376"}],"value":"(-1.3242913891126906 0.43075348294855376)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>-2.4473131164381465</span>","value":"-2.4473131164381465"},{"type":"html","content":"<span class='clj-double'>2.9243102569000046</span>","value":"2.9243102569000046"}],"value":"(-2.4473131164381465 2.9243102569000046)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>1.9728184660659718</span>","value":"1.9728184660659718"},{"type":"html","content":"<span class='clj-double'>8.504981844078197</span>","value":"8.504981844078197"}],"value":"(1.9728184660659718 8.504981844078197)"},{"type":"list-like","open":"<span class='clj-list'>(</span>","close":"<span class='clj-list'>)</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-double'>10.92844930810266</span>","value":"10.92844930810266"},{"type":"html","content":"<span class='clj-double'>4.935858168232938</span>","value":"4.935858168232938"}],"value":"(10.92844930810266 4.935858168232938)"}],"value":"((-4.456097693810728 1.6553462064030988) (6.935040246215556 0.7519240794436888) (1.127714764912355 6.211719381976934) (11.772572631348455 8.792670218628565) (-1.3242913891126906 0.43075348294855376) (-2.4473131164381465 2.9243102569000046) (1.9728184660659718 8.504981844078197) (10.92844930810266 4.935858168232938))"}],"value":"(20 ((-4.456097693810728 1.6553462064030988) (6.935040246215556 0.7519240794436888) (1.127714764912355 6.211719381976934) (11.772572631348455 8.792670218628565) (-1.3242913891126906 0.43075348294855376) (-2.4473131164381465 2.9243102569000046) (1.9728184660659718 8.504981844078197) (10.92844930810266 4.935858168232938)))"}],"value":"[[[[[#'bounce-worksheet/best-sample1,(0 ((2.2813094250642045 5.788361603230571) (10.043024359353142 9.123235108954454) (9.157555234169347 8.431794169102817) (8.43032961077778 2.4676060204834482) (6.848086724122507 2.8298333396027586) (8.61759829532771 3.322702589374622) (7.738918971337561 1.0521424135475366) (0.8920793582330697 7.541763620642823)))],#'bounce-worksheet/lazy-samples1],#'bounce-worksheet/samples1],#'bounce-worksheet/best-sample1],(20 ((-4.456097693810728 1.6553462064030988) (6.935040246215556 0.7519240794436888) (1.127714764912355 6.211719381976934) (11.772572631348455 8.792670218628565) (-1.3242913891126906 0.43075348294855376) (-2.4473131164381465 2.9243102569000046) (1.9728184660659718 8.504981844078197) (10.92844930810266 4.935858168232938)))]"}
;; <=

;; @@
(show-world-simulation (first (rest best-sample1)))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-unkown'>#object[quil.Applet 0x404ef36d &quot;quil.Applet[panel5,0,0,600x500,layout=java.awt.FlowLayout]&quot;]</span>","value":"#object[quil.Applet 0x404ef36d \"quil.Applet[panel5,0,0,600x500,layout=java.awt.FlowLayout]\"]"},{"type":"html","content":"<span class='clj-unkown'>#object[quil.Applet 0x540f3abe &quot;quil.Applet[panel6,0,0,600x500,layout=java.awt.FlowLayout]&quot;]</span>","value":"#object[quil.Applet 0x540f3abe \"quil.Applet[panel6,0,0,600x500,layout=java.awt.FlowLayout]\"]"}],"value":"[#object[quil.Applet 0x404ef36d \"quil.Applet[panel5,0,0,600x500,layout=java.awt.FlowLayout]\"],#object[quil.Applet 0x540f3abe \"quil.Applet[panel6,0,0,600x500,layout=java.awt.FlowLayout]\"]]"}
;; <=

;; **
;;; ## physics1 with stochastic conditioning
;;; 
;;; I modified simulate-world so that it gets an optional extra argument --- 20 random numbers. This way, I can generate stochastic conditioning in the query.
;; **

;; @@
(with-primitive-procedures
  [create-world simulate-world balls-in-box]
  (defquery physics1s []
    (let [n-bumpers 8
          f (fn [] (list 
                     (sample (uniform-continuous -5 14))
                     (sample (uniform-continuous 0 10))))
          bs (repeatedly n-bumpers f)
          w0 (create-world bs)
          w1 (simulate-world w0 
                             ;; stochastic conditioning
                             (vec (repeatedly 
                                    20 
                                    (fn [] 
                                      (sample (tag :stochastic 
                                                   (uniform-continuous 0 1)))))))
          num-balls (balls-in-box w1)]
      (observe (normal num-balls 1) 20)
      (list num-balls bs))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/physics1s</span>","value":"#'bounce-worksheet/physics1s"}
;; <=

;; **
;;; Now, sampling randomness in the initial position is a part of the chain, so more iterations are required in SMH for fair comparison.
;; **

;; @@
(def lazy-samples1s 
  (doquery :smh physics1s []))
(def samples1s 
  (map :result (take-nth 10 (take 4000 (drop 2000 lazy-samples1s)))))
(def best-sample1s
  (reduce (fn [acc x] (if (> (first x) (first acc)) x acc))
          samples1s))
best-sample1s
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/lazy-samples1s</span>","value":"#'bounce-worksheet/lazy-samples1s"},{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/samples1s</span>","value":"#'bounce-worksheet/samples1s"}],"value":"[#'bounce-worksheet/lazy-samples1s,#'bounce-worksheet/samples1s]"}
;; <=

;; @@
(show-world-simulation (first (rest best-sample1s)))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-unkown'>#object[quil.Applet 0x2ac7d3f6 &quot;quil.Applet[panel7,0,0,600x500,layout=java.awt.FlowLayout]&quot;]</span>","value":"#object[quil.Applet 0x2ac7d3f6 \"quil.Applet[panel7,0,0,600x500,layout=java.awt.FlowLayout]\"]"}
;; <=

;; **
;;; End of physics1-sc
;; **

;; @@
(with-primitive-procedures
  [create-world simulate-world balls-in-box]
  (defquery physics2 []
    (let [n-bumpers (sample (poisson 6))
          f (fn [] (list 
                     (sample (uniform-continuous -5 14))
                     (sample (uniform-continuous 0 10))))
          bs (repeatedly n-bumpers f)
          w0 (create-world bs) 
          w1 (simulate-world w0)
          num-balls (balls-in-box w1)]
      (observe (normal n-bumpers 2) 0)
      (observe (normal num-balls 1) 20)
      (list num-balls bs))))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/physics2</span>","value":"#'bounce-worksheet/physics2"}
;; <=

;; **
;;; 
;; **

;; @@
(def lazy-samples2 
  (doquery :lmh physics2 []))
(def samples2 
  (map :result (take-nth 10 (take 2000 (drop 1000 lazy-samples2)))))
(defn is-better [x y]
  (let [num-bumpers-less (< (count (second x)) (count (second y)))
        num-balls-more (> (first x) (first y))
        num-balls-equal (= (first x) (first y))
        x-above-threshold (> (first x) 15)
        y-above-threshold (> (first x) 15)]
    (or (and x-above-threshold num-bumpers-less)
        (and num-balls-equal num-bumpers-less)
        num-balls-more)))
(def best-sample2 
  (reduce (fn [acc x] (if (is-better x acc) x acc)) 
          samples2))
best-sample2
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/lazy-samples2</span>","value":"#'bounce-worksheet/lazy-samples2"},{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/samples2</span>","value":"#'bounce-worksheet/samples2"}],"value":"[#'bounce-worksheet/lazy-samples2,#'bounce-worksheet/samples2]"},{"type":"html","content":"<span class='clj-var'>#&#x27;bounce-worksheet/is-better</span>","value":"#'bounce-worksheet/is-better"}],"value":"[[#'bounce-worksheet/lazy-samples2,#'bounce-worksheet/samples2],#'bounce-worksheet/is-better]"}
;; <=

;; @@
(show-world-simulation (first (rest best-sample2)))
;; @@

;; **
;;; # physics2 with stochastic conditioning
;; **

;; @@
(with-primitive-procedures
  [create-world simulate-world balls-in-box]
  (defquery physics2s []
    (let [n-bumpers (sample (poisson 6))
          f (fn [] (list 
                     (sample (uniform-continuous -5 14))
                     (sample (uniform-continuous 0 10))))
          bs (repeatedly n-bumpers f)
          w0 (create-world bs) 
          bs (repeatedly n-bumpers f)
          w0 (create-world bs)
          w1 (simulate-world w0 
                             ;; stochastic conditioning
                             (vec (repeatedly 
                                    20 
                                    (fn [] 
                                      (sample (tag :stochastic 
                                                   (uniform-continuous 0 1)))))))num-balls (balls-in-box w1)]
      (observe (normal n-bumpers 2) 0)
      (observe (normal num-balls 1) 20)
      (list num-balls bs))))
;; @@

;; @@
(def lazy-samples2s
  (doquery :smh physics2s []))
(def samples2s 
  (map :result (take-nth 10 (take 400 (drop 20 lazy-samples2s)))))
(def best-sample2s
  (reduce (fn [acc x] (if (is-better x acc) x acc)) 
          samples2s))
best-sample2s
;; @@

;; @@
(show-world-simulation (first (rest best-sample2s)))
;; @@
