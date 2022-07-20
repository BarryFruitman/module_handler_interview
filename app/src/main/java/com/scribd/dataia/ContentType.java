package com.scribd.dataia;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;


public class ContentType implements Parcelable {

    /**
     * Hardcoding content type names is not best practice.
     * But due to the introduction of content tabs there are often times when we want to do mappings between
     * these tabs and unrelated pages
     */
    public static final String MIXED_CONTENT_TYPE_NAME = "mixed";
    public static final String BOOK_NAME = "book";
    public static final String AUDIOBOOK_NAME = "audiobook";
    public static final String DOCUMENT_NAME = "document";
    public static final String ARTICLE_NAME = "article";
    public static final String SHEET_MUSIC_NAME = "sheet_music";
    public static final String SUMMARY_NAME = "summary";
    public static final String PODCAST_EPISODE_NAME = "podcast";
    public static final String MAGAZINE_CONTENT_TYPE_NAME = "magazine"; // dummy ContentType for magazines tab
    public static final String NEWS_CONTENT_TYPE_NAME = "news";         // dummy ContentType for news tab

    /**
     * Human readable title of this content type, to be displayed to the user
     */
    private String title;

    /**
     * Human readable subtitle of this content type, to be displayed to the user
     */
    private String subtitle;

    /**
     * Machine readable string used to identify this content type in api requests
     */
    private String name;

    /**
     * The recommendation tracking analytics ID
     */
    private String analytics_id;

    public ContentType(String title, String subtitle, String name, String analytics_id){
        this.title = title;
        this.subtitle = subtitle;
        this.name = name;
        this.analytics_id = analytics_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnalyticsId() {
        return analytics_id;
    }

    public void setAnalyticsId(String analyticsId) { this.analytics_id = analyticsId; }

    public static ContentType articleType() {
        ContentType article = new ContentType();
        article.name = ARTICLE_NAME;
        return article;
    }

    // Parcelable stuff ----------------------------------------

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.subtitle);
        dest.writeString(this.name);
        dest.writeString(this.analytics_id);
    }

    public ContentType() {
    }

    protected ContentType(Parcel in) {
        this.title = in.readString();
        this.subtitle = in.readString();
        this.name = in.readString();
        this.analytics_id = in.readString();
    }

    public static final Creator<ContentType> CREATOR = new Creator<ContentType>() {
        @Override
        public ContentType createFromParcel(Parcel source) {
            return new ContentType(source);
        }

        @Override
        public ContentType[] newArray(int size) {
            return new ContentType[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof ContentType) {
            ContentType contentType = (ContentType) obj;
            return Objects.equals(this.name, contentType.name)
                    && Objects.equals(this.title, contentType.title);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.name != null ? this.name.hashCode() : 0;
    }
}
