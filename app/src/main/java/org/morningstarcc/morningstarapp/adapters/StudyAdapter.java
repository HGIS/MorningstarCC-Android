package org.morningstarcc.morningstarapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import org.morningstarcc.morningstarapp.R;

import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setImageLink;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyAdapter extends DatabaseItemAdapter {

    private Activity mActivity;

    private static String VIDEO_LINK = "vnd.youtube://";
    private static final String IMAGE_THUMBNAIL = "http://img.youtube.com/vi/%s/1.jpg";

    public StudyAdapter(Activity activity, Bundle[] data) {
        super(activity, R.layout.study_list_row, data);
        mActivity = activity;
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
    protected void setupView(View root, final int position) {
        setText(root, R.id.title, data[position].getString("title"));

        setImageLink(mContext, root, R.id.image, String.format(IMAGE_THUMBNAIL, getVideoId(data[position].getString("VideoLink"))));

        setText(root, R.id.title, data[position].getString("title"));
        setText(root, R.id.book, data[position].getString("Scripture"));
        setText(root, R.id.speaker, data[position].getString("Speaker"));
        setText(root, R.id.date, data[position].getString("StudyDate"));

        root.findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String videoId = getVideoId(getItem(position).getString("VideoLink"));
                Intent tostart = new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_LINK + videoId));

                mActivity.startActivity(Intent.createChooser(tostart, "Complete action using"));
            }
        });

        root.findViewById(R.id.listen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getItem(position).getString("AudioLink")));
                intent.setType("audio/*");

                mActivity.startActivity(intent);
            }
        });
    }

    private String getVideoId(String videoLink) {
        return videoLink.substring(videoLink.lastIndexOf("/") + 1);
    }
}
