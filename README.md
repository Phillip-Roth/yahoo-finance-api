# yahoo-finance-api
This project contains two classes: Stock.java and Option.java, that get data from Yahoo Finance to evaluate different options trading strategies.



## Stock.java

This class gets pulls all data related to the stock entered.

### Key Methods

**Constructor** 

The Stock constructor takes one arugument, the ticker of the stock.

Example: a Stock object that represents AT&T

Stock t = new Stock("T");

**Update**

this method pulls data from yahoo finance the first time that it is called it gets all data, and any call after that updates it.

**Getter Methods**

Below are the methods to access data related to the Option and what they return.


* getAbv() - returns a string containing the ticker.
* getAsk() - returns a double containing the current ask for the stock.
* getBid() - returns a double containing the current bid for the stock.
* getOpen() - returns a double containing the current open of the stock.
* getDivYield() - returns a double containing the current dividend yield of the stock.
* getPrevClose() - returns a double containing the previous close of the stock.

**Errors**

If you ever get an error from the code refering to the website, as long as the ticker is correct this just means that 
yahoo finance blocked the program from accessing the website for whatever reason. Just run it again and it should be 
fine.


## Option.java

This class pull data related to the stock's option based on expiration date and strike price.

### Key Methods

**Constructor** 

The Option constructor takes 4 arguments: ticker, strike price, expiration date, and type of option.

* Ticker - takes the string that represents the stock
* Strike Price - the strike price of the option you are interested in. *Must be in $ 0.50 increments* 
* Expiration Date - the date that the option is expiring. *Must be a friday, a future date, and the date must be in DD MM YYYY format*
* Type of option - enter a "C" for call and "P" for put

Example: a call with a strike price of $30.00 for AT&T expiring June 5th, 2020

Option t = new Option("T", 30.00, "05 06 2020", "C")

**Update**

This method is crutial to the Option class. It must be called before attempting to access any data.

**Getter Methods**

Below are the methods to access data related to the Option and what they return.

* getAbv() - returns the ticker of the underlying security
* getAsk() - returns the ask of the option
* getBid() - returns the bid of the option
* getDate() - returns the expiry date of the option
* getStrike() - returns the strike price of the option
* getIV() - returns the implied volitility of the option
* getOpenInterest() - returns the open interest (total number of options that have not been called) 
* getExpiryDate() - returns the expiry date of the option
