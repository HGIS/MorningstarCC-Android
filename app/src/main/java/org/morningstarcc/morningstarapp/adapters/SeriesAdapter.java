package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.datastructures.DatabaseItem;

import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setImageLink;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

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
        setText(root, R.id.title, data[position].getString("title"));
        setText(root, R.id.count, getStudyCount(position));
    }

    private String getStudyCount(int position) {
        int numStudies = getNumStudies(position);

        if (numStudies >= 0)
            return numStudies + " studies";

        return "";
    }

    private int getNumStudies(int position) {
        try {
            return new DatabaseItem(mContext).get("MCCStudiesInSeriesRSS" + data[position].getString("SeriesId"), R.array.study_fields).length;
        }
        catch (NullPointerException e) {
            return -1;
        }
    }
}
