package com.dyshkotaras.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.dyshkotaras.todolist.main_app.MainActivity;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private WebViewModel viewModel;
    public static final String URL = "https://ohmytraff.space/api";
    private SharedPreferencesManager sharedPreferencesManager;
    public static final String TAG = "WebViewActivity1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_main);
        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        viewModel = new ViewModelProvider(this).get(WebViewModel.class);
        viewModel.getIsInternet().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isInternet) {
                if (!isInternet) {
                    Log.d(TAG, String.valueOf("no Internet"));
                    loadMainActivity();
                } else {
                    viewModel.executeApiRequest();
                }
            }
        });
        viewModel.getIsError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean is404) {
                Log.d(TAG, "is404 - " + is404);
                if (is404) {
                    loadMainActivity();
                }
            }
        });

        viewModel.isConnectedToInternet(this);

        sharedPreferencesManager = new SharedPreferencesManager(this);
        String lastWebsite = sharedPreferencesManager.getLastWebsite();
        if (lastWebsite != null) {
            Log.d(TAG, "lastWebsite - " + lastWebsite);
            webView.loadUrl(lastWebsite);

        } else {
            Log.d(TAG, "url");
            webView.loadUrl(URL);
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                sharedPreferencesManager.saveLastWebsite(url);
            }
        });

    }

    private void loadMainActivity() {
        Intent intent = MainActivity.newIntent(WebViewActivity.this);
        startActivity(intent);
    }
}