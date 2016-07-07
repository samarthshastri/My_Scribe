package com.prince.samarth_shastri.myscribe.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Samarth_Shastri on 5/2/16.
 */
public class ScribeProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private ScribeDbHelper mOpenHelper;

    static final int SCRIBE = 101;
    static final int SCRIBE_CONTENTS = 100;

    private Cursor scribeReturnDeatilData(Uri uri, String[] projection, String sortOrder)
    {
        String ScribeName = ScribeContract.ScribeEntry.getScribeFromUri(uri);
        String[] selectionArgs;
        String selection;
        selection = insertString;
        selectionArgs = new String[]{ScribeName};
        //Log.d("Sam",selection+insertString+ScribeName);
        return mOpenHelper.getReadableDatabase().query(ScribeContract.ScribeEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
    }

    private static final SQLiteQueryBuilder ScribeQueryBuilder;
    static {
        ScribeQueryBuilder = new SQLiteQueryBuilder();

    }

    private  static final String scribeData = ScribeContract.ScribeEntry.TABLE_NAME;
    private  static final String insertString = ScribeContract.ScribeEntry.TABLE_NAME+"."+ScribeContract.ScribeEntry.COLUMN_NOTE_NAME+"=?";



    private Cursor getScribeData(Uri uri,String[] projection, String sortOrder)
    {
        return ScribeQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new ScribeDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case SCRIBE_CONTENTS: {
                retCursor = mOpenHelper.getReadableDatabase().query(ScribeContract.ScribeEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            }
            case SCRIBE: {
                retCursor = scribeReturnDeatilData(uri, projection, sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
//            case WEATHER_WITH_LOCATION_AND_DATE:
//            case WEATHER_WITH_LOCATION:
            case SCRIBE:
                return ScribeContract.ScribeEntry.CONTENT_ITEM_TYPE;
            case SCRIBE_CONTENTS:
                return ScribeContract.ScribeEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case SCRIBE_CONTENTS:
            {
                long _id = db.insert(ScribeContract.ScribeEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = ScribeContract.ScribeEntry.buildScirbeUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        final String deleteString = ScribeContract.ScribeEntry.TABLE_NAME+"."+ScribeContract.ScribeEntry.COLUMN_NOTE_NAME+"=?";

        int deletedRows;
        switch (match) {
            case SCRIBE_CONTENTS:
                deletedRows = db.delete(ScribeContract.ScribeEntry.TABLE_NAME,deleteString,strings);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        if (deletedRows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deletedRows;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        final String updateString = ScribeContract.ScribeEntry.TABLE_NAME+"."+ScribeContract.ScribeEntry.COLUMN_NOTE_NAME+"=?";

        int rowsUpdated;
        switch (match) {
            case SCRIBE_CONTENTS:
                rowsUpdated = db.update(ScribeContract.ScribeEntry.TABLE_NAME,values,updateString,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    static UriMatcher buildUriMatcher() {
        // 1) The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case. Add the constructor below.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ScribeContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, ScribeContract.PATH_SCRIBE, SCRIBE_CONTENTS);
        matcher.addURI(authority,ScribeContract.PATH_SCRIBE+"/*",SCRIBE);
       // matcher.addURI(authority, ScribeContract.PATH_SCRIBE, SCRIBE);


        // 2) Use the addURI function to match each of the types.  Use the constants from
        // WeatherContract to help define the types to the UriMatcher.


        // 3) Return the new matcher!
        return matcher;
    }
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case SCRIBE_CONTENTS:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(ScribeContract.ScribeEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
