package com.example.mick.dockandroidlogin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mick on 2/2/2018.
 */

public class Server {

    public static String usernamae ;
    public static final String app_version = "IfishAppsBeta1V34" ;

    //public static final String URL = "http://10.0.2.2/skils/laravel_projects/rest_api/public/";
    public static final String URL = "http://ifish.id/apps/rest_api/public/";
    public static boolean isConnectingToInternet(Context context)
    {
        ConnectivityManager connectivity =
                (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }

}