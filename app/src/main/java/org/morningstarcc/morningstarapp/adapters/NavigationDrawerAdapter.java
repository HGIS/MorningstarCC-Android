package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.R;

/**
 * Created by Kyle on 11/4/2014.
 */
public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    private String[] elements;
    private Context mContext;

    public NavigationDrawerAdapter(Context context, String[] elements) {
        super(context, android.R.layout.simple_list_item_1, elements);
        this.elements = elements;
        this.mContext = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return !elements[position].equals("break");
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        setDropDownViewResource(isEnabled(position) ? android.R.layout.simple_list_item_1 : R.layout.horizontal_list_item_break);
        View rootView = super.getDropDownView(position, convertView, parent);


        // LOL NOPE
        TextView textView = (TextView) rootView.findViewById(android.R.id.text1);
        textView.setCompoundDrawables(mContext.getResources().getDrawable(android.R.drawable.ic_btn_speak_now), null, null, null);

        return rootView;
    }
}
