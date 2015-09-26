package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import org.morningstarcc.app.data.Connect;
import org.morningstarcc.app.libs.IntentUtils;
import org.morningstarcc.app.viewholders.ConnectHolder;

/**
 * Created by whykalo on 12/21/2014.
 */
public class ConnectAdapter extends DatabaseItemAdapter<ConnectHolder> {

    public ConnectAdapter(Activity mActivity, int row_layout, Bundle[] data, Class<? extends Activity> nextActivity) {
        super(mActivity, row_layout, data, nextActivity);
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

            if (item.getString("title").contains("Email"))
                mActivity.startActivity(IntentUtils.emailIntent(item.getString("weblink")));
            else
                mActivity.startActivity(new Intent(mActivity, nextActivity).putExtras(item));
        }
    }
}
