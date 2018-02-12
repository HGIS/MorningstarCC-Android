package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;

import com.squareup.picasso.Picasso;

import org.morningstarcc.app.R;
import org.morningstarcc.app.data.Series;
import org.morningstarcc.app.viewholders.SeriesHolder;

/**
 * Created by Kyle on 8/2/2014.
 */
public class SeriesAdapter extends DatabaseItemAdapter<Series, SeriesHolder> {

    public SeriesAdapter(Activity mActivity, int row_layout, Series[] data, Class<? extends Activity> nextActivity) {
        super(mActivity, row_layout, data, nextActivity);
    }

    @Override
    protected void setupView(SeriesHolder viewHolder, int position) {
        Series curData = data[position];
        int numStudies = Integer.valueOf(curData.StudyCount);

        if (TextUtils.isEmpty(curData.Imagelink) || !URLUtil.isValidUrl(curData.Imagelink)) {
            Picasso
                    .with(mActivity)
                    .load(R.drawable.ic_splash)
                    .into(viewHolder.image);
        } else {
            Picasso
                    .with(mActivity)
                    .load(curData.Imagelink)
                    .placeholder(R.drawable.ic_splash)
                    .into(viewHolder.image);
        }

        viewHolder.title.setText(curData.title);
        viewHolder.count.setText(mActivity.getResources().getQuantityString(R.plurals.study_counter, numStudies, numStudies));
    }

    @Override
    protected SeriesHolder getViewHolder(View view) {
        return new SeriesHolder(view);
    }
}
