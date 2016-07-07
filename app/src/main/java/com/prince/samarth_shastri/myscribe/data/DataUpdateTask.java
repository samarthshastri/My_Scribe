package com.prince.samarth_shastri.myscribe.data;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Samarth_Shastri on 5/4/16.
 */
public class DataUpdateTask  extends AsyncTask<String, Void, Void> {

    String update;
    String text;
    private final Context mContext;

    public DataUpdateTask (Context context,String s1, String s2)
    {
        update = s1;
        text = s2;
        mContext = context;
    }
    public void updatetask()
    {
        ContentValues locationValues = new ContentValues();
        locationValues.put(ScribeContract.ScribeEntry.COLUMN_NOTE_CONTENT,text);
       // String[20] s = new String[];
       String[] s = new String[]{update};

       int update = mContext.getContentResolver().update(ScribeContract.ScribeEntry.CONTENT_URI,locationValues,null,s);
       // Log.d("Sam","updated"+text);

    }

    @Override
    protected Void doInBackground(String... strings) {
        updatetask();
        return null;
    }
}
