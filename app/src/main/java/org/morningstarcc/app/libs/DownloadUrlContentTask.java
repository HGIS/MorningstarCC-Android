package org.morningstarcc.app.libs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A class that retrieves data from a specified remote location and loads it through an RSS parser,
 *  placing the result inside the database based on the given location string.
 */
public abstract class DownloadUrlContentTask<T> extends AsyncTask<String, Void, T> {
    private static final String TAG = "DownloadUrlContentTask";

    protected long length = 0;

    @Override
    protected abstract T doInBackground(String... urls);

    @Override
    protected abstract void onPostExecute(T t);

    /**
     * Given a string representation of a URL, sets up a connection and gets an input stream.
     */
    protected InputStream getRemoteInputStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        // Starts the query
        conn.connect();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException(urlString + " (" + conn.getResponseCode() + ") - " + conn.getResponseMessage());
        }

        this.length = conn.getContentLength();
        Log.d(TAG, urlString + " - " + length + " bytes");

        return conn.getInputStream();
    }

    public long getLength() {
        return length;
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