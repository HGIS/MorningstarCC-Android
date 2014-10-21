package org.morningstarcc.morningstarapp.libs;

import android.content.Context;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.util.Log;

import org.morningstarcc.morningstarapp.datastructures.Album;
import org.morningstarcc.morningstarapp.datastructures.RssEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            local.set(dest, result);
            onDataReturned();
        }
    }
}
