package org.morningstarcc.morningstarapp.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.DevotionActivity;
import org.morningstarcc.morningstarapp.adapters.DevotionAdapter;

/**
 * Created by Kyle on 7/19/2014.
 */
public class DevotionFragment extends RecyclerFragment {

    public DevotionFragment() {
        super("MCCDailyDevoRSS", R.array.devotion_fields);
    }

    @Override
    protected RecyclerView.Adapter getAdapter(Bundle[] data) {
        return new DevotionAdapter(mContext, R.layout.devotion_list_row, data, DevotionActivity.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.adapter.notifyDataSetChanged();
    }
}
