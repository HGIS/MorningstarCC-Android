package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.squareup.picasso.Picasso;

import org.morningstarcc.app.R;
import org.morningstarcc.app.data.Study;
import org.morningstarcc.app.database.Database;
import org.morningstarcc.app.viewholders.SeriesHolder;

import java.sql.SQLException;

/**
 * Created by Kyle on 8/2/2014.
 */
public class SeriesAdapter extends DatabaseItemAdapter<SeriesHolder> {

    public SeriesAdapter(Activity mActivity, int row_layout, Bundle[] data, Class<? extends Activity> nextActivity) {
        super(mActivity, row_layout, data, nextActivity);
    }

    @Override
    protected void setupView(SeriesHolder viewHolder, int position) {
        Bundle curData = data[position];
        long numStudies = getStudyCount(curData);

        Picasso
                .with(mActivity)
                .load(curData.getString("Imagelink"))
                .placeholder(R.drawable.ic_splash)
                .into(viewHolder.image);

        viewHolder.title.setText(curData.getString("title"));
        viewHolder.count.setText(numStudies + (numStudies == 1 ? " study" : " studies"));
    }

    private long getStudyCount(Bundle data) {
        return Math.max(getNumStudies(data), 0);
    }

    private long getNumStudies(Bundle data) {
        try {
            return OpenHelperManager.getHelper(mActivity, Database.class)
                    .getDao(Study.class)
                    .queryForEq("SeriesId", data.getString("SeriesId"))
                    .size();
        }
        catch (SQLException e) {
            return -1;
        }
    }

    @Override
    protected SeriesHolder getViewHolder(View view) {
        return new SeriesHolder(view);
    }
}
