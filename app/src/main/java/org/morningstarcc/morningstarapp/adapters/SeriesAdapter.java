package org.morningstarcc.morningstarapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.squareup.picasso.Picasso;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.database.Database;
import org.morningstarcc.morningstarapp.viewholders.SeriesHolder;

import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setImageLink;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by Kyle on 8/2/2014.
 */
public class SeriesAdapter extends DatabaseItemAdapter<SeriesHolder> {

    public SeriesAdapter(Context mContext, int row_layout, Bundle[] data, Class<? extends Activity> nextActivity) {
        super(mContext, row_layout, data, nextActivity);
    }

    @Override
    protected void setupView(SeriesHolder viewHolder, int position) {
        Bundle curData = data[position];

        Picasso
                .with(mContext)
                .load(curData.getString("Imagelink"))
                .placeholder(R.drawable.ic_launcher)
                .into(viewHolder.image);

        viewHolder.title.setText(curData.getString("title"));
        viewHolder.count.setText(getStudyCount(curData));
    }

    private String getStudyCount(Bundle data) {
        long numStudies = getNumStudies(data);

        if (numStudies > 0)
            return numStudies + " studies";

        return "";
    }

    private long getNumStudies(Bundle data) {
        try {
            return Database.withContext(mContext)
                    .forTable("MCCStudiesInSeriesRSS" + data.getString("SeriesId"))
                    .getCount();
        }
        catch (NullPointerException e) {
            return -1;
        }
    }

    @Override
    protected SeriesHolder getViewHolder(View view) {
        return new SeriesHolder(view);
    }
}
