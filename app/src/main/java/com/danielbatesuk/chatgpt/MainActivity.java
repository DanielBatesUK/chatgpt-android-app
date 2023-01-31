package com.danielbatesuk.chatgpt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;


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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        WebView webView = findViewById(R.id.web);

        if(item.getItemId() == R.id.menu_refresh) {
            webView.reload();
        }

        if(item.getItemId() == R.id.menu_github) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/DanielBatesUK/chatgpt-android-app"));
            startActivity(intent);
        }

        return true;
    }
}