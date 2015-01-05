package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.fragments.ConnectFragment;

import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by whykalo on 12/21/2014.
 */
public class ConnectAdapter extends DatabaseItemAdapter {

    public ConnectAdapter(Context mContext, Bundle[] data) {
        super(mContext, R.layout.connect_list_row, data);
    }

    @Override
    protected void setupView(View root, int position) {
        setText(root, android.R.id.text1, data[position].getString("title"));
    }
}
