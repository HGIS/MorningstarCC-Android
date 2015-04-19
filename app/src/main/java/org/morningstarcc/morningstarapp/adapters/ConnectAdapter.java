package org.morningstarcc.morningstarapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.viewholders.ConnectHolder;

import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by whykalo on 12/21/2014.
 */
public class ConnectAdapter extends DatabaseItemAdapter<ConnectHolder> {

    public ConnectAdapter(Context mContext, int row_layout, Bundle[] data, Class<? extends Activity> nextActivity) {
        super(mContext, row_layout, data, nextActivity);
    }

    @Override
    protected void setupView(ConnectHolder viewHolder, int position) {
        viewHolder.title.setText(data[position].getString("title"));
    }

    @Override
    protected ConnectHolder getViewHolder(View view) {
        return new ConnectHolder(view);
    }
}
