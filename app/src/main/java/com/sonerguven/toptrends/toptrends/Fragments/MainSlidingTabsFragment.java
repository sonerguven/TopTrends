package com.sonerguven.toptrends.toptrends.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sonerguven.toptrends.toptrends.Activities.DetailedActivity;
import com.sonerguven.toptrends.toptrends.Activities.ShowNewsActivity;
import com.sonerguven.toptrends.toptrends.R;
import com.sonerguven.toptrends.toptrends.Adaptors.AnaListeAdaptor;
import com.sonerguven.toptrends.toptrends.Constants.Constants;
import com.sonerguven.toptrends.toptrends.Enums.MenuItems;
import com.sonerguven.toptrends.toptrends.Helpers.Helpers;
import com.sonerguven.toptrends.toptrends.Helpers.ItemClickSupport;
import com.sonerguven.toptrends.toptrends.Helpers.SGSession;
import com.sonerguven.toptrends.toptrends.Properties.ObjTrends;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainSlidingTabsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainSlidingTabsFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    private static final String TAG = "TopTrends";
    private ObjTrends objTrends;
    private RecyclerView mRecyclerView;
    private AnaListeAdaptor adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    SGSession.Ref<AnaListeAdaptor> anaListeAdaptorRef = new SGSession.Ref<AnaListeAdaptor>();
    SGSession.Ref<ObjTrends> objTrendsAdaptorRef = new SGSession.Ref<ObjTrends>();
    private SharedPreferences sharedpreferences;
    private ImageView imgNoData;
    private TextView txtNoNews;

    private int position;

    public static MainSlidingTabsFragment newInstance(int position) {
        MainSlidingTabsFragment fragment = new MainSlidingTabsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_mainslidingtabs, container, false);
        /*MobileAds.initialize(rootView.getContext(), "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) rootView.findViewById(R.id.adViewFragment);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_ana);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        imgNoData = (ImageView) rootView.findViewById(R.id.imgNoNews);
        txtNoNews = (TextView) rootView.findViewById(R.id.txtNoNews);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        ArrangeAndFetchData(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrangeAndFetchData(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (anaListeAdaptorRef.get().getItemCount() > 0) {
                    if(objTrendsAdaptorRef.get().getStorySummaries().getTrendingStories().get(position).getImage().getNewsUrl() != null)
                    {
                        Intent intent = new Intent(getActivity(), ShowNewsActivity.class);
                        intent.putExtra(Constants.ARTICLE_URL, objTrendsAdaptorRef.get().getStorySummaries().getTrendingStories().get(position).getImage().getNewsUrl());
                        startActivity(intent);
                    }
                    else
                    {
                        final List<ObjTrends.Articles> articles = objTrendsAdaptorRef.get().getStorySummaries().getTrendingStories().get(position).getArticles();
                        final String title = objTrendsAdaptorRef.get().getStorySummaries().getTrendingStories().get(position).getTitle();
                        Intent intent = new Intent(getActivity(), DetailedActivity.class);
                        intent.putExtra(Constants.ARTICLES, (Serializable) articles);
                        intent.putExtra(Constants.ARTICLES_TITLE, title);
                        startActivity(intent);
                    }
                }
            }
        });
        return rootView;
    }
    private String GetMenuLinkByPosition(String link, String countryPref, String languagePref)
    {
        if(position == MenuItems.Business.ordinal())
        {
            link = Constants.SERVICE_URL.replace("{region}", countryPref).replace("{language}", languagePref).replace("{category}", "b");
        }
        else if(position == MenuItems.Entertainment.ordinal()){
            link = Constants.SERVICE_URL.replace("{region}", countryPref).replace("{language}", languagePref).replace("{category}", "e");
        }
        else if(position == MenuItems.Health.ordinal()){
            link = Constants.SERVICE_URL.replace("{region}", countryPref).replace("{language}", languagePref).replace("{category}", "m");
        }
        else if(position == MenuItems.SciTech.ordinal()){
            link = Constants.SERVICE_URL.replace("{region}", countryPref).replace("{language}", languagePref).replace("{category}", "t");
        }
        else if(position == MenuItems.Sports.ordinal()){
            link = Constants.SERVICE_URL.replace("{region}", countryPref).replace("{language}", languagePref).replace("{category}", "s");
        }
        else if(position == MenuItems.TopStories.ordinal()){
            link = Constants.SERVICE_URL.replace("{region}", countryPref).replace("{language}", languagePref).replace("{category}", "h");
        }
        return link;
    }
    private void ArrangeAndFetchData(Boolean getFromCache)
    {
        sharedpreferences = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        Locale current = getResources().getConfiguration().locale;
        String countryPref = sharedpreferences.getString(Constants.MYPREFCOUNTRY,null);
        String link = Constants.SERVICE_URL.replace("{region}", countryPref).replace("{language}", current.getLanguage()).replace("{category}", "all");
        link = GetMenuLinkByPosition(link,countryPref, current.getLanguage());
        new Helpers.DownloadTask(
                new Helpers.DownloadTask.IAsyncProcessFinished(){
                    @Override
                    public void processFinish(Integer result) {
                        if (result != 1) {
                            imgNoData.setVisibility(View.VISIBLE);
                            txtNoNews.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            imgNoData.setVisibility(View.INVISIBLE);
                            txtNoNews.setVisibility(View.INVISIBLE);
                        }
                    }
                }
        ).executeLink(link, progressBar, anaListeAdaptorRef, mRecyclerView, objTrendsAdaptorRef, getActivity(), getFromCache);
    }
}
