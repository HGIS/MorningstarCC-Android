package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.R;

import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setImageLink;

/**
 * Created by Kyle on 8/2/2014.
 */
public class SeriesAdapter extends DatabaseItemAdapter {

    public SeriesAdapter(Context mContext, Bundle[] data) {
        super(mContext, R.layout.series_list_row, data);
    }

    @Override
    protected void setupView(View root, int position) {
        setImageLink(mContext, root, R.id.image, data[position].getString("Imagelink"));
    }
}
