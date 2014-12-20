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

import org.morningstarcc.morningstarapp.R;

/**
 * Created by whykalo on 12/17/2014.
 */
public class ViewConstructorUtils {
    public static void setText(View parent, int resId, String text) {
        ((TextView) parent.findViewById(resId)).setText(text);
    }

    public static void setText(Activity activity, int resId, String text) {
        ((TextView) activity.findViewById(resId)).setText(text);
    }

    public static void setTitle(ActionBarActivity actionBarActivity, String title) {
        ActionBar titleBar = actionBarActivity.getSupportActionBar();
        if (titleBar != null) {
            titleBar.setTitle(title);
        }
    }

    public static void setImageLink(Activity activity, int resId, String link) {
        Picasso
                .with(activity)
                .load(link)
                .placeholder(R.drawable.ic_launcher)
                .into((ImageView) activity.findViewById(resId));
    }

    public static void setImageLink(Context context, View parent, int resId, String link) {
        Picasso
                .with(context)
                .load(link)
                .placeholder(R.drawable.ic_launcher)
                .into((ImageView) parent.findViewById(resId));
    }

    public static void setTypeface(View parent, int resId, Typeface typeface) {
        ((TextView) parent.findViewById(resId)).setTypeface(typeface);
    }
}
