package com.example.week2weekendcelebrities.model.datasource.local.contentprovider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class CelebrityProviderContract {

    public static final String CONTENT_AUTHORITY ="com.example.week2weekendcelebrities.model.datasource.local.contentprovider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CELEBRITY = "celebrity";

    public static final class CelebrityEntry implements BaseColumns{
        public static final Uri CELEBRITY_CONTENT_URI = CONTENT_URI.buildUpon().appendPath(PATH_CELEBRITY).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir" + CELEBRITY_CONTENT_URI + "/"+ PATH_CELEBRITY;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item" + CELEBRITY_CONTENT_URI + "/"+ PATH_CELEBRITY;

        public static Uri buildCelebUri(long id){
            return ContentUris.withAppendedId(CELEBRITY_CONTENT_URI, id);
        }
    }
}
