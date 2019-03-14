package com.sonerguven.toptrends.toptrends.Properties;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SonerGuven on 12/11/16.
 */

public class ObjTrends implements Serializable {
    //region Private Fields
    @SerializedName("storySummaries")
    private StorySummaries storySummaries;

    //endregion
    //region Constructors
    public ObjTrends(StorySummaries storySummaries) {
        this.storySummaries = storySummaries;
    }

    public ObjTrends() {
    }

    //endregion
    //region Getters
    public StorySummaries getStorySummaries() {
        if (storySummaries == null) return new StorySummaries();
        return storySummaries;
    }

    //endregion
    //region Setters
    public void setStorySummaries(StorySummaries storySummaries) {
        this.storySummaries = storySummaries;
    }

    //endregion

    public class StorySummaries implements Serializable {
        //region Private Fields
        @SerializedName("trendingStories")
        private List<TrendingStories> trendingStories;

        //endregion
        //region Constructors
        public StorySummaries() {
        }

        public StorySummaries(List<TrendingStories> trendingStories) {
            this.trendingStories = trendingStories;
        }

        //endregion
        //region Getters
        public List<TrendingStories> getTrendingStories() {
            if (trendingStories == null) return new ArrayList<TrendingStories>();
            return trendingStories;
        }

        //endregion
        //region Setters
        public void setTrendingStories(List<TrendingStories> trendingStories) {
            this.trendingStories = trendingStories;
        }
        //endregion
    }

    public class TrendingStories implements Serializable {
        //region Private Fields
        @SerializedName("image")
        private Image image;
        @SerializedName("articles")
        private List<Articles> articles;
        @SerializedName("id")
        private String id;
        @SerializedName("title")
        private String title;
        @SerializedName("entityNames")
        private String[] entityNames;

        //endregion
        //region Getters
        public Image getImage() {
            return image;
        }

        public List<Articles> getArticles() {
            if (articles == null) return new ArrayList<Articles>();
            return articles;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String[] getEntityNames() {
            return entityNames;
        }

        //endregion
        //region Setters
        public void setImage(Image image) {
            this.image = image;
        }

        public void setArticles(List<Articles> articles) {
            this.articles = articles;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setEntityNames(String[] entityNames) {
            this.entityNames = entityNames;
        }
        //endregion
    }

    public class Image implements Serializable {
        //region Private Fields
        @SerializedName("newsUrl")
        private String newsUrl;
        @SerializedName("source")
        private String source;
        @SerializedName("imgUrl")
        private String imgUrl;

        //endregion
        //region Getters
        public String getNewsUrl() {
            return newsUrl;
        }

        public String getSource() {
            return source;
        }

        public String getImgUrl() {
            if (imgUrl != null && !imgUrl.startsWith("http:")) imgUrl = "http:" + imgUrl;
            return imgUrl;
        }

        //endregion
        //region Setters
        public void setNewsUrl(String newsUrl) {
            this.newsUrl = newsUrl;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
        //endregion
    }

    public class Articles implements Serializable {
        //region Private Fields
        @SerializedName("articleTitle")
        private String articleTitle;
        @SerializedName("url")
        private String url;
        @SerializedName("source")
        private String source;
        @SerializedName("time")
        private String time;

        //endregion
        //region Getters
        public String getArticleTitle() {
            return articleTitle;
        }

        public String getUrl() {
            return url;
        }

        public String getSource() {
            return source;
        }

        public String getTime() {
            return time;
        }

        //endregion
        //region Setters
        public void setArticleTitle(String articleTitle) {
            this.articleTitle = articleTitle;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public void setTime(String time) {
            this.time = time;
        }
        //endregion
    }
}



