package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.morningstarcc.morningstarapp.datastructures.Album;

import java.util.ArrayList;

/**
 * Created by Kyle on 8/2/2014.
 */
public class SeriesAdapter extends ArrayAdapter<Album> {

    //TODO: nope, not this
    public SeriesAdapter(Context mContext, ArrayList<Album> albums) {
        super(mContext, android.R.layout.simple_list_item_1, albums);
    }

}
