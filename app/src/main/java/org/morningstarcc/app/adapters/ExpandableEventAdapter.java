package org.morningstarcc.app.adapters;

import android.app.Activity;
import android.content.Intent;
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
import static org.morningstarcc.app.libs.DateUtils.getFullDayYearString;
import static org.morningstarcc.app.libs.DateUtils.getTimeOfDay;

/**
 * Created by Kyle on 10/10/2014.
 *
 * History:
 * 11/11/2015 - Juan Manuel Gomez - Added title in the constructor
 * 11/21/2015 - Juan Manuel Gomez - Change title from title to event time
 * 11/21/2015 - Juan Manuel Gomez - Added event end time
 */
public class ExpandableEventAdapter extends DatabaseItemAdapter<Event, ExpandableEventHolder> {
    public static final int HEADER = 0;
    public static final int BODY = 1;
    private static final int TYPE_TAG = "view_type".hashCode();

    private StringCache[] cache;
    private SectionableList<Event> data;
    private int row_layout_header;

    public ExpandableEventAdapter(Activity mActivity, int row_layout_header, int row_layout_list, Event[] data, Class<? extends Activity> nextActivity) {
        super(mActivity, row_layout_list, data, nextActivity);
        this.cache = new StringCache[data.length];
        this.row_layout_header = row_layout_header;
        this.data = new SectionableList<Event>(data, new EventSorter()) {
            @Override
            public Event buildHeader(Event item) {
                Event header = new Event();

                header.eventstarttime = item.eventstarttime;
                header.eventendtime = item.eventendtime;
                header.eventendtime = item.eventendtime;

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
            rootView.setTag(TYPE_TAG, HEADER);
        }
        else {
            rootView = mInflater.inflate(row_layout_header, parent, false);
            rootView.setTag(TYPE_TAG, BODY);
        }

        return new ExpandableEventHolder(rootView, viewType);
    }

    @Override
    protected void setupView(ExpandableEventHolder holder, int position) {
        if (cache[position] != null) {
            holder.title.setText(cache[position].title);
            if (holder.time != null) {
                holder.time.setText(cache[position].startDate);
                holder.endTime.setText(cache[position].endDate);
            }
            return;
        }

        Event bundle = this.data.get(position);
        StringCache cur = new StringCache();

        holder.title.setText(cur.title = getTitle(bundle));

        if (holder.time != null) {
            Date day = getFullDate(bundle.eventstarttime);
            Date endTime = getFullDate(bundle.eventendtime);

            if (day != null) {
                holder.time.setText(cur.startDate = getTimeOfDay(day));
                holder.endTime.setText(cur.endDate = getTimeOfDay(endTime));
            } else {
                holder.time.setVisibility(View.GONE);
            }
        }

        cache[position] = cur;
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.data.isHeader(position) ? HEADER : BODY;
    }

    @Override
    protected ExpandableEventHolder getViewHolder(View view) {
        Integer type = (Integer) view.getTag(TYPE_TAG);

        if (type == null) {
            return null;
        } else if (type == HEADER) {
            return new ExpandableEventHolder(view, HEADER);
        } else if (type == BODY) {
            return new ExpandableEventHolder(view, BODY);
        }

        return null;
    }

    private String getTitle(Event from) {
        if (from.title == null ){
            //Format the date
            Date eventDate = getFullDate(from.eventstarttime);
            return String.valueOf(getFullDayYearString(eventDate));
        } else {
            try {
                return String.valueOf(Html.fromHtml(from.title));
            } catch (RuntimeException e) {
                return "";
            }
        }
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
            Event item = data.get(mRecyclerView.getChildAdapterPosition(v));
            mActivity.startActivity(new Intent(mActivity, nextActivity).putExtra(App.PARCEL, item));
        }
    }

    private static class StringCache {
        String title, startDate, endDate;
    }
}
