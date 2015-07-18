package org.morningstarcc.morningstarapp.libs;

import android.content.ContentValues;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import org.morningstarcc.morningstarapp.database.Database;
import org.morningstarcc.morningstarapp.database.DatabaseTable;

import java.io.IOException;
import java.util.List;

import static org.morningstarcc.morningstarapp.database.Database.getTableName;

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

    public void update(String from) {
        DatabaseTable table = Database.withContext(mContext).forTable(getTableName(from));

        new RemoteStorage(table).execute(from);
    }

    public abstract void onDataReturned(boolean success);

    // a class to execute remote requests in the background
    private class RemoteStorage extends DownloadUrlContentTask<List<ContentValues>> {

        private DatabaseTable table;

        public RemoteStorage(DatabaseTable table) {
            this.table = table;
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
            table.write(result);
            onDataReturned(true);
        }
    }
}
