package org.morningstarcc.morningstarapp.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.activities.ConnectActivity;

/**
 * Created by Kyle on 4/19/2015.
 */
public class ConnectHolder extends RecyclerView.ViewHolder {
    public TextView title;

    public ConnectHolder(View view) {
        super(view);
        this.title = (TextView) view.findViewById(android.R.id.text1);
    }
}
