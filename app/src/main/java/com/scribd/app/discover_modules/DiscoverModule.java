package com.scribd.app.discover_modules;

import com.scribd.dataia.ContentType;
import com.scribd.dataia.Document;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DiscoverModule {
    @Nullable
    public final CharSequence subtitle = "";
    public final ContentType[] contentTypes = new ContentType[] {};
    public final Map<String, String> auxData = new HashMap();

    public Document[] getDocuments() {
        return new Document[0];
    }

    public CharSequence getTitle() {
        return null;
    }

    public String getType() {
        return null;
    }

    public String getAuxDataAsString(String key) {
        return null;
    }

    public int getAuxDataAsInt(String key, int i) {
        return 0;
    }

    public boolean getAuxDataAsBoolean(String key) {
        return false;
    }

    public enum ItemDisplayVariant {
        THUMBNAIL, LIBRARY_RECENT, THUMBNAIL_LARGE, PORTRAIT_METADATA, PORTRAIT_METADATA_LARGE, ARTICLE
    }

    public enum Type {
        featured_document,
        hero_content,
        hero_issue,
        client_document_list_item
    }

    public enum AuxDataKey {
        HEADER_TYPE, SORT_TYPE, PROFILE_CONTENT_KEY, PROFILE_USER_ID
    }

    public static class MagazineAuxDataKey {
        public static final String MAGAZINE_IS_FOLLOWED = "magazine_followed";
    }
}
