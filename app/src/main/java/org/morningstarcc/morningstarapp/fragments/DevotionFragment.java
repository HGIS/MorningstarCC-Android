package org.morningstarcc.morningstarapp.fragments;

import android.os.Bundle;

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

    private static boolean hasPrinted = false;
    @Override
    protected DatabaseItemAdapter getAdapter(Bundle[] data) {
        if (!hasPrinted) {
            android.util.Log.e("Devotions", java.util.Arrays.deepToString(data));
            hasPrinted = true;
        }


        return new DevotionAdapter(mContext, data);
    }
}
