package com.prince.samarth_shastri.myscribe.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Samarth_Shastri on 5/1/16.
 */
public class ScribeDbHelper extends SQLiteOpenHelper {



    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "scribe.db";

    public ScribeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_WEATHER_TABLE = "CREATE TABLE " + ScribeContract.ScribeEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                ScribeContract.ScribeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data
                ScribeContract.ScribeEntry.COLUMN_NOTE_NAME + " TEXT NOT NULL, " +
                ScribeContract.ScribeEntry.COLUMN_NOTE_CONTENT + " TEXT NOT NULL "+" );";

        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

}
