package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.R;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyAdapter extends DatabaseItemAdapter {

    public StudyAdapter(Context mContext, Bundle[] data) {
        super(mContext, R.layout.study_list_row, data);
    }

    @Override
    protected void setupView(View root, int position) {
        setText(root, R.id.title, data[position].getString("title"));
    }
}
