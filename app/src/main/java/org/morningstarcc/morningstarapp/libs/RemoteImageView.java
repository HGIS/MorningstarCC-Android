package org.morningstarcc.morningstarapp.libs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

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
    private static final String TAG = "RemoteImageView";

    public RemoteImageView(Context context) {
        super(context);
    }

    public RemoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RemoteImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Sets the image link to the remote location. If the given link was in the database,
     *  this will update the database to reflect a new File that is created to make this more efficient.
     *
     * @param link  url to the image bitmap. (Accepts http:// and local filename Strings)
     */
    public void setImageLink(String link) {
        if (link.contains(REMOTE)) {
            new DownloadImageTask(this).execute(link);
        }
        else {
            this.setImageURI(Uri.parse(link) );
        }
    }

    // Adpated from http://stackoverflow.com/questions/649154/save-bitmap-to-location
    private File saveBitmapToFile(Bitmap bmp, String filename) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new File(filename);
    }

    // Adapted from http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
    private class DownloadImageTask extends DownloadUrlContentTask<Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            try {
                return BitmapFactory.decodeStream( getRemoteInputStream(urls[0]) );
            }
            catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
                return null;
            }
        }

        // TODO: create file and update database
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
