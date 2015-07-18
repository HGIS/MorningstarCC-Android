package org.morningstarcc.morningstarapp.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.morningstarcc.morningstarapp.R;

/**
 * Created by Kyle on 7/11/2015.
 */
public class SeriesCategoryHolder extends RecyclerView.ViewHolder {
    public ImageView image;

    public SeriesCategoryHolder(View view) {
        super(view);
        this.image = (ImageView) view.findViewById(R.id.image);
    }
}
