package org.morningstarcc.morningstarapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.EventActivity;
import org.morningstarcc.morningstarapp.database.Database;
import org.morningstarcc.morningstarapp.adapters.ExpandableEventAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle on 7/19/2014.
 */
public class ExpandableEventFragment extends Fragment {
    protected Context mContext;
    protected BaseExpandableListAdapter adapter;
    protected String table;
    private int arrayResId;

    public ExpandableEventFragment() {
        super();
        this.table = "MCCEventsRSS";
        this.arrayResId = R.array.event_fields;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expandable_list, container, false);
        ExpandableListView list = (ExpandableListView) rootView.findViewById(R.id.expandable_list);
        Bundle[] data = Database
                .withContext(mContext)
                .forTable(table)
                .readAll(arrayResId)
                .asBundleArray();

        adapter = getAdapter(data);
        list.setAdapter(adapter);
        list.setOnChildClickListener(new ItemClickListener());
        expandAll(list, adapter);

        return rootView;
    }

    protected BaseExpandableListAdapter getAdapter(Bundle[] events) {
        List<String> titles = new ArrayList<String>();
        List<List<Bundle>> bucketedEvents = new ArrayList<List<Bundle>>();

        for (Bundle event : events) {
            String eventDate = event.getString("eventstarttime");
            eventDate = eventDate.substring(0, eventDate.indexOf(' '));
            int idx = titles.indexOf(eventDate);

            if (idx >= 0)
                bucketedEvents.get(idx).add(event);
            else {
                List<Bundle> eventBucket = new ArrayList<Bundle>();

                titles.add(eventDate);
                eventBucket.add(event);
                bucketedEvents.add(eventBucket);
            }
        }

        return new ExpandableEventAdapter(mContext, titles, bucketedEvents);
    }

    private class ItemClickListener implements ExpandableListView.OnChildClickListener {
        @Override
        public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
            try {
                mContext.startActivity(new Intent(mContext, EventActivity.class).putExtras((Bundle) adapter.getChild(groupPosition, childPosition)));
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
    }

    private void expandAll(ExpandableListView toExpand, ExpandableListAdapter adapter) {
        for (int i = 0; i < adapter.getGroupCount(); i++)
            toExpand.expandGroup(i);
    }
}
