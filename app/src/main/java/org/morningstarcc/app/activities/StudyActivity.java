package org.morningstarcc.app.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;

import org.morningstarcc.app.R;
import org.morningstarcc.app.data.Study;
import org.morningstarcc.app.libs.IntentUtils;

import static org.morningstarcc.app.libs.ViewConstructorUtils.setText;

/**
 * History:
 * 11/12/2015 - Juan Manuel Gomez - Added youtube crash
 */
public class StudyActivity extends DetailsActivity<Study> {

    private static final String VIDEO_LINK = "vnd.youtube://";
    private static final String HTTP_LINK = "http://youtu.be/";

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

        if (item.VideoLink.toLowerCase().contains("livestream")){
            //Livestream
            if (URLUtil.isValidUrl(item.VideoLink)){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.VideoLink)));
            }else{
                //Try to fix the url
                String urlFixed = String.format("%s%s", "http://", item.VideoLink);
                if (URLUtil.isValidUrl(urlFixed)){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlFixed)));
                }else{
                    new AlertDialog.Builder(StudyActivity.this)
                            .setTitle("")
                            .setMessage("Invalid URL")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create().show();
                }
            }

        }else{
            try{
                startActivity(toStart);
            }catch(Exception e){
                e.printStackTrace();
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(HTTP_LINK + videoId)));
            }
        }

    }

    private String getVideoId(String videoLink) {
        return videoLink.substring(videoLink.lastIndexOf("/") + 1);
    }
}
