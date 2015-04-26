package org.morningstarcc.morningstarapp.fragments;

import android.os.Bundle;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.EventActivity;
import org.morningstarcc.morningstarapp.adapters.DatabaseItemAdapter;
import org.morningstarcc.morningstarapp.adapters.ExpandableEventAdapter;

/**
 * Created by Kyle on 7/19/2014.
 */
public class ExpandableEventFragment extends RecyclerFragment {

    public ExpandableEventFragment() {
        super("MCCEventsRSS", R.array.event_fields);
    }

    @Override
    protected DatabaseItemAdapter getAdapter(Bundle[] data) {
        return new ExpandableEventAdapter(mContext, R.layout.expandable_event_header_row, R.layout.expandable_event_list_row, data, EventActivity.class);
    }
}
