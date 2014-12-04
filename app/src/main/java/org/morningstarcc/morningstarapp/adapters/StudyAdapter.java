package org.morningstarcc.morningstarapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import org.morningstarcc.morningstarapp.R;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyAdapter extends DatabaseItemAdapter {

    private static final String IMAGE_THUMBNAIL = "http://img.youtube.com/vi/%s/1.jpg";

    public StudyAdapter(Context mContext, Bundle[] data) {
        super(mContext, R.layout.study_list_row, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(this.row_layout, parent, false);
        }

        setupView(convertView, position);

        return convertView;
    }

    @Override
    protected void setupView(View root, int position) {
        // TODO: put video id in bundle / database
        setText(root, R.id.title, data[position].getString("title"));

        setImageLink(root, R.id.image, String.format(IMAGE_THUMBNAIL, getVideoId(data[position].getString("VideoLink"))));

        setText(root, R.id.title, data[position].getString("title"));
        setText(root, R.id.book, data[position].getString("Scripture"));
        setText(root, R.id.speaker, data[position].getString("Speaker"));
        setText(root, R.id.date, data[position].getString("StudyDate"));
    }

    private String getVideoId(String videoLink) {
        return videoLink.substring(videoLink.lastIndexOf("/") + 1);
    }
}
