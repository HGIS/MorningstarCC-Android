package org.morningstarcc.morningstarapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.viewholders.StudyHolder;

import java.util.Date;

import static org.morningstarcc.morningstarapp.libs.DateUtils.getDate;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getDayString;
import static org.morningstarcc.morningstarapp.libs.DateUtils.getMonthString;
import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyAdapter extends DatabaseItemAdapter<StudyHolder> {

    private Activity mActivity;

    private static String VIDEO_LINK = "vnd.youtube://";
    private static final String IMAGE_THUMBNAIL = "http://img.youtube.com/vi/%s/1.jpg";

    public StudyAdapter(Activity activity, int row_layout, Bundle[] data, Class<? extends Activity> nextActivity) {
        super(activity, row_layout, data, nextActivity);
        this.mActivity = activity;
    }

    @Override
    protected void setupView(StudyHolder viewHolder, final int position) {
        Date studyDate = getDate(data[position].getString("StudyDate"));

        viewHolder.title.setText(data[position].getString("title"));
        viewHolder.month.setText(getMonthString(studyDate));
        viewHolder.day.setText(getDayString(studyDate));

        viewHolder.root.setClickable(false);
        viewHolder.root.findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String videoId = getVideoId(data[position].getString("VideoLink"));
                Intent toStart = new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_LINK + videoId));

                mActivity.startActivity(toStart);
            }
        });

        viewHolder.root.findViewById(R.id.listen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(data[position].getString("AudioLink")), "audio/*");

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

    @Override
    protected StudyHolder getViewHolder(View view) {
        return new StudyHolder(view);
    }
}
