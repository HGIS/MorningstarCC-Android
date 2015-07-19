package org.morningstarcc.morningstarapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.morningstarcc.morningstarapp.R;
import org.morningstarcc.morningstarapp.libs.IntentUtils;

import static org.morningstarcc.morningstarapp.libs.ViewConstructorUtils.setText;

public class StudyActivity extends DetailsActivity {

    private static String VIDEO_LINK = "vnd.youtube://";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        getSupportActionBar().setTitle(intent.getStringExtra("title"));

        setText(this, R.id.speaker, intent.getStringExtra("Speaker"));
        setText(this, R.id.date, intent.getStringExtra("StudyDate"));
        setText(this, R.id.scripture, intent.getStringExtra("Scripture"));
        setText(this, R.id.description, intent.getStringExtra("Description"));
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
        Intent shareIntent = IntentUtils.shareIntent(String.format(
                "Morningstar's %s taught about %s from %s on %s. Check it out at: %s, or listen to it at: %s",
                intent.getStringExtra("Speaker"), intent.getStringExtra("title").toLowerCase(),
                intent.getStringExtra("Scripture"), intent.getStringExtra("StudyDate"),
                intent.getStringExtra("VideoLink"), intent.getStringExtra("AudioLink")));

        startActivity(shareIntent);
    }

    public void onListen() {
        Intent listenIntent = new Intent(Intent.ACTION_VIEW);
        listenIntent.setDataAndType(Uri.parse(intent.getStringExtra("AudioLink")), "audio/*");

        startActivity(listenIntent);
    }

    public void onPlay(View view) {
        String videoId = getVideoId(intent.getStringExtra("VideoLink"));
        Intent toStart = new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_LINK + videoId));

        startActivity(toStart);
    }

    private String getVideoId(String videoLink) {
        return videoLink.substring(videoLink.lastIndexOf("/") + 1);
    }
}
