package org.morningstarcc.morningstarapp.libs;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// Implementation of AsyncTask used to download XML feed adapted from stackoverflow.com.
public abstract class DownloadUrlContentTask<T> extends AsyncTask<String, Void, T> {
    private static final String TAG = "DownloadUrlContentTask";

    @Override
    protected abstract T doInBackground(String... urls);

    @Override
    protected abstract void onPostExecute(T t);

    /**
     * Given a string representation of a URL, sets up a connection and gets an input stream.
     */
    protected InputStream downloadUrl(String urlString) throws IOException {
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
}