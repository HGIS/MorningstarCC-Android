package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import org.morningstarcc.app.App;
import org.morningstarcc.app.data.Connect;
import org.morningstarcc.app.libs.IntentUtils;
import org.morningstarcc.app.viewholders.ConnectHolder;

/**
 * Created by whykalo on 12/21/2014.
 * 11/11/2015 - Juan Manuel Gomez - Added functionality for web url
 */
public class ConnectAdapter extends DatabaseItemAdapter<Connect, ConnectHolder> {

    public ConnectAdapter(Activity mActivity, int row_layout, Connect[] data, Class<? extends Activity> nextActivity) {
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
        viewHolder.title.setText(String.valueOf(data[position].title));
    }

    @Override
    protected ConnectHolder getViewHolder(View view) {
        return new ConnectHolder(view);
    }

    private class ItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = mRecyclerView.getChildPosition(v);
            Connect item = data[itemPosition];

            if (item.title.contains("Email"))
                mActivity.startActivity(IntentUtils.emailIntent(item.weblink));
            else if (item.weblink.trim().length() > 0){
                Uri uri = Uri.parse(item.weblink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mActivity.startActivity(intent);
            }else
                mActivity.startActivity(new Intent(mActivity, nextActivity).putExtra(App.PARCEL, item));
        }
    }
}
