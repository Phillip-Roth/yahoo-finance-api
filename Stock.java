package stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Stock
{

  // Instance Variables
  private double ask;
  private double bid;
  private double open;
  private double divPercent;
  private double prevClose;

  private String abv;
  private String url;

  private final String ASK_TEXT = "\"ask\":{\"ra";
  private final String BID_TEXT = "\"bid\":{\"ra";
  private final String OPEN_TEXT = "regularMarketOpen";
  private final String DIV_PERCENT_TEXT = "AnnualDividendYield";
  private final String PREV_CLOSE_TEXT = "PreviousClose";

  public Stock(String abv) throws IOException
  {

    ask = 0.0;
    bid = 0.0;
    open = 0.0;
    divPercent = 0.0;
    prevClose = 0.0;

    this.abv = abv;
    url = "http://finance.yahoo.com/quote/" + abv + "?p=" + abv;

  } // Constructor

  // Public Methods
  // -------------------------------------------------------------------------

  /**
   * Will update stock info.
   */
  public void update() throws IOException
  {

    gatherAsk();
    gatherBid();
    gatherOpen();
    gatherDivYield();
    gatherPrevClose();

  } // getData method

  /**
   * Will return the ask.
   * 
   * @return double
   */
  public double getAsk()
  {

    return ask;

  } // getAsk method

  /**
   * Will return the bid.
   * 
   * @return double
   */
  public double getBid()
  {

    return bid;

  } // getBid method

  /**
   * Will return the open price.
   * 
   * @return double
   */
  public double getOpen()
  {

    return open;

  } // getOpen method

  /**
   * Will return dividend yield.
   * 
   * @return double
   */
  public double getDivYield()
  {

    return divPercent;

  } // getDivYield method

  /**
   * Will return previous close price.
   * 
   * @return double
   */
  public double getPrevClose()
  {

    return prevClose;

  } // getPrevClose method

  public String getAbv()
  {
    return abv;
  }

  // Private Methods
  // -------------------------------------------------------------------------

  /**
   * Gathers ask from html of webpage.
   * 
   * @return double
   */
  private double gatherAsk() throws IOException
  {

    ask = Scan(ASK_TEXT);

    return ask;

  } // gatherAsk method

  /**
   * Gathers bid from html of webpage.
   * 
   * @return double
   */
  private double gatherBid() throws IOException
  {

    bid = Scan(BID_TEXT);

    return bid;

  } // gatherBid method

  /**
   * Gathers open from html of webpage.
   * 
   * @return double
   */
  private double gatherOpen() throws IOException
  {

    open = Scan(OPEN_TEXT);

    return open;

  } // gatherOpen method

  /**
   * Gathers dividend percentage from html of webpage.
   * 
   * @return double
   */
  private double gatherDivYield() throws IOException
  {

    divPercent = Scan(DIV_PERCENT_TEXT);

    return divPercent;

  } // gatherDivYield method

  /**
   * Gathers previous close from html of webpage.
   * 
   * @return double
   */
  private double gatherPrevClose() throws IOException
  {

    prevClose = Scan(PREV_CLOSE_TEXT);

    return prevClose;

  } // gatherPrevClose method

  /**
   * Will scan html.
   * 
   * @param keyword
   * @return info
   */
  private double Scan(String keyword) throws IOException
  {

    double info = 0.0;

    URL website = new URL(url);
    URLConnection UrlCon = website.openConnection();
    InputStreamReader isr = new InputStreamReader(UrlCon.getInputStream());
    BufferedReader br = new BufferedReader(isr);

    //br.skip(200000);

    String line = br.readLine();

    while (line != null)
    {

      if (line.contains(keyword))
      {

        int start = line.indexOf(keyword);
        int place = line.indexOf("fmt\":\"", start);
        int stop = line.indexOf("\"", place + 6);

        if (line.substring(stop - 1, stop).equals("%"))
        {
          stop = stop - 1;
        }

        String infoString = line.substring(place + 6, stop);

        while (infoString.contains(","))
        {
          int temp = infoString.indexOf(",");
          infoString = infoString.substring(0, temp) + infoString.substring(temp + 1);
        }

        info = Double.parseDouble(infoString);

      } // end if

      line = br.readLine();

    } // end while

    return info;

  } // scan method

  // End of Methods
  // ----------------------------------------------------------------------------

} // Stock class
