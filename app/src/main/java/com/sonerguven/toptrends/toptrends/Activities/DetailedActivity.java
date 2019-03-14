package com.sonerguven.toptrends.toptrends.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sonerguven.toptrends.toptrends.R;
import com.sonerguven.toptrends.toptrends.Adaptors.DetayListeAdaptor;
import com.sonerguven.toptrends.toptrends.Constants.Constants;
import com.sonerguven.toptrends.toptrends.Helpers.ItemClickSupport;
import com.sonerguven.toptrends.toptrends.Properties.ObjTrends;

import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity {
    private static final String TAG = "DemoActivity";
    private List<ObjTrends.Articles> objArticles;
    private String articleTitle = "";
    private RecyclerView mRecyclerView;
    private DetayListeAdaptor adapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        AdView mAdView = (AdView) findViewById(R.id.adViewDetail);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3498db")));

        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#3498db"));
            window.setTitleColor(Color.parseColor("#FFFFFF"));
        }
        Intent intArticles = getIntent();
        Bundle bundle = intArticles.getExtras();
        if (bundle != null) {
            objArticles = (ArrayList<ObjTrends.Articles>) intArticles.getExtras().getSerializable(Constants.ARTICLES);
            articleTitle = intArticles.getStringExtra(Constants.ARTICLES_TITLE);
            //Toast.makeText(this, Html.fromHtml(articleTitle), Toast.LENGTH_LONG).show();
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_detay);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetayListeAdaptor(DetailedActivity.this, objArticles);
        mRecyclerView.setAdapter(adapter);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (adapter.getItemCount() > 0) {
                    Intent intent = new Intent(DetailedActivity.this, ShowNewsActivity.class);
                    intent.putExtra(Constants.ARTICLE_URL, objArticles.get(position).getUrl());
                    startActivity(intent);
                }
            }
        });
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
}
