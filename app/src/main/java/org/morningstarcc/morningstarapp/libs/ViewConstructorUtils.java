package org.morningstarcc.morningstarapp.libs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.morningstarcc.morningstarapp.R;

/**
 * A utility class to clean up code for setting commonly handled fields from views found by their id's.
 * All public methods can be statically imported.
 */
public class ViewConstructorUtils {

    /**
     * Sets the text on an indirectly referenced textview
     */

    public static void setText(View parent, int resId, String text) {
        setText(text,(TextView) parent.findViewById(resId));
    }

    public static void setText(Activity activity, int resId, String text) {
        setText(text, (TextView) activity.findViewById(resId));
    }

    private static void setText(String text, TextView into) {
        into.setText(text);
    }

    /**
     * Sets the typeface (normal, bold, or italic & serif or sans-serif) on an indirectly referenced textview
     */

    public static void setTypeface(View parent, int resId, Typeface typeface) {
        setTypeface(typeface, (TextView) parent.findViewById(resId));
    }

    public static void setTypeface(Activity activity, int resId, Typeface typeface) {
        setTypeface(typeface, (TextView) activity.findViewById(resId));
    }

    private static void setTypeface(Typeface typeface, TextView into) {
        into.setTypeface(typeface);
    }

    /**
     * Sets the title in the action bar (using android support libraries)
     */

    public static void setTitle(ActionBarActivity actionBarActivity, String title) {
        ActionBar titleBar = actionBarActivity.getSupportActionBar();
        if (titleBar != null)
            titleBar.setTitle(title);
    }

    /**
     * Uses Picasso library to asynchronously place an image from the given link into an indirectly referenced imageview
     */

    public static void setImageLink(Activity activity, int resId, String link) {
        setImageLink(activity, resId, link, R.drawable.ic_launcher);
    }

    public static void setImageLink(Activity activity, int resId, String link, int errorResId) {
        setImageLink(activity, link, errorResId, (ImageView) activity.findViewById(resId));
    }

    public static void setImageLink(Context context, View parent, int resId, String link) {
        setImageLink(context, parent, resId, link, R.drawable.ic_launcher);
    }

    public static void setImageLink(Context context, View parent, int resId, String link, int errorResId) {
        setImageLink(context, link, errorResId, (ImageView) parent.findViewById(resId));
    }

    private static void setImageLink(Context context, String link, int errorResId, ImageView into) {
        Picasso
                .with(context)
                .load(link)
                .placeholder(R.drawable.ic_launcher)
                .error(errorResId)
                .into(into);
    }
}