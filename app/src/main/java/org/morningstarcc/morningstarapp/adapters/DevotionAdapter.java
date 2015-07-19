package org.morningstarcc.morningstarapp.adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.activities.DevotionActivity;
import org.morningstarcc.morningstarapp.database.Database;
import org.morningstarcc.morningstarapp.viewholders.DevotionHolder;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.getDate;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getDayString;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getMonthString;

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

        Typeface textStyle = isRead(data[position].getString("devoId"))
                ? Typeface.DEFAULT
                : Typeface.DEFAULT_BOLD;

        viewHolder.title.setTypeface(textStyle);
        viewHolder.author.setTypeface(textStyle);
    }

    private boolean isRead(String id) {
        Cursor lookup = Database
                .withContext(mActivity)
                .forTable(DevotionActivity.READ_DEVOTIONS_TABLE)
                .readAll(null)
                .getData();

        if (lookup == null)
            return false;

        int readColumn = lookup.getColumnIndex(DevotionActivity.READ_COLUMN),
            idColumn   = lookup.getColumnIndex("devoId");

        lookup.moveToFirst();
        while (!lookup.isAfterLast()) {
            if (lookup.getString(idColumn).equals(id))
                return lookup.getString(readColumn).equals(DevotionActivity.IS_READ);

            lookup.moveToNext();
        }

        return false;
    }

    @Override
    protected DevotionHolder getViewHolder(View view) {
        return new DevotionHolder(view);
    }
}
