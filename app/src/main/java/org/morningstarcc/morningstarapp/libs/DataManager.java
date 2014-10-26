package org.morningstarcc.morningstarapp.libs;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Kyle on 9/6/2014.
 *
 * Manages the backend of data for calling context. Defines an interface for data collection from
 *  given string via the onDataReturned method (given true iff the data was successfully retrieved and parsed)
 */
public abstract class DataManager {

    private Context mContext;

    public DataManager(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressWarnings("unchecked")
    public void update(String from) {
        DownloadUrlContentTask remote;
        DatabaseStorage local = new DatabaseStorage(this.mContext);

        Log.i("DataManager", from.substring(from.lastIndexOf("/"), from.lastIndexOf(".")));
        remote = new RemoteStorage(local, from.split("[./]")[5]);

        if (DownloadUrlContentTask.hasInternetAccess(this.mContext) && remote.upToDate(local.getLastUpdated())) {
            remote.execute(from);
        }
        else {
            // simply return since we cannot update
            onDataReturned(false);
        }
    }

    public abstract void onDataReturned(boolean success);

    // a class to execute remote requests in the background
    private class RemoteStorage extends DownloadUrlContentTask {
        private DatabaseStorage local;
        private String dest;

        public RemoteStorage(DatabaseStorage local, String dest) {
            this.local = local;
            this.dest = dest;
        }

        @Override
        protected void onPostExecute(List<ContentValues> result) {
            local.set(dest, result);
            onDataReturned(true);
        }
    }
}
