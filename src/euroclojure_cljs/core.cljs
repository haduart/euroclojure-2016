(ns euroclojure-cljs.core
  (:require [reagent.core :as reagent]
            reagent.dom
            webpack-bundle))

(enable-console-print!)

(defonce app-state
  (reagent/atom {:slide-index 0
                 :transition "forward"}))

(defn slide-1 []
  [:div "Slide 1"])

(defn slide-2 []
  [:div "Slide 2"])

(defn slide-3 []
  [:div "Slide 3"])

(defn slide-4 []
  [:div "Slide 4"])

(def slides [#'slide-1 #'slide-2 #'slide-3 #'slide-4])

(defn next-slide []
  (swap! app-state assoc :transition "forward")
  (swap! app-state update :slide-index inc))

(defn previous-slide []
  (swap! app-state assoc :transition "backward")
  (swap! app-state update :slide-index dec))

(defn on-key-down [e]
  (let [{:keys [slide-index]} @app-state]
    (case (.-keyCode e)
      37 (when (< 0 slide-index)
           (previous-slide))
      39 (when (> (count slides) (inc slide-index))
           (next-slide))
      :skip)))

(defn stepper-component [slide-index]
  [:> js/Stepper {:activeStep slide-index
                  :style {:width "90%"
                          :float "left"
                          :height "30px"}}
   (map-indexed (fn [index slide]
                         ^{:key index}
                         [:> js/Step
                          [:> js/StepLabel ""]]) slides)])

(defn layout []
  (let [{:keys [slide-index transition]} @app-state]
    [:> js/MuiThemeProvider {:muiTheme js/MuiTheme}
     [:div
      [:div {:class "controls"}
       [:button (if (< 0 slide-index)
                  {:on-click previous-slide}
                  {:disabled true})
        "<"]
       [stepper-component slide-index]
       [:button (if (> (count slides) (inc slide-index))
                  {:on-click next-slide}
                  {:disabled true})
        ">"]]
      [:> js/React.addons.CSSTransitionGroup
       {:component "div"
        :className "slide-transition"
        :transitionName transition
        :transitionEnter true
        :transitionLeave true
        :transitionEnterTimeout 1000
        :transitionLeaveTimeout 1000}
       ^{:key slide-index}
       [:div {:class (str "slide slide-" (inc slide-index))}
        [(nth slides slide-index)]]]]]))

(defonce boot
  (do
    (set! (.-onkeydown js/document) on-key-down)
    (reagent/render-component [#'layout]
                              (. js/document (getElementById "app")))))

(defn on-js-reload []
  (reagent/force-update-all))
