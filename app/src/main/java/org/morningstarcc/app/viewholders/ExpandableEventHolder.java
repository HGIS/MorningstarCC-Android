package org.morningstarcc.app.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.morningstarcc.app.R;
import org.morningstarcc.app.adapters.ExpandableEventAdapter;
import org.w3c.dom.Text;

/**
 * Created by Kyle on 4/25/2015.
 * 11/21/2015 - Juan Manuel Gomez - Added event end time
 */
public class ExpandableEventHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView time;
    public TextView endTime;

    public ExpandableEventHolder(View itemView, int viewType) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.title);

        if (viewType == ExpandableEventAdapter.BODY) {
            this.time = (TextView) itemView.findViewById(R.id.time);
            this.endTime = (TextView) itemView.findViewById(R.id.end_time);
        }
    }
}
