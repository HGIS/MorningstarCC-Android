package org.morningstarcc.app.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.morningstarcc.app.R;

/**
 * Created by Kyle on 4/18/2015.
 */
public class SeriesHolder extends RecyclerView.ViewHolder {
    private View root;
    public ImageView image;
    public TextView title;
    public TextView count;

    public SeriesHolder(View view) {
        super(view);
        this.image = (ImageView) view.findViewById(R.id.image);
        this.title = (TextView) view.findViewById(R.id.title);
        this.count = (TextView) view.findViewById(R.id.count);
    }

    public void setClickable(boolean clickable) {
        root.setClickable(clickable);
    }
}
