package com.prince.samarth_shastri.myscribe.data;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Samarth_Shastri on 5/23/16.
 */
public class DataDeleteTask extends AsyncTask<String, Void, Void> {

    String delete;
    String text;
    private final Context mContext;

    public DataDeleteTask (Context context,String s1)
    {
        text = s1;
        mContext = context;
    }
    public void deletetask()
    {
        //ContentValues locationValues = new ContentValues();
        //locationValues.put(ScribeContract.ScribeEntry.COLUMN_NOTE_CONTENT,text);
            //String[20] s = new String[];
            String[] s = new String[]{text};

        int update = mContext.getContentResolver().delete(ScribeContract.ScribeEntry.CONTENT_URI,null,s);
        //Log.d("Sam", "deleted" + text);

    }

    @Override
    protected Void doInBackground(String... strings) {
        deletetask();
        return null;
    }
}
