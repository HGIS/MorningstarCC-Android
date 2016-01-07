package org.morningstarcc.app.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.morningstarcc.app.R;

/**
 * History:
 * 11/12/2015 - Juan Manuel Gomez - Created
 */
public class PDFActivity extends ActionBarActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        setTitle(getString(R.string.home_bulletin));

        webView = (WebView) findViewById(R.id.pdfview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                settings.setAllowUniversalAccessFromFileURLs(true);
            }

            settings.setBuiltInZoomControls(true);
            webView.setWebChromeClient(new WebChromeClient());
            webView.loadUrl("file:///android_asset/pdfviewer/index.html");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.clearCache(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.loadUrl("javascript:window.location.reload( true )");
    }
}
