package org.morningstarcc.app.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.DevotionActivity;
import org.morningstarcc.app.adapters.DevotionAdapter;
import org.morningstarcc.app.data.Devotion;

/**
 * Created by Kyle on 7/19/2014.
 */
public class DevotionFragment extends RecyclerFragment<Devotion> {

    public DevotionFragment() {
        super(Devotion.class);
    }

    @Override
    protected RecyclerView.Adapter getAdapter(Bundle[] data) {
        return new DevotionAdapter(getActivity(), R.layout.devotion_list_row, data, DevotionActivity.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.adapter.notifyDataSetChanged();
    }
}
