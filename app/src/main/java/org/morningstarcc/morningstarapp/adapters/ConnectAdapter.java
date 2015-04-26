package org.morningstarcc.morningstarapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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
    public ConnectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(row_layout, parent, false);

        rootView.setOnClickListener(new ItemClickListener());

        return getViewHolder(rootView);
    }

    @Override
    protected void setupView(ConnectHolder viewHolder, int position) {
        viewHolder.title.setText(data[position].getString("title"));
    }

    @Override
    protected ConnectHolder getViewHolder(View view) {
        return new ConnectHolder(view);
    }

    private class ItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = mRecyclerView.getChildPosition(v);
            Bundle item = data[itemPosition];

            Log.e("exc", item.toString());
            mContext.startActivity(new Intent(mContext, nextActivity).putExtras(item));
        }
    }
}
