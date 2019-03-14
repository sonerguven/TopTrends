package com.sonerguven.toptrends.toptrends.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sonerguven.toptrends.toptrends.Activities.DetailedActivity;
import com.sonerguven.toptrends.toptrends.R;
import com.sonerguven.toptrends.toptrends.Constants.Constants;
import com.sonerguven.toptrends.toptrends.Properties.ObjTrends;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by SonerGuven on 20/11/2016.
 */

public class AnaListeAdaptor extends RecyclerView.Adapter<AnaListeAdaptor.CustomViewHolder> {

    private List<ObjTrends.TrendingStories> objTrends;
    private Context mContext;

    public AnaListeAdaptor(Context context, List<ObjTrends.TrendingStories> objTrends) {
        this.objTrends = objTrends;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ana_liste_satir, viewGroup, false);
        AnaListeAdaptor.CustomViewHolder viewHolder = new AnaListeAdaptor.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AnaListeAdaptor.CustomViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        ObjTrends.TrendingStories trendingStories = objTrends.get(position);


        if (trendingStories.getImage() != null && trendingStories.getImage().getImgUrl() != null) {
            String imgUrl = trendingStories.getImage().getImgUrl();
            final List<ObjTrends.Articles> articles = objTrends.get(position).getArticles();
            final String title = objTrends.get(position).getTitle();
            Picasso.with(mContext)
                    .load(imgUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.imageView);
            holder.txtKaynak.setText(Html.fromHtml(trendingStories.getImage().getSource()));
            holder.rlvDigerKaynaklar.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailedActivity.class);
                    intent.putExtra(Constants.ARTICLES, (Serializable) articles);
                    intent.putExtra(Constants.ARTICLES_TITLE, title);
                    mContext.startActivity(intent);
                }
            });
        } else {
            /*holder.imageView.setVisibility(View.INVISIBLE);
            holder.LnrResim.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            lp.weight = 0;
            holder.LnrBaslik.setLayoutParams(lp);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            lp1.weight = 1;
            holder.LnrBaslik.setLayoutParams(lp1);*/
            holder.rlvDigerKaynaklar.setVisibility(View.INVISIBLE);
        }
        holder.txtDigerHaber.setText(getStringResourceByName("RelatedNews"));
        holder.textView.setText(position+1 + ". " + Html.fromHtml(trendingStories.getTitle()));
        holder.txtDigerHaber.setPaintFlags(holder.txtDigerHaber.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        /*new DownloadImageTask((ImageView) holder.imageView)
                .execute(trendingStories.getImage().getImgUrl());*/
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public int getItemCount() {
        return (null != objTrends ? objTrends.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected LinearLayout LnrBaslik;
        protected LinearLayout LnrResim;
        protected TextView txtKaynak;
        protected TextView txtDigerHaber;
        protected RelativeLayout rlvDigerKaynaklar;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
            this.LnrBaslik = (LinearLayout) view.findViewById(R.id.LnrBaslik);
            this.LnrResim = (LinearLayout) view.findViewById(R.id.LnrResim);
            this.txtKaynak = (TextView) view.findViewById(R.id.kaynak);
            this.txtDigerHaber = (TextView) view.findViewById(R.id.digerKaynak);
            this.rlvDigerKaynaklar = (RelativeLayout) view.findViewById(R.id.RlvDigerKaynaklar);
        }
    }

    private String getStringResourceByName(String aString) {
        String packageName = mContext.getPackageName();
        int resId = mContext.getResources().getIdentifier(aString, "string", packageName);
        return mContext.getString(resId);
    }
}