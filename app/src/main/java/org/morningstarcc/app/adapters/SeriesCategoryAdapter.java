package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.squareup.picasso.Picasso;

import org.morningstarcc.app.R;
import org.morningstarcc.app.data.SeriesCategory;
import org.morningstarcc.app.viewholders.SeriesCategoryHolder;

/**
 * Created by Kyle on 7/11/2015.
 */
public class SeriesCategoryAdapter extends DatabaseItemAdapter<SeriesCategory, SeriesCategoryHolder> {

    public SeriesCategoryAdapter(Activity mActivity, int row_layout, SeriesCategory[] data, Class<? extends Activity> nextActivity) {
        super(mActivity, row_layout, data, nextActivity);
    }

    @Override
    protected void setupView(SeriesCategoryHolder viewHolder, int position) {
        SeriesCategory curData = data[position];

        if (TextUtils.isEmpty(curData.Imagelink)) {
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
    }

    @Override
    protected SeriesCategoryHolder getViewHolder(View view) {
        return new SeriesCategoryHolder(view);
    }
}