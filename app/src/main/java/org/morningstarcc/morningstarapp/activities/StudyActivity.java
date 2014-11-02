package org.morningstarcc.morningstarapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import org.morningstarcc.morningstarapp.R;

/**
 * Created by Kyle on 10/30/2014.
 */
public class StudyActivity extends DetailsActivity {
    private static String VIDEO_LINK = "vnd.youtube://";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(intent.getStringExtra("title"));
        setContentView(R.layout.activity_study);
    }

    public void onClick(View view) {
        String videoId = getVideoId(intent.getStringExtra("VideoLink"));
        Intent tostart = new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_LINK + videoId));
        
        startActivity(Intent.createChooser(tostart, "Complete action using"));
    }

    private String getVideoId(String videoLink) {
        return videoLink.substring(videoLink.lastIndexOf("/") + 1);
    }
}
