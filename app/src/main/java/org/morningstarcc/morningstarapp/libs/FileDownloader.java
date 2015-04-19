package org.morningstarcc.morningstarapp.libs;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Created by Kyle on 4/7/2015.
 */
public abstract class FileDownloader extends DownloadUrlContentTask<File> {

    private File dest;

    public FileDownloader(String prefix, String suffix) throws IOException {
        dest = File.createTempFile(prefix, suffix);
        dest.deleteOnExit();
    }

    @Override
    protected File doInBackground(String... urls) {
        try {
            Scanner remoteStream = new Scanner(getRemoteInputStream(urls[0]));
            OutputStream localStream = new FileOutputStream(dest);

            while (remoteStream.hasNextByte()) {
                localStream.write(remoteStream.nextByte());
            }
        }
        catch (IOException e) {
            Log.e("FileDownloader", Log.getStackTraceString(e));
        }

        return dest;
    }

    @Override
    public abstract void onPostExecute(File file);
}
