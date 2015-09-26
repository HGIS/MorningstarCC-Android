package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
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
        int numStudies = getStudyCount(curData);
        String image = curData.getString("Imagelink");

        if (TextUtils.isEmpty(image)) {
            Picasso
                    .with(mActivity)
                    .load(R.drawable.ic_splash)
                    .into(viewHolder.image);
        } else {
            Picasso
                    .with(mActivity)
                    .load(image)
                    .placeholder(R.drawable.ic_splash)
                    .into(viewHolder.image);
        }

        viewHolder.title.setText(curData.getString("title"));
        viewHolder.count.setText(mActivity.getResources().getQuantityString(R.plurals.study_counter, numStudies, numStudies));
    }

    private int getStudyCount(Bundle data) {
        int size = 0;
        try {
            size = OpenHelperManager.getHelper(mActivity, Database.class)
                    .getDao(Study.class)
                    .queryForEq("SeriesId", data.getString("SeriesId"))
                    .size();
        }
        catch (SQLException e) {
        }

        OpenHelperManager.releaseHelper();
        return size;
    }

    @Override
    protected SeriesHolder getViewHolder(View view) {
        return new SeriesHolder(view);
    }
}
