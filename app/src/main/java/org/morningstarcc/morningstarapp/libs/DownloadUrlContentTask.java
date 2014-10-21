package org.morningstarcc.morningstarapp.libs;

import android.content.Context;
import android.database.AbstractCursor;
import android.database.MatrixCursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

// Implementation of AsyncTask used to download XML feed adapted from stackoverflow.com.
public abstract class DownloadUrlContentTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>> > {
    private static final String TAG = "DownloadUrlContentTask";

    // TODO: get actual date of latest data
    private static Calendar curDataDate = new GregorianCalendar();

    @Override
    protected ArrayList< HashMap<String, String> > doInBackground(String... urls) {
        try {
            return RssParser.parse(getRemoteInputStream(urls[0]));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected abstract void onPostExecute(ArrayList<HashMap<String, String>> t);

    /**
     * Given a string representation of a URL, sets up a connection and gets an input stream.
     */
    protected InputStream getRemoteInputStream(String urlString) throws IOException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query
            conn.connect();
            return conn.getInputStream();
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        }
    }

    public boolean upToDate(Calendar lastUpdated) {
        return curDataDate.after(lastUpdated);
    }

    public static boolean hasInternetAccess(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}