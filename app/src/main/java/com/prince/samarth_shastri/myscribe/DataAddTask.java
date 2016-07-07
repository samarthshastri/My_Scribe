package com.prince.samarth_shastri.myscribe;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.prince.samarth_shastri.myscribe.data.ScribeContract;

/**
 * Created by Samarth_Shastri on 5/2/16.
 */
public class DataAddTask  extends AsyncTask<String, Void, Void> {

    private final Context mContext;
    private final String i1;
    public DataAddTask(Context context, String m){
        mContext = context;
        i1 = m;
    }

    public void addScribe()
    {

        ContentValues locationValues = new ContentValues();
        locationValues.put(ScribeContract.ScribeEntry.COLUMN_NOTE_NAME,i1);
        locationValues.put(ScribeContract.ScribeEntry.COLUMN_NOTE_CONTENT,"");
        Uri insertedUri = mContext.getContentResolver().insert(
                ScribeContract.ScribeEntry.CONTENT_URI,
                locationValues
        );
       // Log.d("sam","inserted");
    }
    @Override
    protected Void doInBackground(String... strings) {
        addScribe();
        return null;
    }
}
