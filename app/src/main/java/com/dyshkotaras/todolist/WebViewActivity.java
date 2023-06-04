package com.dyshkotaras.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.HttpURLConnection;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    public static final String URL = "https://ohmytraff.space/api";
    private SharedPreferencesManager sharedPreferencesManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_main);
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        String lastWebsite = sharedPreferencesManager.getLastWebsite();
        if (lastWebsite != null) {
            webView.loadUrl(lastWebsite);
        } else {
            webView.loadUrl(URL);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                loadMainActivity();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                sharedPreferencesManager.saveLastWebsite(url);
                if (String.valueOf(view.getTitle()).equals(URL)){
                    loadMainActivity();
                }
            }


        });

    }

    private void loadMainActivity() {
        Intent intent = MainActivity.newIntent(WebViewActivity.this);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}