package com.sonerguven.toptrends.toptrends.Helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sonerguven.toptrends.toptrends.Adaptors.AnaListeAdaptor;
import com.sonerguven.toptrends.toptrends.Properties.ObjTrends;
import com.sonerguven.toptrends.toptrends.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by SonerGuven on 20/12/2016.
 */

public class Helpers {
    public static class DownloadTask extends AsyncTask<String, Void, Integer> {
        private static final String TAG = "TopTrends";
        private ProgressBar progressBar;
        private SGSession.Ref<AnaListeAdaptor> adapter;
        private RecyclerView mRecyclerView;
        private SGSession.Ref<ObjTrends> objTrends;
        private Context mContext;
        private Boolean getFromCache = false;
        private String link;
        private IAsyncProcessFinished delegate = null;

        public DownloadTask(IAsyncProcessFinished delegate)
        {
            this.delegate = delegate;
        }

        public void executeLink(String link,
                                ProgressBar progressBar,
                                SGSession.Ref<AnaListeAdaptor> adapter,
                                RecyclerView mRecyclerView,
                                SGSession.Ref<ObjTrends> objTrends,
                                Context mContext,
                                Boolean getFromCache) {
            this.link = link;
            this.progressBar = progressBar;
            this.adapter = adapter;
            this.mRecyclerView = mRecyclerView;
            this.objTrends = objTrends;
            this.mContext = mContext;
            this.getFromCache = getFromCache;
            super.execute(link);
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            if (getFromCache && SGSession.Get(link) != null) {
                objTrends.set((ObjTrends) SGSession.Get(link));
                result = 1;
            } else {
                HttpURLConnection urlConnection;
                try {
                    URL url = new URL(params[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    int statusCode = urlConnection.getResponseCode();

                    // 200 represents HTTP OK
                    if (statusCode == 200) {
                        BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            response.append(line);
                        }
                        parseResult(response.toString());
                        result = 1; // Successful
                    }
                    else
                    {
                        result = 0; //"Failed to fetch data!";
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.getLocalizedMessage());
                }
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter.set(new AnaListeAdaptor(mContext, objTrends.get().getStorySummaries().getTrendingStories()));
                mRecyclerView.setAdapter(adapter.get());

            }
            delegate.processFinish(result);
        }
        public interface IAsyncProcessFinished {
            void processFinish(Integer output);
        }

        private void parseResult(String result) throws ClassNotFoundException {
            Gson gson = new Gson();
            int leng = result.length();
            if (leng > 4) {
                result = result.substring(4, leng);
            }
            objTrends.set(gson.fromJson(result, ObjTrends.class));
            SGSession.Add(link, objTrends.get());
        }
    }

    public static List<String> GetClassFields(Class cls) {
        List<String> lstFields = new ArrayList<String>();
        for (Field f : cls.getDeclaredFields()) {
            lstFields.add(f.getName());
        }
        return lstFields;
    }

    public static List<String> GetEnumFields(Class cls) {
        List<String> lstFieldsFirst = new ArrayList<String>(EnumSet.allOf(cls));
        List<String> lstFieldsLast = new ArrayList<>();
        for (Iterator<String> i = lstFieldsFirst.iterator(); i.hasNext(); ) {
            Object item = i.next();
            lstFieldsLast.add(item.toString());
        }
        return lstFieldsLast;
    }

    public static Boolean IsNullOrEmpty(String string) {
        if (string != null && !string.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
