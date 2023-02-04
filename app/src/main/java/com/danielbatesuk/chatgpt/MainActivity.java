package com.danielbatesuk.chatgpt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    String githubURL = "https://github.com/DanielBatesUK/chatgpt-android-app";
    String chatgptUrl = "https://chat.openai.com/chat";
    String chatgptAuthUrl = "https://chat.openai.com/auth/login";
    String openAIUrl = "https://openai.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressBar progressBar = findViewById(R.id.prog);
        WebView webView = findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(chatgptUrl);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else{
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuCompat.setGroupDividerEnabled(menu, true);
        String versionName = "v" + BuildConfig.VERSION_NAME;
        MenuItem menuItem = menu.findItem(R.id.menu_version);
        menuItem.setTitle(versionName);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        WebView webView = findViewById(R.id.web);

        // Refresh
        if(item.getItemId() == R.id.menu_refresh || item.getItemId() == R.id.menu_refresh2) {
            if(!Objects.equals(webView.getUrl(), chatgptAuthUrl)) {
                webView.reload();
            } else {
                webView.loadUrl(chatgptUrl);
            }
        }

        // External: OpenAI
        if(item.getItemId() == R.id.menu_openai) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(openAIUrl));
            startActivity(intent);
        }

        // External: GitHub
        if(item.getItemId() == R.id.menu_github) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubURL));
            startActivity(intent);
        }

        return true;
    }
}