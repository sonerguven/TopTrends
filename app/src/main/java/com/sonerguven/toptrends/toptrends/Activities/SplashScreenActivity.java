package com.sonerguven.toptrends.toptrends.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.sonerguven.toptrends.toptrends.Constants.Constants;
import com.sonerguven.toptrends.toptrends.Enums.TopicItems;
import com.sonerguven.toptrends.toptrends.Helpers.Helpers;
import com.sonerguven.toptrends.toptrends.R;

import io.fabric.sdk.android.Fabric;
import java.util.List;
import java.util.Locale;

public class SplashScreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    /*private SharedPreferences sharedpreferences;
    private String countryPref;
    private ProgressBar progressBar;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Fabric.isInitialized()) {
            Fabric.with(this, new Crashlytics());
        }
        setContentView(R.layout.activity_splash_screen);
        /*sharedpreferences = getPreferences(MODE_PRIVATE);
        countryPref = sharedpreferences.getString(Constants.MYPREFCOUNTRY, null);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar_splash);
        List<String> menuEnums = Helpers.GetEnumFields(TopicItems.class);
        for (final String item : menuEnums) {
            if (Helpers.IsNullOrEmpty(countryPref)) {
                Locale current = getResources().getConfiguration().locale;
                countryPref = current.getCountry();
            }
            new Helpers.DownloadTask().onInitialize(Constants.SERVICE_URL.replace("{region}", countryPref).replace("{language}", "en-US").replace("{category}", item.toString()),progressBar);
        }*/
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
