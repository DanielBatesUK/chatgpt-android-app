package com.danielbatesuk.chatgpt;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
    String chatgptUrl = "https://chat.openai.com";
    String chatgptAuthUrl = "https://chat.openai.com/auth/login";
    String openAIUrl = "https://openai.com/";

    WebView webView;
    ProgressBar progressBar;
    MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar = findViewById(R.id.prog);
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    invalidateOptionsMenu();
                    progressBar.setVisibility(View.GONE);
                } else{
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        webView.loadUrl(chatgptUrl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuCompat.setGroupDividerEnabled(menu, true);
        String versionName = "v" + BuildConfig.VERSION_NAME;
        menuItem = menu.findItem(R.id.menu_version);
        menuItem.setTitle(versionName);

        if(!webView.getUrl().contains(chatgptUrl)  && !webView.getUrl().contains(chatgptAuthUrl)) {
            // Back
            menuItem = menu.findItem(R.id.menu_refresh);
            menuItem.setTitle(R.string.menu_back);
            menuItem.setIcon(R.drawable.baseline_arrow_back_24);
            menuItem = menu.findItem(R.id.menu_refresh2);
            menuItem.setTitle(R.string.menu_back);
        } else {
            // Refresh
            menuItem = menu.findItem(R.id.menu_refresh);
            menuItem.setTitle(R.string.menu_refresh);
            menuItem.setIcon(R.drawable.baseline_refresh_24);
            menuItem = menu.findItem(R.id.menu_refresh2);
            menuItem.setTitle(R.string.menu_refresh);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        webView = findViewById(R.id.web);
        // Refresh/Back
        if(item.getItemId() == R.id.menu_refresh || item.getItemId() == R.id.menu_refresh2) {
            // Back
            if(String.valueOf(item.getTitle()).equals(getResources().getString(R.string.menu_back))) {
                webView.goBack();
                return false;
            }
            // Refresh
            if(Objects.equals(webView.getUrl(), chatgptUrl)) {
                webView.reload();
            } else {
                webView.loadUrl(chatgptUrl);
            }
            return true;
        }
        // External website: OpenAI
        if(item.getItemId() == R.id.menu_openai) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(openAIUrl));
            startActivity(intent);
            return true;
        }
        // External website: GitHub
        if(item.getItemId() == R.id.menu_github) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubURL));
            startActivity(intent);
            return true;
        }
        return true;
    }
}