package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import org.morningstarcc.app.R;
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
        Bundle devotion = data[position];
        String dateString = devotion.getString("pubDate");
        String author = devotion.getString("dccreator");
        String read = devotion.getString("read");

        Date date = TextUtils.isEmpty(dateString) ? null : getDate(dateString);

        if (date != null) {
            viewHolder.month.setText(getMonthString(date));
            viewHolder.day.setText(getDayString(date));
        } else {
            viewHolder.month.setVisibility(View.GONE);
            viewHolder.day.setVisibility(View.GONE);
        }
        viewHolder.title.setText(String.valueOf(devotion.getString("title")));
        viewHolder.author.setText(TextUtils.isEmpty(author) ? mActivity.getString(R.string.unknown_author) : author);

        Typeface textStyle = !TextUtils.isEmpty(read) && Boolean.parseBoolean(read)
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
