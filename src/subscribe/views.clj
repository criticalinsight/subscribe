;; Copyright (c) 2019-2020 Bastien Guerry <bzg@bzg.fr>
;; SPDX-License-Identifier: EPL-2.0
;; License-Filename: LICENSES/EPL-2.0.txt

(ns subscribe.views
  "Subscribe application views."
  (:require [hiccup.page :as h]
            [hiccup.element :as he]
            [ring.util.anti-forgery :as afu]
            [subscribe.i18n :refer [i18n]]
            [subscribe.config :as config]
            [compojure.handler :as handler]))

(defn default [title mailing-list content]
  (h/html5
   {:lang config/locale}
   [:head
    [:title (i18n [:title])]
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1, shrink-to-fit=yes"}]
    (h/include-css config/css)
    config/before-head-closing-html]
   [:body
    config/after-body-beginning-html
    [:section.hero.is-primary
     [:div.hero-body
      [:div.container
       [:h1.title.has-text-centered title]]]]
    [:section.section
     [:div.column.is-8.is-offset-2 content]]
    (or config/footer-html
        [:footer.footer
         [:div.content.has-text-centered
          (if-let [tos (config/tos-url mailing-list)]
            [:p [:a {:href tos :target "new"} (i18n [:tos])]])
          [:p (i18n [:made-with]) " "
           [:a {:href   "https://github.com/bzg/subscribe"
                :target "new"} "Subscribe"]]]])]))

(defn error []
  (default
   (i18n [:error])
   nil
   [:div.container
    [:p [:a {:href (config/return-url nil)}
         (i18n [:return-to-site])]]]))

(defn mailing-lists [lists]
  (default
   (or (config/team nil) (i18n [:mailing-lists]))
   nil
   [:div.container
    (for [l lists]
      [:div.columns
       [:div.column
        [:p.title (:name l)]
        [:p.subtitle (or (not-empty (:description l))
                         (config/description (:address l)))]]
       [:div.column.level
        [:div.level-left
         [:a.level-item.button.is-info
          {:href (str "/subscribe/" (:address l))}
          (i18n [:subscribe-button])]
         [:a.level-item.button.is-danger
          {:href (str "/unsubscribe/" (:address l))}
          (i18n [:unsubscribe-button])]]]])]))

(defn subscribe-to-mailing-list [mailing-list]
  (let [email-ui (i18n [:email-address])
        name-ui  (i18n [:name])]
    (default
     (or (config/description mailing-list) mailing-list)
     (or (config/description mailing-list) mailing-list)
     [:div.container
      [:form
       {:action "/subscribe" :method "post"}
       (afu/anti-forgery-field)
       [:input {:name  "mailing-list" :type "hidden"
                :value mailing-list}]
       [:div.field
        [:label.label name-ui]
        [:div.control
         [:input.input
          {:name "name" :type        "text"
           :size "30"   :placeholder name-ui}]]]
       [:div.field
        [:label.label email-ui]
        [:div.control
         [:input.input
          {:name     "subscriber" :type        "email"
           :size     "30"         :placeholder email-ui
           :required true}]]]
       [:div.field
        [:div.control
         [:input.button.is-info
          {:type  "submit"
           :value (i18n [:subscribe])}]]]]])))

;; FROM: unsubscribe-from-mailing-list
(defn unsubscribe-from-mailing-list [mailing-list]
  (let [email-ui (i18n [:email-address])]
    (default
     (or (config/description mailing-list) mailing-list)
     (or (config/description mailing-list) mailing-list)
     [:div.container
      [:form
       {:action "/unsubscribe" :method "post"}
       (afu/anti-forgery-field)
       [:input {:name  "mailing-list" :type "hidden"
                :value mailing-list}]       
       [:label.label email-ui]
       [:input.input
        {:name     "subscriber" :type        "email"
         :size     "30"         :placeholder email-ui
         :required true}]
       [:br]
       [:br]
       [:input.button.is-danger
        {:type  "submit"
         :value (i18n [:unsubscribe])}]]])))

(defn feedback [title mailing-list message]
  (default
   title
   mailing-list
   [:div.container
    [:p.subtitle message]
    [:p [:a {:href (config/return-url mailing-list)}
         (i18n [:return-to-site])]]]))
