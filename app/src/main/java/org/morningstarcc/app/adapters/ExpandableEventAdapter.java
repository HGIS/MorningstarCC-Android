package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;

import org.morningstarcc.app.App;
import org.morningstarcc.app.data.Event;
import org.morningstarcc.app.libs.SectionableList;
import org.morningstarcc.app.viewholders.ExpandableEventHolder;

import java.util.Comparator;
import java.util.Date;

import static org.morningstarcc.app.libs.DateUtils.getDate;
import static org.morningstarcc.app.libs.DateUtils.getFullDate;
import static org.morningstarcc.app.libs.DateUtils.getFullDayString;
import static org.morningstarcc.app.libs.DateUtils.getTimeOfDay;

/**
 * Created by Kyle on 10/10/2014.
 */
public class ExpandableEventAdapter extends DatabaseItemAdapter<Event, ExpandableEventHolder> {
    public static final int HEADER = 0;
    public static final int BODY = 1;

    private SectionableList<Event> data;
    private int row_layout_header;

    public ExpandableEventAdapter(Activity mActivity, int row_layout_header, int row_layout_list, Event[] data, Class<? extends Activity> nextActivity) {
        super(mActivity, row_layout_list, data, nextActivity);
        this.row_layout_header = row_layout_header;
        this.data = new SectionableList<Event>(data, new EventSorter()) {
            @Override
            public Event buildHeader(Event item) {
                Event header = new Event();

                header.eventstarttime = item.eventstarttime;

                return header;
            }
        };
    }

    @Override
    public ExpandableEventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView;

        if (viewType != HEADER) {
            rootView = mInflater.inflate(row_layout, parent, false);

            rootView.setOnClickListener(new ItemClickListener());
        }
        else
            rootView = mInflater.inflate(row_layout_header, parent, false);

        return new ExpandableEventHolder(rootView, viewType);
    }

    @Override
    protected void setupView(ExpandableEventHolder holder, int position) {
        Event bundle = this.data.get(position);
        Date day = getFullDate(bundle.eventstarttime);
        String title = String.valueOf(bundle.title);

        try {
            title = Html.fromHtml(bundle.title).toString();
        } catch (RuntimeException e) {}

        holder.title.setText(title);
        if (holder.time != null) {
            if (day != null) {
                holder.time.setText(getTimeOfDay(day));
            } else {
                holder.time.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (this.data.isHeader(position))
            return HEADER;
        return BODY;
    }

    @Override
    protected ExpandableEventHolder getViewHolder(View view) {
        return null;
    }

    private class EventSorter implements Comparator<Event> {
        @Override
        public int compare(Event lhs, Event rhs) {
            Date start = getDate(lhs.eventstarttime.split(" ")[0]);
            Date end = getDate(rhs.eventstarttime.split(" ")[0]);

            return start.compareTo(end);
        }
    }

    private class ItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = mRecyclerView.getChildPosition(v);
            Event item = data.get(itemPosition);
            mActivity.startActivity(new Intent(mActivity, nextActivity).putExtra(App.PARCEL, item));
        }
    }
}
