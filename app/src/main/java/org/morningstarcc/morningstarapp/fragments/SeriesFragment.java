package org.morningstarcc.morningstarapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.morningstarcc.morningstarapp.R;

/**
 * Created by Kyle on 7/18/2014.
 */
public class SeriesFragment extends Fragment {
    private Context mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_generic_list, container, false);
        ListView list = (ListView) rootView.findViewById(R.id.list);

        list.setAdapter(new ArrayAdapter<String>(
                mContext,
                android.R.layout.simple_list_item_1,
                mContext.getResources().getStringArray(R.array.drawer_items)
        ));

        return rootView;
    }
}
