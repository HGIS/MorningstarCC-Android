package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.R;

import java.util.ArrayList;

/**
 * Created by whykalo on 12/20/2014.
 */
public class EventAdapter extends DatabaseItemAdapter {

    public EventAdapter(Context mContext, Bundle[] events) {
        super(mContext, R.layout.event_list_row, trim(events));
    }

    @Override
    protected void setupView(View root, int position) {

    }

    private static Bundle[] trim(Bundle[] toTrim) {
        ArrayList<Bundle> trimmed = new ArrayList<Bundle>();

        for (Bundle event : toTrim)
            if (event.getString("imagepath") != null)
                trimmed.add(event);

        return trimmed.toArray(new Bundle[trimmed.size()]);
    }
}
