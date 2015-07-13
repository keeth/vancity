(ns vancity.core
  (:gen-class)
  (:use clj-webdriver.taxi)
  (:require [clj-http.client :as client]))

(defn to-http-cookies [webdriver-cookies]
  (zipmap (map #(:name %) webdriver-cookies)
          (map #(select-keys % [:value :path]) webdriver-cookies)))

(defn vancity-personal-banking
  "Get latest transactions from personal bank account"
  [config]
  (set-driver! {:browser :chrome} "https://www.vancity.com/OnlineBanking/MyAccounts/")
  (quick-fill-submit {"input[name=branch]" (:branch config)}
                     {"input[name=acctnum]" (:account config)}
                     {"input[name=acctnum]" submit})
  (let [question (text (find-element {:css "label[for=answer]"}))
        answer (get (:answers config) question)]
    (quick-fill-submit {"input[name=answer]" answer}
                       {"input[name=answer]" submit}))
  (quick-fill-submit {"input[name=pac]" (:pac config)}
                     {"input[name=pac]" submit})
  (get-url "https://www.vancity.com/OnlineBanking/MyAccounts/Activity/")
  (let [option (first (filter #(re-find #"CHEQUING" %) (map text (options "#fromAcct"))))]
    (select-by-text "#fromAcct" option))
  (select "input[value=DateRangeFilter]")
  (click (find-element {:xpath "//a[text()='Advanced Options']"}))
  (select-by-value "#stype" "QUICKBOOKS")
  (submit "#getAccountActivity")
  (let [url (attribute "#DownloadWarning" :action)
        http-cookies (to-http-cookies (cookies))
        response (client/post url {:cookies http-cookies
                                   :proxy-host "127.0.0.1"
                                   :proxy-port 48888
                                   :insecure? true
                                   :form-params {:CONTINUE_DOWNLOAD "Continue"}
                                   :force-redirects true
                                   })]
      (println (response :body))
    )
  (close)
  (System/exit 0))

(defn -main
  "Download OFX transactions from Vancity bank account or Vancity VISA account"
  [& args]
  (let [[account-type, config-file] args
        account-type-kw (keyword account-type)]
    (if (nil? config-file)
      (throw (RuntimeException. "Usage: lein run (personal|visa) config.edn")))
    (case account-type-kw
      :personal (vancity-personal-banking (load-file config-file))
      :visa (throw (RuntimeException. "Not implemented yet"))
      (throw (RuntimeException. "The only recognized account-types are :personal and :visa")))))
