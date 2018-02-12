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

import org.morningstarcc.app.App;
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
 *
 * History:
 * 11/11/2015 - Juan Manuel Gomez - Added validation for extras in the intent for parcel
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

        //Read parcelable data
        if (args != null && args.containsKey(App.PARCEL)){
            Connect data = (Connect)args.get(App.PARCEL);

            if (data != null && data.haschild != null && data.haschild.equalsIgnoreCase("true")) {
                //Sub Options
                rootView = super.onCreateView(inflater, container, savedInstanceState);
                adapter = getAdapter(getData(data.linkid));
                ((RecyclerView) rootView.findViewById(R.id.recycler)).setAdapter(adapter);
            }else if (data.encoded != null && data.encoded.length() > 0) {
                //HTML
                rootView = inflater.inflate(R.layout.fragment_connect_content, container, false);
                ((TextView) rootView.findViewById(R.id.content)).setText(Html.fromHtml(data.encoded));

            }/*else{
                //Error
            }*/

        }else{
            //Default
            rootView = super.onCreateView(inflater, container, savedInstanceState);
            adapter = getAdapter(getDefaultData());
            ((RecyclerView) rootView.findViewById(R.id.recycler)).setAdapter(adapter);
        }

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
