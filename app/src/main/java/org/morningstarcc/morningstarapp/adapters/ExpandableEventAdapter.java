package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import org.morningstarcc.morningstarapp.R;

import java.util.Date;
import java.util.List;

import static org.morningstarcc.morningstarapp.libs.DateUtils.getDate;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getDateInterval;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by Kyle on 10/10/2014.
 */
public class ExpandableEventAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> titles;
    private List<List<Bundle>> events;

    public ExpandableEventAdapter(Context context, List<String> titles, List<List<Bundle>> events) {
        super();
        if (titles.size() != events.size()) {
            throw new IllegalArgumentException("Titles and Contents must be the same size.");
        }

        this.mContext = context;
        this.titles = titles;
        this.events = events;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Date eventDate = getDate(titles.get(groupPosition));

        if (convertView == null) {
            convertView = ((LayoutInflater) mContext
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                            .inflate(R.layout.expandable_event_header_row, parent, false);
        }

        setText(convertView, R.id.title, getDateInterval(eventDate, eventDate));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Bundle event = events.get(groupPosition).get(childPosition);

        if (convertView == null) {
            convertView = ((LayoutInflater) mContext
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                                .inflate(R.layout.expandable_event_list_row, parent, false);
        }

        setText(convertView, R.id.title, event.getString("title"));
        setText(convertView, R.id.time, event.getString("eventstarttime"));

        return convertView;
    }

    @Override
    public List<Bundle> getGroup(int groupPosition) {
        return events.get(groupPosition);
    }

    @Override
    public Bundle getChild(int groupPosition, int childPosition) {
        return events.get(groupPosition).get(childPosition);
    }

    @Override
    public int getGroupCount() {
        return events.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return events.get(groupPosition).size();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }
}
