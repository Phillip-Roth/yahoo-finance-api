import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Option
{
  private double bid;
  private double ask;
  private double strike;
  private double IV;
  private double openInterest;
  private String expiryDate;
  private String contractName;

  private String abv;
  private String url;

  private final String ASK_TEXT = "\"ask\":{\"ra";
  private final String BID_TEXT = "\"bid\":{\"ra";
  private final String STRIKE_TEXT = "\"strike\":{\"ra";
  private final String OPEN_INTEREST_TEXT = "\"openInterest\":{\"ra";
  private final String IV_TEXT = "\"impliedVolatility\":{\"ra";

  public Option(String abv, double strikePrice, String date, String typeOfOption)
  {

    this.abv = abv;
    bid = 0.0;
    ask = 0.0;
    strike = strikePrice;
    IV = 0.0;
    openInterest = 0.0;
    expiryDate = date;

    long daysInBetween = 0;

    SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
    String inputString1 = "01 01 1970";
    String inputString2 = date;

    contractName = abv + inputString2.substring(8) + inputString2.substring(3, 5)
        + inputString2.substring(0, 2) + typeOfOption + findContractNum();

    try
    {
      Date date1 = myFormat.parse(inputString1);
      Date date2 = myFormat.parse(inputString2);
      long diff = date2.getTime() - date1.getTime();
      daysInBetween = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
      daysInBetween++;

    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }

    daysInBetween = daysInBetween * 86400;

    url = "https://finance.yahoo.com/quote/" + abv + "/options?p=" + abv + "&date=" + daysInBetween;
    

  }

  /**
   * Will update stock info.
   */
  public void update() throws IOException
  {
    gatherAsk();
    gatherBid();
    gatherStrike();
    gatherOpenInterest();
    gatherIV();

  } // getData method

  /**
   * Will get ticker.
   */
  public String getAbv() {
      
      return abv;
      
  } // getAbv method
  
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
   * Will return the date
   * 
   * @return String
   */
  public String getDate() {
      
      return expiryDate;
      
  } // getDate method

  /**
   * Will return the open price.
   * 
   * @return double
   */
  public double getStrike()
  {

    return strike;

  } // getOpen method

  /**
   * Will return dividend yield.
   * 
   * @return double
   */
  public double getIV()
  {

    return IV;

  } // getDivYield method

  /**
   * Will return previous close price.
   * 
   * @return double
   */
  public double getOpenInterest()
  {

    return openInterest;

  } // getPrevClose method

  public String getExpiryDate()
  {

    return expiryDate;

  } // getPrevClose method

  // Private Methods
  // -------------------------------------------------------------------------

  private String findContractNum()
  {
    String temp;
    int strikeInt = (int) (strike * 100);
    temp = Integer.toString(strikeInt);
    temp = temp + "0";

    while (temp.length() < 8)
    {
      temp = "0" + temp;
    }

    return temp;
  }

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
  private double gatherStrike() throws IOException
  {

    strike = Scan(STRIKE_TEXT);

    return strike;

  } // gatherOpen method

  private double gatherIV() throws IOException
  {
    IV = Scan(IV_TEXT);

    return IV;
  }

  /**
   * Gathers dividend percentage from html of webpage.
   * 
   * @return double
   */
  private double gatherOpenInterest() throws IOException
  {

    openInterest = Scan(OPEN_INTEREST_TEXT);

    return openInterest;

  } // gatherDivYield method

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

    String line = br.readLine();

    while (line != null)
    {

      if (line.contains(keyword) && line.contains(contractName))
      {
        String temp = line.substring(line.indexOf(contractName));

        int start = temp.indexOf(keyword);
        int place = temp.indexOf("fmt\":\"", start);
        int stop = temp.indexOf("\"", place + 6);

        if (temp.substring(stop - 1, stop).equals("%"))
        {
          stop = stop - 1;
        }

        String infoString = temp.substring(place + 6, stop);

        while (infoString.contains(","))
        {
          int tempNum = infoString.indexOf(",");
          infoString = infoString.substring(0, tempNum) + infoString.substring(tempNum + 1);
        }

        info = Double.parseDouble(infoString);
      } // end if

      line = br.readLine();

    } // end while

    return info;

  } // scan method

  // End of Methods
  // ----------------------------------------------------------------------------

}