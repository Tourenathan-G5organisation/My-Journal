package com.toure.myjournal.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;

/**
 * Created by Toure Nathan on 6/26/2018.
 */
public class Utility {

    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("http://www.google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
