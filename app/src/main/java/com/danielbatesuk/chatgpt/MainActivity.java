package com.danielbatesuk.chatgpt;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("https://chat.openai.com/chat");
        webView.setWebViewClient(new WebViewClient());

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.refreshWeb);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_color_700);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(webView.getUrl() == "https://chat.openai.com/auth/login") {
                    webView.loadUrl("https://chat.openai.com/chat");
                } else {
                    webView.reload();
                }
                webView.reload();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}