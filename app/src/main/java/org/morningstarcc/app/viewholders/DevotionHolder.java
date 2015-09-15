package org.morningstarcc.app.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.morningstarcc.app.R;

/**
 * Created by Kyle on 4/18/2015.
 */
public class DevotionHolder extends RecyclerView.ViewHolder {
    public TextView month;
    public TextView day;
    public TextView title;
    public TextView author;

    public DevotionHolder(View view) {
        super(view);
        this.month = (TextView) view.findViewById(R.id.month);
        this.day = (TextView) view.findViewById(R.id.day);
        this.title = (TextView) view.findViewById(R.id.title);
        this.author = (TextView) view.findViewById(R.id.author);
    }
}
