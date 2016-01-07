package org.morningstarcc.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.android.volley.Response;

import org.morningstarcc.app.R;
import org.morningstarcc.app.http.DataService;

/**
 * Created by Kyle on 10/26/2014.
 * History:
 * 11/12/2015 - Juan Manuel Gomez - Added progress dialog
 */
public class SplashActivity extends Activity {
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
        DataService.updateAll(this, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
