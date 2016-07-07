package com.prince.samarth_shastri.myscribe;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prince.samarth_shastri.myscribe.data.ScribeContract;

/**
 * Created by Samarth_Shastri on 5/2/16.
 */
public class ScribeAdapter extends CursorAdapter
{

    public ScribeAdapter(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
    }

    public String convertCursorRowToUXFormat(Cursor cursor)
    {
        int id_note = cursor.getColumnIndex(ScribeContract.ScribeEntry.COLUMN_NOTE_NAME);
        int id_noteName = cursor.getColumnIndex(ScribeContract.ScribeEntry.COLUMN_NOTE_CONTENT);
        //int id_noteId = cursor.getColumnIndex(ScribeContract.ScribeEntry.C)
        //ArrayList<String> al = new ArrayList<String>();
        String al = cursor.getString(id_note);
        return al;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_list_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv = (TextView)view.findViewById(R.id.list_item);
        tv.setText(convertCursorRowToUXFormat(cursor));

        //Button b = (Button)view.findViewById(R.id.id_button);
       // b.setText("delete");


    }



}
