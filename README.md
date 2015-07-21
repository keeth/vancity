# vancity

Download recent OFX transactions from [Vancity](http://www.vancity.com) bank account or [Vancity VISA](https://www.myvisaaccount.com/) account.

My bank (Vancouver City Savings Credit Union) doesn't make it easy to automatically download transactions, 
so I started hacking on a solution.

So far this is mostly a proof-of-concept and a way for me to learn clj-webdriver.

## Installation

[WebDriver](http://semperos.github.io/clj-webdriver/) is used to log in to Vancity and download transactions.

By default Google Chrome is used, so you must have Chrome and [ChromeDriver](https://sites.google.com/a/chromium.org/chromedriver/) installed.  
This may be parameterized in the future.

    git clone https://github.com/keeth/vancity
    cd vancity

## Usage

    cp vancity.personal.edn.template vancity.personal.edn
    lein run personal vancity.personal.edn > personal-transactions.ofx
    
    cp vancity.visa.edn.template vancity.visa.edn
    lein run visa vancity.visa.edn > visa-transactions.ofx


## Todo

* Make Selenium options configurable
* Expose options like name of sub-account, and date range.
* Put core into a library, if it makes sense to do that.

## License

Copyright © 2015 Keith Grennan

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
