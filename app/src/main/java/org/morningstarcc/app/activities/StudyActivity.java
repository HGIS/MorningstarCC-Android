package org.morningstarcc.app.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.morningstarcc.app.R;
import org.morningstarcc.app.data.Study;
import org.morningstarcc.app.libs.IntentUtils;

import static org.morningstarcc.app.libs.ViewConstructorUtils.setText;

public class StudyActivity extends DetailsActivity<Study> {

    private static String VIDEO_LINK = "vnd.youtube://";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        getSupportActionBar().setTitle(item.title);

        setText(this, R.id.speaker, item.Speaker);
        setText(this, R.id.date, item.StudyDate);
        setText(this, R.id.scripture, item.Scripture);
        setText(this, R.id.description, item.Description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_study_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                onShare();
                return true;
            case R.id.action_listen:
                onListen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onShare() {
        Intent shareIntent = IntentUtils.shareIntent(getString(R.string.share_study_format,
                item.Speaker, item.title.toLowerCase(), item.Scripture, item.StudyDate,
                item.VideoLink, item.AudioLink));

        startActivity(shareIntent);
    }

    public void onListen() {
        Intent listenIntent = new Intent(Intent.ACTION_VIEW);
        listenIntent.setDataAndType(Uri.parse(item.AudioLink), "audio/*");

        startActivity(listenIntent);
    }

    public void onPlay(View view) {
        String videoId = getVideoId(item.VideoLink);
        Intent toStart = new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_LINK + videoId));

        startActivity(toStart);
    }

    private String getVideoId(String videoLink) {
        return videoLink.substring(videoLink.lastIndexOf("/") + 1);
    }
}
