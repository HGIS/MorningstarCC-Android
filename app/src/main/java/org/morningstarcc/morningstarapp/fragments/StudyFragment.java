package org.morningstarcc.morningstarapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.adapters.DatabaseItemAdapter;
import org.morningstarcc.morningstarapp.database.Database;
import org.morningstarcc.morningstarapp.adapters.StudyAdapter;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyFragment extends ListFragment {

    private ListView list = null;

    public StudyFragment() {
        super(null, "MCCStudiesInSeriesRSS", R.array.study_fields);
    }

    @Override
    public void setArguments(Bundle args) {
        this.table += args.getString("SeriesId");
    }

    @Override
    protected DatabaseItemAdapter getAdapter(Bundle[] data) {
        return new StudyAdapter(getActivity(), data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.generic_list, container, false);
        list = (ListView) rootView.findViewById(R.id.list);
        Bundle[] data = Database
                .withContext(mContext)
                .forTable(table)
                .readAll(R.array.study_fields)
                .asBundleArray();

        adapter = getAdapter(data);
        list.setAdapter(adapter);
        list.setSelector(R.drawable.empty);

        return rootView;
    }

    /**
     * Determine how many pixels the listview has scrolled by.
     *
     * Source: http://stackoverflow.com/questions/10808387/android-getting-exact-scroll-position-in-listview/13415273#13415273
     */
    private Dictionary<Integer, Integer> listViewItemHeights = new Hashtable<Integer, Integer>();

    public int getScroll() {
        // my own added touch since i launch the checker before inflating the view
        if (list == null)
            return 0;

        View c = list.getChildAt(0); //this is the first visible row

        // my own added touch in case the list has no elements yet
        if (c == null)
            return 0;

        int scrollY = -c.getTop();
        listViewItemHeights.put(list.getFirstVisiblePosition(), c.getHeight());
        for (int i = 0; i < list.getFirstVisiblePosition(); ++i) {
            if (listViewItemHeights.get(i) != null) // (this is a sanity check)
                scrollY += listViewItemHeights.get(i); //add all heights of the views that are gone
        }

        return scrollY;
    }
}
