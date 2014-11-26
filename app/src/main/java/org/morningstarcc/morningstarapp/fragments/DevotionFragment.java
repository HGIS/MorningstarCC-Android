package org.morningstarcc.morningstarapp.fragments;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.DevotionActivity;
import org.morningstarcc.morningstarapp.adapters.DatabaseItemAdapter;
import org.morningstarcc.morningstarapp.adapters.DevotionAdapter;

/**
 * Created by Kyle on 7/19/2014.
 */
public class DevotionFragment extends ListFragment {

    public DevotionFragment() {
        super(DevotionActivity.class, "MCCDailyDevoRSS", R.array.devotion_fields);
    }

    @Override
    protected DatabaseItemAdapter getAdapter(Bundle[] data) {
        return new DevotionAdapter(mContext, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.adapter.notifyDataSetChanged();
    }
}
