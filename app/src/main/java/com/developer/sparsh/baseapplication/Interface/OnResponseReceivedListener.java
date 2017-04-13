package com.developer.sparsh.baseapplication.Interface;

import org.json.JSONObject;

/**
 * Created by utkarshnath on 12/04/17.
 */

public interface OnResponseReceivedListener {
    public void onResponseReceived(String responseString, JSONObject responseObject, String accesToken);
}