package org.morningstarcc.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.morningstarcc.app.R;
import org.morningstarcc.app.data.Event;
import org.morningstarcc.app.http.DataService;
import org.morningstarcc.app.http.RssRequest;
import org.morningstarcc.app.http.XmlArray;
import org.morningstarcc.app.libs.DatabaseUpdater;
import org.morningstarcc.app.libs.DownloadUrlContentTask;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Kyle on 10/26/2014.
 */
public class SplashActivity extends Activity {

    private static final int DELAY = 1500; // ms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new Slide(Gravity.TOP));
            getWindow().setEnterTransition(new Explode());
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("SplashActivity", "Updater launching...");
        updateFeeds();

        // launch the next activity after a delay
        startAfter(new Intent(SplashActivity.this, MainActivity.class), DELAY);
    }

    public void updateFeeds() {
        DataService.enqueue(new RssRequest("http://www.morningstarcc.org/MCCEventsRSS.aspx", new Response.Listener<XmlArray>() {
            @Override
            public void onResponse(XmlArray response) {
                List<Event> events = response.convert(Event.class);
                Log.d("Volley", "First volley request: " + events);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley", "Something something error: " + error);
            }
        }));

        if (DownloadUrlContentTask.hasInternetAccess(this)) {
            new DatabaseUpdater(this).update();
        }
    }

    private void startAfter(final Intent toStart, final int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(toStart);
                finish();
            }
        }, delay);
    }
}