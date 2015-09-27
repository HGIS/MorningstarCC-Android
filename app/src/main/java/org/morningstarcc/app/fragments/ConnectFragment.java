package org.morningstarcc.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import org.morningstarcc.app.R;
import org.morningstarcc.app.activities.ConnectActivity;
import org.morningstarcc.app.adapters.ConnectAdapter;
import org.morningstarcc.app.adapters.DatabaseItemAdapter;
import org.morningstarcc.app.data.Connect;
import org.morningstarcc.app.database.Database;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by whykalo on 12/21/2014.
 */
public class ConnectFragment extends RecyclerFragment<Connect> {

    public ConnectFragment() {
        super(Connect.class);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        View rootView = null;

        // if no parent data was provided, load listview with the default data
        if (args == null) {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
            adapter = getAdapter(getDefaultData());

            ((RecyclerView) rootView.findViewById(R.id.recycler)).setAdapter(adapter);
        }

        // if parent data was provided and there are more children, load listview with data
        else if (args.getString("haschild").equalsIgnoreCase("true")) {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
            adapter = getAdapter(getData(args.getString("linkid")));

            ((RecyclerView) rootView.findViewById(R.id.recycler)).setAdapter(adapter);
        }

        // if we have content, display it in a webview
        else if (args.getString("content:encoded") != null && args.getString("content:encoded").length() > 0) {
            rootView = inflater.inflate(R.layout.fragment_connect_content, container, false);

            ((TextView) rootView.findViewById(R.id.content)).setText(Html.fromHtml(args.getString("content:encoded")));
        }

        // if we have no content, activity should have launched a browser with the web link. Otherwise, return null

        return rootView;
    }

    public DatabaseItemAdapter getAdapter(Connect[] data) {
        return new ConnectAdapter(getActivity(), R.layout.connect_list_row, data, ConnectActivity.class);
    }

    // gets the default data for the connect rows
    public Connect[] getDefaultData() {
        Connect[] data;
        try {
            HashMap<String, Object> values = new HashMap<>(2);

            values.put("parentId", "0");
            values.put("isactive", "True");

            List<Connect> dataList = OpenHelperManager.getHelper(getActivity(), Database.class).getDao(Connect.class).queryForFieldValues(values);
            data = dataList.toArray(new Connect[dataList.size()]);
        } catch (SQLException ignored) {
            data = null;
        }

        OpenHelperManager.releaseHelper();
        return data;
    }

    public Connect[] getData(String parentId) {
        if (parentId == null || parentId.equals("0"))
            return getDefaultData();

        Connect[] data;
        try {
            HashMap<String, Object> values = new HashMap<>(2);

            values.put("parentId", parentId);
            values.put("isactive", "True");

            List<Connect> dataList = OpenHelperManager.getHelper(getActivity(), Database.class).getDao(Connect.class).queryForFieldValues(values);
            data = dataList.toArray(new Connect[dataList.size()]);
        } catch (SQLException ignored) {
            data = null;
        }

        OpenHelperManager.releaseHelper();
        return data;
    }
}
