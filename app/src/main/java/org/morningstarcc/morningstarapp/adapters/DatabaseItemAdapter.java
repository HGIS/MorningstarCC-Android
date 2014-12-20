package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by Kyle on 10/10/2014.
 */
public abstract class DatabaseItemAdapter extends ArrayAdapter<Bundle> {

    protected LayoutInflater mInflater;
    protected Bundle[] data;

    protected int row_layout;

    protected Context mContext;

    public DatabaseItemAdapter(Context mContext, int row_layout, Bundle[] data) {
        super(mContext, row_layout, data);

        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.row_layout = row_layout;
        this.mContext = mContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(this.row_layout, parent, false);
        }

        setupView(convertView, position);

        return convertView;
    }

    protected abstract void setupView(View root, int position);
}
