package com.hakey.btcwidget;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

class HTTPRequestThread extends Thread{
    private static final String urlString = "https://api.coindesk.com/v1/bpi/currentprice.json";

    String getInfoString() {
        return output;
    }

    private String output = "";
    int cp = 0;
    private void requestPrice() {

        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();


            response.append("{\"time\":{\"updated\":\"Jun 13, 2019 16:19:00 UTC\",\"updatedISO\":\"2019-06-13T16:19:00+00:00\",\"updateduk\":\"Jun 13, 2019 at 17:19 BST\"},\"disclaimer\":\"This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org\",\"chartName\":\"Bitcoin\",\"bpi\":{\"USD\":{\"code\":\"USD\",\"symbol\":\"&#36;\",\"rate\":\"8,175.0933\",\"description\":\"United States Dollar\",\"rate_float\":8175.0933},\"GBP\":{\"code\":\"GBP\",\"symbol\":\"&pound;\",\"rate\":\"6,448.8488\",\"description\":\"British Pound Sterling\",\"rate_float\":6448.8488},\"EUR\":{\"code\":\"EUR\",\"symbol\":\"&euro;\",\"rate\":\"7,251.0871\",\"description\":\"Euro\",\"rate_float\":7251.0871}}}");

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(response.toString());
            }
            in.close();

            output = "Price: " + JSONParser.getPrice(response.toString()) + "\n" + getTimeStamp();
            System.out.println(output);

        } catch (Exception e) {
            output = e.toString();
            System.out.println(output);
        }
    }

    @Override
    public void run() {
        requestPrice();
    }

    private String getTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        if(calendar.get(Calendar.MINUTE)>9) {

            return "Time: " + calendar.get(Calendar.HOUR_OF_DAY)
                    + ":" + calendar.get(Calendar.MINUTE);
        } else {
            return "Time: " + calendar.get(Calendar.HOUR_OF_DAY)
                    + ":0" + calendar.get(Calendar.MINUTE);
        }

    }
}
