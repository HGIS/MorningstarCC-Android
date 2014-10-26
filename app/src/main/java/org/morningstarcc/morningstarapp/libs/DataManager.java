package org.morningstarcc.morningstarapp.libs;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

/**
 * Created by Kyle on 9/6/2014.
 */
public abstract class DataManager {

    private static final String TAG = "DataManager";

    private Context mContext;

    public DataManager(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressWarnings("unchecked")
    public void update(String from) {
        DownloadUrlContentTask remote;
        LocalStorage local = new DatabaseStorage(this.mContext);

        remote = new RemoteStorage(local, from.split("[./]")[5]);

        if (DownloadUrlContentTask.hasInternetAccess(this.mContext) && remote.upToDate(local.getLastUpdated())) {
            remote.execute(from);
        }
        else {
            // simply return since we cannot update
            onDataReturned();
        }
    }

    public abstract void onDataReturned();

    private class RemoteStorage extends DownloadUrlContentTask {
        private LocalStorage local;
        private String dest;

        public RemoteStorage(LocalStorage local, String dest) {
            this.local = local;
            this.dest = dest;
        }

        @Override
        protected void onPostExecute(List<ContentValues> result) {
            local.set(dest, result);
            onDataReturned();
        }
    }
}
