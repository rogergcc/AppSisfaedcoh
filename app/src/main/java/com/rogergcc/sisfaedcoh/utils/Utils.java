

/*
 * Created by rogergcc
 * Copyright Ⓒ 2019 . All rights reserved.
 */

package com.rogergcc.sisfaedcoh.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.URI;

public class Utils {
    public  static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static   boolean isValidURL(String urlStr) {
        try {
            URI uri = new URI(urlStr);
            return uri.getScheme().equals("http") || uri.getScheme().equals("https");
        } catch (Exception e) {
            return false;
        }
    }
}
