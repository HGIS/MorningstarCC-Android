package org.morningstarcc.morningstarapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.activities.ConnectActivity;
import org.morningstarcc.morningstarapp.adapters.ConnectAdapter;
import org.morningstarcc.morningstarapp.database.Database;
import org.morningstarcc.morningstarapp.intents.WebViewIntent;

import java.util.Arrays;

/**
 * Created by whykalo on 12/21/2014.
 */
public class ConnectFragment extends ListFragment {

    public ConnectFragment() {
        super(ConnectActivity.class, "", R.array.connect_fields);
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

            ((ListView) rootView.findViewById(R.id.list)).setAdapter(adapter);
        }

        // if parent data was provided and there are more children, load listview with data
        else if (args.getString("haschild").equalsIgnoreCase("true")) {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
            adapter = getAdapter(getData(args.getString("linkid")));

            ((ListView) rootView.findViewById(R.id.list)).setAdapter(adapter);
        }

        // if we have content, display it in a webview
        else if (args.getString("content:encoded") != null && args.getString("content:encoded").length() > 0) {
            rootView = inflater.inflate(R.layout.fragment_connect_content, container, false);

            ((TextView) rootView.findViewById(R.id.content)).setText(Html.fromHtml(args.getString("content:encoded")));
        }

        // if we have no content, activity should have launched a browser with the web link. Otherwise, return null

        return rootView;
    }

    public ArrayAdapter getAdapter(Bundle[] data) {
        return new ConnectAdapter(mContext, data);
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
