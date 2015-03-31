package org.morningstarcc.morningstarapp.intents;

import android.content.Intent;
import android.net.Uri;

/**
 * An intent to allow the user to view the given url link in a browser of their choice on their device.
 */
public class WebViewIntent {

    public static Intent build(String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    }
}
