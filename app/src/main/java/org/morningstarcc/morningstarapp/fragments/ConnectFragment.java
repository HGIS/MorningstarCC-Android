package org.morningstarcc.morningstarapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.ConnectActivity;
import org.morningstarcc.morningstarapp.adapters.ConnectAdapter;

/**
 * Created by whykalo on 12/21/2014.
 */
public class ConnectFragment extends Fragment {

    private Context mContext;
    private BaseAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.generic_list, container, false);
        ListView list = (ListView) rootView.findViewById(R.id.list);

        list.setAdapter(new ConnectAdapter(mContext));
        list.setOnItemClickListener(new ItemClickListener());

        return rootView;
    }

    private class ItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Class nextActivity = getActivityForPosition(position);

            Bundle item = (Bundle) adapter.getItem(position);
            mContext.startActivity( new Intent(mContext, nextActivity) );
        }
    }

    private Class getActivityForPosition(int position) {
        return ConnectActivity.class;
    }
}
