package org.morningstarcc.morningstarapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.database.Database;
import org.morningstarcc.morningstarapp.intents.EmailIntent;

/**
 * Created by Kyle on 7/19/2014.
 */
public abstract class ListFragment extends Fragment {

    protected Context mContext;
    protected ArrayAdapter adapter;
    private Class<? extends Activity> nextActivity;
    protected String table;
    private int arrayResId;

    protected ListFragment(Class<? extends Activity> nextActivity, String table, int arrayResId) {
        super();
        this.nextActivity = nextActivity;
        this.table = table;
        this.arrayResId = arrayResId;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.generic_list, container, false);
        ListView list = (ListView) rootView.findViewById(R.id.list);
        Bundle[] data = Database
                .withContext(mContext)
                .forTable(table)
                .readAll(arrayResId)
                .asBundleArray();

        adapter = getAdapter(data);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new ItemClickListener());

        return rootView;
    }

    protected abstract ArrayAdapter getAdapter(Bundle[] data);

    protected class ItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle item = (Bundle) adapter.getItem(position);

            // email intents are send, all others are considered view
            if (item.getString("title").toLowerCase().contains("email"))
                mContext.startActivity(EmailIntent.build(item.getString("weblink")));
            else
                mContext.startActivity(new Intent(mContext, nextActivity).putExtras(item) );
        }
    }
}
