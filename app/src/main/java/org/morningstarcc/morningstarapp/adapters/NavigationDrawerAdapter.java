package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.morningstarcc.morningstarapp.R;

/**
 * Created by Kyle on 11/4/2014.
 */
public class NavigationDrawerAdapter extends ArrayAdapter<String> {

    private String[] elements;

    public NavigationDrawerAdapter(Context context, String[] elements) {
        super(context, android.R.layout.simple_list_item_1, elements);
        this.elements = elements;
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
        setDropDownViewResource(isEnabled(position) ? android.R.layout.simple_list_item_1 : R.layout.list_item_break);
        return super.getDropDownView(position, convertView, parent);
    }
}
