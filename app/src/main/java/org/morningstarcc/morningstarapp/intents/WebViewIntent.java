package org.morningstarcc.morningstarapp.intents;

import android.content.Intent;
import android.net.Uri;

/**
 * An intent to allow the user to view the given url link in a browser of their choice on their device.
 */
public class WebViewIntent extends Intent {

    public WebViewIntent(String url) {
        super(Intent.ACTION_VIEW, Uri.parse(url));
    }
}
