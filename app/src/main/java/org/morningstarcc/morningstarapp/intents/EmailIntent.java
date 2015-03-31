package org.morningstarcc.morningstarapp.intents;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Kyle on 3/26/2015.
 */
public class EmailIntent {
    public static Intent build(String address) {
        return new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", address, null));
    }
}
