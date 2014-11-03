package org.morningstarcc.morningstarapp.libs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

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

    // TODO: some sort of caching
    private Palette mPalette;

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

    public Palette getPalette() {
        return this.mPalette;
    }

    // Adapted from http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
    private class DownloadImageTask extends DownloadUrlContentTask<Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                Bitmap result = BitmapFactory.decodeStream( getRemoteInputStream(urls[0]) );
                mPalette = Palette.generate(result);

                return result;
            }
            catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
                return null;
            }
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
