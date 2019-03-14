package com.sonerguven.toptrends.toptrends.Activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mikelau.countrypickerx.Country;
import com.mikelau.countrypickerx.CountryPickerCallbacks;
import com.mikelau.countrypickerx.CountryPickerDialog;
import com.sonerguven.toptrends.toptrends.Constants.Constants;
import com.sonerguven.toptrends.toptrends.Fragments.MainSlidingTabsFragment;
import com.sonerguven.toptrends.toptrends.R;
import com.sonerguven.toptrends.toptrends.Enums.MenuItems;
import com.sonerguven.toptrends.toptrends.Helpers.Helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity {
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapterFrag;
    private String[] menuItemsBind;
    private CountryPickerDialog countryPicker;
    private SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) findViewById(R.id.adViewMain);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        sharedpreferences = getPreferences(MODE_PRIVATE);
        String countryPref = sharedpreferences.getString(Constants.MYPREFCOUNTRY,null);
        if(Helpers.IsNullOrEmpty(countryPref))
        {
            Locale current = getResources().getConfiguration().locale;
            SetCountryToPref(current.getCountry());
        }
        List<String> menuEnums = Helpers.GetEnumFields(MenuItems.class);
        List<String> menuDescriptions = new ArrayList<String>();
        for(String item: menuEnums) {
            menuDescriptions.add(getStringResourceByName(item.toString()));
        }
        menuItemsBind = new String[menuDescriptions.size()];
        menuItemsBind = menuDescriptions.toArray(menuItemsBind);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        adapterFrag = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapterFrag);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);
        int currentColor1 = 0xFFFFFFFF;
        tabs.setIndicatorColor(0xFFFFFFFF);
        tabs.setTextColor(currentColor1);
        tabs.setBackgroundColor(Color.parseColor("#3498db"));

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_nobackground);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3498db")));
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#3498db"));
            window.setTitleColor(Color.parseColor("#FFFFFF"));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.SelectCountry:
                int resourceId = getResources().getIdentifier("country_avail", "raw", getApplicationContext().getPackageName());
                String countryPref = sharedpreferences.getString(Constants.MYPREFCOUNTRY,null);
                countryPicker = new CountryPickerDialog(MainActivity.this, new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(Country country, int flagResId) {
                        SetCountryToPref(country.getIsoCode());
                        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
                        pager = (ViewPager) findViewById(R.id.pager);
                        adapterFrag = new MyPagerAdapter(getSupportFragmentManager());
                        pager.setAdapter(adapterFrag);
                        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                                .getDisplayMetrics());
                        pager.setPageMargin(pageMargin);
                        pager.setCurrentItem(tabs.getCurrentPosition());
                        tabs.setViewPager(pager);
                        countryPicker.dismiss();
                    }
                }, false, resourceId, countryPref);
                countryPicker.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public class MyPagerAdapter extends FragmentPagerAdapter {
        private final String[] TITLES = menuItemsBind;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return MainSlidingTabsFragment.newInstance(position);
        }

    }

    private String getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "string", packageName);
        return getString(resId);
    }
    private void SetCountryToPref(String countryCode)
    {
            SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
            editor.putString(Constants.MYPREFCOUNTRY, countryCode);
            editor.apply();
    }
}
