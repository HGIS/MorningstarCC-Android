package org.morningstarcc.morningstarapp.libs;

import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A class that retrieves data from a specified remote location and loads it through an RSS parser,
 *  placing the result inside the database based on the given location string.
 */
public abstract class DownloadUrlContentTask extends AsyncTask<String, Void, List<ContentValues>> {
    private static final String TAG = "DownloadUrlContentTask";

    // TODO: get actual date of latest data
    private static Calendar curDataDate = new GregorianCalendar();

    @Override
    protected List<ContentValues> doInBackground(String... urls) {
        try {
            return RssParser.parse(getRemoteInputStream(urls[0]));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected abstract void onPostExecute(List<ContentValues> t);

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

    /**
     * Convenience method to check if internet access is okay
     *
     * @param context   required to get internet tools
     * @return          true iff internet access is good
     */
    public static boolean hasInternetAccess(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}