package org.morningstarcc.morningstarapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.ConnectActivity;
import org.morningstarcc.morningstarapp.adapters.ConnectAdapter;
import org.morningstarcc.morningstarapp.adapters.DatabaseItemAdapter;
import org.morningstarcc.morningstarapp.database.Database;

/**
 * Created by whykalo on 12/21/2014.
 */
public class ConnectFragment extends RecyclerFragment {

    public ConnectFragment() {
        super("", R.array.connect_fields);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.table = Database.getTableName(activity.getString(R.string.connect_url));
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

    public DatabaseItemAdapter getAdapter(Bundle[] data) {
        return new ConnectAdapter(mContext, R.layout.connect_list_row, data, ConnectActivity.class);
    }

    // gets the default data for the connect rows
    public Bundle[] getDefaultData() {
        return Database
                .withContext(mContext)
                .forTable(table)
                .read(null, "parentId = \"0\" AND LOWER(isactive) = \"true\"", null)
                .asBundleArray();
    }

    public Bundle[] getData(String parentId) {
        if (parentId == null || parentId.equals("0"))
            return getDefaultData();

        return Database
                .withContext(mContext)
                .forTable(table)
                .read(null, "parentId = \"" + parentId + "\" AND LOWER(isactive) = \"true\"", null)
                .asBundleArray();
    }
}
