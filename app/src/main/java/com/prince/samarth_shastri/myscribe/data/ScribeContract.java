package com.prince.samarth_shastri.myscribe.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Samarth_Shastri on 5/1/16.
 */
public class ScribeContract {
    public static final String CONTENT_AUTHORITY ="com.prince.samarth_shastri.myscribe";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SCRIBE = "scribe";

    public static final class ScribeEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCRIBE).build();

        public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCRIBE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCRIBE;

        public static final String TABLE_NAME = "scribe";
        public static final String COLUMN_NOTE_NAME = "note_name";
        public static final String COLUMN_NOTE_CONTENT = "note_content";


        public static Uri buildScirbeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri buildScribeURI() {

            return CONTENT_URI;
        }
        public static String getScribeFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }


        public static Uri buildSribeWithName(String name) {
            return CONTENT_URI.buildUpon().appendPath(name).build();
        }

    }
}
