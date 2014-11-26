package org.morningstarcc.morningstarapp.libs;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import org.morningstarcc.morningstarapp.BuildConfig;
import org.morningstarcc.morningstarapp.datastructures.UpdateParcel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * An adaptation for ImageViews that simplifies getting images from a website.
 *
 * It will also create a File containing the downloaded image, based off the link name.
 * If the given link originated from the database, this class will also update the database with a new
 *  link to the created File.
 *
 * Please note that this waits for the image to be downloaded the first time before it displays anything,
 *  therefore it is advised to display a loading indicator in front of the image until it is loaded.
 */
public class RemoteImageView extends ImageView {
    private static final String REMOTE = "http";

    // TODO: some sort of caching
    private Palette mPalette;
    private Context mContext;

    // fields to handle writing file to database
    private DatabaseStorage storage = null;
    private String table, idColumn, idValue, updateColumn;

    public RemoteImageView(Context context) {
        super(context);
        this.mContext = context;
    }

    public RemoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public RemoteImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    /**
     * Sets the image link to the remote location. If the given link was in the database,
     *  this will update the database to reflect a new File that is created to make this more efficient.
     *
     * @param link  url to the image bitmap. (Accepts http:// and local filename Strings)
     */
    public void setImageLink(String link) {
        setImageLink(link, null, null, null, null, null);
    }

    public void setImageLink(String link, DatabaseStorage storage, String table, String idColumn, String idValue, String updateColumn) {
        if (link.contains(REMOTE)) {
            this.storage = storage;
            this.table = table;
            this.idColumn = idColumn;
            this.idValue = idValue;
            this.updateColumn = updateColumn;

            new DownloadImageTask(link).execute(link);
        }
        else {
            setImageFilePath(link); // still do this aynchronoujsly
        }
    }

    private void setImageFilePath(String path) {
        //this.setImageURI( Uri.parse(path) );
        this.setImageBitmap(BitmapFactory.decodeFile(new File(path).getAbsolutePath()));
        if (this.storage != null) { this.storage.update(table, idColumn, idValue, updateColumn, path);


            Cursor tmp = this.storage.get(table, new String[]{null});
            int i = 0;

            tmp.moveToFirst();
            while (!tmp.isAfterLast()) {
                try {
                    Log.e("Test", i++ + ": " + updateColumn + " = " + tmp.getString(tmp.getColumnIndex(updateColumn)));
                }
                catch (Exception e) { Log.e("Test", i++ + ": "); }

                tmp.moveToNext();
            }
        }
    }

    public Palette getPalette() {
        return this.mPalette;
    }

    // Adapted from http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
    private class DownloadImageTask extends DownloadUrlContentTask<Bitmap> {
        String filename;

        public DownloadImageTask(String filename) {
            this.filename = getLocalFilename(filename);
            Log.e("Test", "Local filename: " + this.filename);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                Bitmap result = BitmapFactory.decodeStream( getRemoteInputStream(urls[0]) );
                //mPalette = Palette.generate(result);

                // TODO: check space before we just store the image
                //saveBitmapToFile(result);

                return result;
            }
            catch (Exception e) {
                Log.e(RemoteImageView.class.getName(), Log.getStackTraceString(e));
                return null;
            }
        }

        protected void onPostExecute(Bitmap result) {
            setImageBitmap(result);
            //if (updateParcel != null) { new DatabaseStorage(mContext).update(updateParcel); }
        }

        // Adapted from http://stackoverflow.com/questions/649154/save-bitmap-to-location
        private void saveBitmapToFile(Bitmap bmp) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(filename);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            }
            catch (Exception e) {
                Log.e(RemoteImageView.class.getName(), Log.getStackTraceString(e));
            }
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                }
                catch (IOException e) {
                    Log.e(RemoteImageView.class.getName(), Log.getStackTraceString(e));
                }
            }
        }

        private String getLocalFilename(String remoteFilename) {
            return remoteFilename.substring(remoteFilename.lastIndexOf('/') + 1);
        }
    }
}
