package org.morningstarcc.morningstarapp.libs;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kyle on 9/6/2014.
 *
 * Manages the backend of data for calling context. Defines an interface for data collection from
 *  given string via the onDataReturned method (given true iff the data was successfully retrieved and parsed)
 */
public abstract class DataManager {

    private static final String TAG = "DataManager";

    private Context mContext;

    public DataManager(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressWarnings("unchecked")
    public void update(String from) {
        RemoteStorage remote;
        DatabaseStorage local = new DatabaseStorage(this.mContext);
        String tableName = from.substring(from.lastIndexOf("/") + 1, from.lastIndexOf(".")) +
                (from.contains("=") ? from.substring(from.indexOf("=") + 1) : "");

        remote = new RemoteStorage(local, tableName);

        if (DownloadUrlContentTask.hasInternetAccess(this.mContext) && remote.upToDate(local.getLastUpdated())) {
            remote.execute(from);
        }
        else {
            onDataReturned(false);
        }
    }

    public abstract void onDataReturned(boolean success);

    // a class to execute remote requests in the background
    private class RemoteStorage extends DownloadUrlContentTask<List<ContentValues>> {
        private DatabaseStorage local;
        private String dest;

        public RemoteStorage(DatabaseStorage local, String dest) {
            this.local = local;
            this.dest = dest;
        }

        @Override
        protected List<ContentValues> doInBackground(String... urls) {
            try {
                return RssParser.parse(getRemoteInputStream(urls[0]));
            }
            catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ContentValues> result) {
            local.set(dest, result);
            onDataReturned(true);
        }
    }
}
