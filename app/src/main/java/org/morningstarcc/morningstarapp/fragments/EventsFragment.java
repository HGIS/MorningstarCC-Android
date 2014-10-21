package org.morningstarcc.morningstarapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.EventActivity;
import org.morningstarcc.morningstarapp.adapters.EventAdapter;
import org.morningstarcc.morningstarapp.datastructures.Event;
import org.morningstarcc.morningstarapp.libs.DatabaseStorage;

/**
 * Created by Kyle on 7/19/2014.
 */
public class EventsFragment extends Fragment {

    private Context mContext;
    private ListAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_generic_list, container, false);
        ListView list = (ListView) rootView.findViewById(R.id.list);

        getAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new EventClickListener());

        return rootView;
    }

    private void getAdapter() {
        DatabaseStorage localStorage = new DatabaseStorage(mContext);
        Cursor eventData = localStorage.get("MCCEventsRSS", "eventdate", "title", "pubDate", "eventstarttime", "eventendtime", "description", "registration", "link");
        adapter = new EventAdapter(mContext, Event.getEvents(eventData));

        localStorage.onPostGet();
    }

    private class EventClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent eventDetails = new Intent(mContext, EventActivity.class);
            Event event = (Event) adapter.getItem(position);

            eventDetails.putExtra("title", event.title);

            mContext.startActivity(eventDetails);
        }
    }
}
