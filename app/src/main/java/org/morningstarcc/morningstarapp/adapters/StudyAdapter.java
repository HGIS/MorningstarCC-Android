package org.morningstarcc.morningstarapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.libs.DateUtils;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.getDate;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getDayString;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getMonthString;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyAdapter extends DatabaseItemAdapter {

    private Activity mActivity;

    private static String VIDEO_LINK = "vnd.youtube://";
    private static final String IMAGE_THUMBNAIL = "http://img.youtube.com/vi/%s/1.jpg";

    public StudyAdapter(Activity activity, Bundle[] data) {
        super(activity, R.layout.study_list_row_2, data);
        this.mActivity = activity;
    }

    @Override
    protected void setupView(View root, final int position) {
        Date studyDate = getDate(data[position].getString("StudyDate"));

        setText(root, R.id.title, data[position].getString("title"));
        setText(root, R.id.month, getMonthString(studyDate));
        setText(root, R.id.day, getDayString(studyDate));

        root.setClickable(false);
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
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(getItem(position).getString("AudioLink")), "audio/*");

                mActivity.startActivity(intent);
            }
        });
    }

    private String getImageThumbnailLink(int position) {
        return String.format(IMAGE_THUMBNAIL, getVideoId(data[position].getString("VideoLink")));
    }

    private String getVideoId(String videoLink) {
        return videoLink.substring(videoLink.lastIndexOf("/") + 1);
    }
}
