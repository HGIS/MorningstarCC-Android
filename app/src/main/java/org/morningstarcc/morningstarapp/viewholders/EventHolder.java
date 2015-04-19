package org.morningstarcc.morningstarapp.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.R;

/**
 * Created by Kyle on 4/18/2015.
 */
public class EventHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public TextView date;
    public TextView time;

    public EventHolder(View view) {
        super(view);
        this.image = (ImageView) view.findViewById(R.id.image);
        this.date = (TextView) view.findViewById(R.id.date);
        this.time = (TextView) view.findViewById(R.id.time);
    }
}
