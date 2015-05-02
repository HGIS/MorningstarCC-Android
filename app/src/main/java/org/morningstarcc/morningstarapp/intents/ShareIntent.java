package org.morningstarcc.morningstarapp.intents;

import android.content.Intent;
import android.text.Html;

/**
 * Created by Kyle on 4/11/2015.
 */
public class ShareIntent {
    public static Intent build(String text) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(text).toString());
        sendIntent.setType("text/plain");

        return Intent.createChooser(sendIntent, "Share to...");
    }
}
