(ns euroclojure.prototype)

(defn slide [{:keys [speaker]}]
  [:div.slide.left
   [:h1.centered "Prototyping"]
   [:ul
    [:li "Designer worked on HTML + CSS mockup"]
    (when speaker
      [:p "No need to wait for developers to setup tools to get started, use any tool that focuses on HTML+CSS"])
    [:li "Developers worked on the functionality"]
    (when speaker
      [:div "Setting up basic infrastructure for API calls, Deploy
      script, tests. Also the app needs a lot of state management, so
      this part needed a lot of consideration. I call it the backend
      of the frontend but no one likes that"])
    (when speaker
      [:p "the fact that we could develop the flow logic completely
      independently from the view logic helped us a lot in the
      beginning, it made it so we did no step on each other's toes"])
    [:li "Use HTML mockup directly as template using " [:code "enfocus"]]]
   (when-not speaker
     [:em "Wow factor: parallel work from ground up, no wasted cycles"])])
