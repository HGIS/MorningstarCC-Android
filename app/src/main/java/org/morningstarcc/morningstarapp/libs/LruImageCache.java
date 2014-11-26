package org.morningstarcc.morningstarapp.libs;

import android.app.ActivityManager;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

/**
 * A cache for images used during runtime.
 *
 * TODO: implement a backing diskImageCache (aka. a cache using the filesystem)
 */
public class LruImageCache implements ComponentCallbacks {
    private LruCache<String, Bitmap> cache;

    public LruImageCache(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        this.cache = new LruCache<String, Bitmap>(am.getMemoryClass() * 1024 * 1024);
    }

    public void display(String url, ImageView imageview, int defaultRes) {
        // set a default image in the stead of image to get
        imageview.setImageResource(defaultRes);

        Bitmap image = cache.get(url);
        if (image != null) {
            imageview.setImageBitmap(image);
        }
        else {
            new SetImageTask(imageview).execute(url);
        }
    }

    @Override // I have no idea what to use this for
    public void onConfigurationChanged(Configuration newConfig) {}

    @Override
    public void onLowMemory() {
        // TODO: manage memories here
    }

    /**
     * Inner class to download image and store in cache
     */
    private class SetImageTask extends DownloadUrlContentTask<Boolean> {
        private ImageView imageview;
        private Bitmap bmp;

        public SetImageTask(ImageView imageview) {
            this.imageview = imageview;
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            String url = urls[0];
            try {
                bmp = BitmapFactory.decodeStream(getRemoteInputStream(url));

                if (bmp != null) {
                    cache.put(url, bmp);
                    return true;
                }
            }
            catch (Exception e) {
                Log.e(LruImageCache.class.getName(), Log.getStackTraceString(e));
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                imageview.setImageBitmap(bmp);
            }
        }
    }
}
