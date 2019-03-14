package com.sonerguven.toptrends.toptrends.Adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonerguven.toptrends.toptrends.R;
import com.sonerguven.toptrends.toptrends.Properties.ObjTrends;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by SonerGuven on 20/11/2016.
 */

public class DetayListeAdaptor extends RecyclerView.Adapter<DetayListeAdaptor.CustomViewHolder>  {
    private List<ObjTrends.Articles> objArticles;
    private Context mContext;

    public DetayListeAdaptor(Context context, List<ObjTrends.Articles> objArticles) {
        this.objArticles = objArticles;
        this.mContext = context;
    }

    @Override
    public DetayListeAdaptor.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detay_liste_satir,viewGroup,false);
        DetayListeAdaptor.CustomViewHolder viewHolder = new DetayListeAdaptor.CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetayListeAdaptor.CustomViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        ObjTrends.Articles articles = objArticles.get(position);
        //Setting text view title
        holder.txtTitle.setText(Html.fromHtml(articles.getArticleTitle()));
        holder.txtSource.setText(Html.fromHtml(articles.getSource()));
        holder.txtPublishedTime.setText(Html.fromHtml(articles.getTime()));
    }
    @Override
    public int getItemCount() {
        return (null != objArticles ? objArticles.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtTitle;
        protected TextView txtSource;
        protected TextView txtPublishedTime;

        public CustomViewHolder(View view) {
            super(view);
            this.txtTitle = (TextView) view.findViewById(R.id.detayTitle);
            this.txtSource = (TextView) view.findViewById(R.id.detaySource);
            this.txtPublishedTime = (TextView) view.findViewById(R.id.detayPublishedTime);
        }
    }
}
