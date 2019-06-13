package com.hakey.btcwidget;

import org.json.JSONException;
import org.json.JSONObject;

class JSONParser {

    static String getPrice(String s) throws JSONException {
        String price;
        JSONObject obj = new JSONObject(s);
        JSONObject pairObj = obj.getJSONObject("bpi");
        JSONObject newObj = pairObj.getJSONObject("USD");
        price = newObj.getString("rate");

        return price;
    }

}
