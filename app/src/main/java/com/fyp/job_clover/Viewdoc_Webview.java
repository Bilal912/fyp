package com.fyp.job_clover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import static com.fyp.job_clover.Seeker.seeker_login.MY_PREFS_NAME;

public class Viewdoc_Webview extends AppCompatActivity {
    WebView webView;
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdoc__webview);

        String url = null;

        webView = (WebView) findViewById(R.id.webview_id);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {

            }

        });

    }
    public void onBackPressed(){
        finish();
    }

}