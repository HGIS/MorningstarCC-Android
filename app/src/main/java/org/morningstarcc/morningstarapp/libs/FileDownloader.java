package org.morningstarcc.morningstarapp.libs;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Kyle on 4/7/2015.
 */
public abstract class FileDownloader extends DownloadUrlContentTask<File> {

    private String dest;
    private Context mContext;
    public long progress;

    public FileDownloader(String filename, Context context) {
        this.mContext = context;
        this.dest = filename;
        this.progress = 0;
    }

    @Override
    protected File doInBackground(String... urls) {
        try {
            InputStream remoteStream = getRemoteInputStream(urls[0]);
            OutputStream localStream = mContext.openFileOutput(dest, Context.MODE_WORLD_READABLE);

            byte buf[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = remoteStream.read(buf)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    remoteStream.close();
                    return null;
                }

                total += count;
                localStream.write(buf, 0, count);

                this.progress = total;
                publishProgress();
            }

            remoteStream.close();
            localStream.close();

            Log.d("FileDownloader", "Finished download from: " + urls[0] + "\tTotal bytes: " + total);
        }
        catch (IOException e) {
            Log.e("FileDownloader", Log.getStackTraceString(e));
        }

        return new File(mContext.getFilesDir(), dest);
    }

    @Override
    public abstract void onPostExecute(File file);
}
