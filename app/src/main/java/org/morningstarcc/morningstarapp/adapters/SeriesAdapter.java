package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.libs.RemoteImageView;

/**
 * Created by Kyle on 8/2/2014.
 */
public class SeriesAdapter extends DatabaseItemAdapter {

    public SeriesAdapter(Context mContext, Bundle[] data) {
        super(mContext, R.layout.series_list_row, data);
    }

    @Override
    protected void setupView(View root, int position) {
        // TODO: somehow this sometimes fails (E/BitmapFactory﹕ Unable to decode stream: java.io.FileNotFoundException: /: open failed: EISDIR (Is a directory))
        ((RemoteImageView) root.findViewById(R.id.image)).setImageLink(data[position].getString("Imagelink"));
    }
}
