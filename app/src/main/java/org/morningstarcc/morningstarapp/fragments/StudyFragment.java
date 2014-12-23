package org.morningstarcc.morningstarapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.adapters.DatabaseItemAdapter;
import org.morningstarcc.morningstarapp.adapters.StudyAdapter;
import org.morningstarcc.morningstarapp.datastructures.DatabaseItem;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyFragment extends ListFragment {


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
        ListView list = (ListView) rootView.findViewById(R.id.list);

        adapter = getAdapter(new DatabaseItem(mContext).get(table, R.array.study_fields));
        list.setAdapter(adapter);

        return rootView;
    }
}
