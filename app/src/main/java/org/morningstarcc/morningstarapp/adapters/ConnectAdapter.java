package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.morningstarcc.morningstarapp.R;

import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by whykalo on 12/21/2014.
 */
public class ConnectAdapter extends BaseAdapter {

    private static LayoutInflater mLayoutInflater;

    private static String[] titles;

    public ConnectAdapter(Context mContext) {
        super();

        titles = mContext.getResources().getStringArray(R.array.connect_titles);
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return titles[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.connect_list_row, parent, false);

        setText(convertView, R.id.title, titles[position]);

        return convertView;
    }
}
