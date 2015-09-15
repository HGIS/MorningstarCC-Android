package org.morningstarcc.app.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
