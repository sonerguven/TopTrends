package com.sonerguven.toptrends.toptrends.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.support.v7.widget.ShareActionProvider;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.MobileAds;
import com.sonerguven.toptrends.toptrends.R;
import com.sonerguven.toptrends.toptrends.Constants.Constants;

import java.util.Locale;

public class ShowNewsActivity extends AppCompatActivity {
    private String articleUrl = "";
    private WebView mWebView;
    private ProgressBar progressBar;
    private ShareActionProvider mShareActionProvider;
    private SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#3498db"));
            window.setTitleColor(Color.parseColor("#FFFFFF"));
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3498db")));
        progressBar = (ProgressBar)findViewById(R.id.progress_bar_news);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        Intent intArticles = getIntent();
        Bundle bundle = intArticles.getExtras();
        if(bundle!=null)
        {
            WebView webview = (WebView)findViewById(R.id.webView);
            //getWindow().requestFeature(Window.FEATURE_PROGRESS);
            webview.getSettings().setJavaScriptEnabled(false);

            final Activity activity = this;
            webview.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    // Activities and WebViews measure progress with different scales.
                    // The progress meter will automatically disappear when we reach 100%
                    activity.setProgress(progress * 1000);
                    if(progress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
                        progressBar.setVisibility(ProgressBar.VISIBLE);
                    }

                    progressBar.setProgress(progress);
                    if(progress == 100) {
                        progressBar.setVisibility(ProgressBar.GONE);
                    }
                }
            });
            webview.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(activity, "Error" + description, Toast.LENGTH_SHORT).show();
                }
            });
            WebSettings settings = webview.getSettings();
            settings.setBuiltInZoomControls(true);
            settings.setMinimumFontSize(18);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            settings.setDisplayZoomControls(false);
            articleUrl = intArticles.getStringExtra(Constants.ARTICLE_URL);
            sharedpreferences = getPreferences(MODE_PRIVATE);
            String countryPref = sharedpreferences.getString(Constants.MYPREFCOUNTRY,null);
            Locale current = getResources().getConfiguration().locale;
            Answers.getInstance().logContentView(new ContentViewEvent()
                    .putContentId("1")
                    .putCustomAttribute("Language", current.getLanguage())
                    .putCustomAttribute("Country", countryPref)
                    .putCustomAttribute("ArticleName", articleUrl));
            webview.loadUrl(articleUrl);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_socialsharing, menu);
        MenuItem item = menu.findItem(R.id.menu_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(doShare());

        return true;
    }
    public Intent doShare() {
        // populate the share intent with data
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, articleUrl);
        return intent;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
