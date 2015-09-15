package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.squareup.picasso.Picasso;

import org.morningstarcc.app.R;
import org.morningstarcc.app.viewholders.SeriesCategoryHolder;

/**
 * Created by Kyle on 7/11/2015.
 */
public class SeriesCategoryAdapter extends DatabaseItemAdapter<SeriesCategoryHolder> {

    public SeriesCategoryAdapter(Activity mActivity, int row_layout, Bundle[] data, Class<? extends Activity> nextActivity) {
        super(mActivity, row_layout, data, nextActivity);
    }

    @Override
    protected void setupView(SeriesCategoryHolder viewHolder, int position) {
        Bundle curData = data[position];

        Picasso
            .with(mActivity)
            .load(curData.getString("Imagelink"))
            .placeholder(R.drawable.ic_splash)
            .into(viewHolder.image);
    }

    @Override
    protected SeriesCategoryHolder getViewHolder(View view) {
        return new SeriesCategoryHolder(view);
    }
}