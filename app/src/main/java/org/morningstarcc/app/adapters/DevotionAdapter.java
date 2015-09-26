package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.app.activities.DevotionActivity;
import org.morningstarcc.app.database.Database;
import org.morningstarcc.app.viewholders.DevotionHolder;

import java.util.Date;

import static org.morningstarcc.app.libs.DateUtils.getDate;
import static org.morningstarcc.app.libs.DateUtils.getDayString;
import static org.morningstarcc.app.libs.DateUtils.getMonthString;

/**
 * Created by Kyle on 10/10/2014.
 */
public class DevotionAdapter extends DatabaseItemAdapter<DevotionHolder> {

    Activity mActivity;
    Resources mResources;

    public DevotionAdapter(Activity mActivity, int row_layout, Bundle[] data, Class<? extends Activity> nextActivity) {
        super(mActivity, row_layout, data, nextActivity);
        this.mActivity = mActivity;
        this.mResources = mActivity.getResources();
    }

    @Override
    protected void setupView(DevotionHolder viewHolder, int position) {
        Date date = getDate(data[position].getString("pubDate"));

        viewHolder.month.setText(getMonthString(date));
        viewHolder.day.setText(getDayString(date));
        viewHolder.title.setText(data[position].getString("title"));
        viewHolder.author.setText(data[position].getString("dc:creator"));

        Typeface textStyle = Boolean.parseBoolean(data[position].getString("read"))
                ? Typeface.DEFAULT
                : Typeface.DEFAULT_BOLD;

        viewHolder.title.setTypeface(textStyle);
        viewHolder.author.setTypeface(textStyle);
    }

    @Override
    protected DevotionHolder getViewHolder(View view) {
        return new DevotionHolder(view);
    }
}
