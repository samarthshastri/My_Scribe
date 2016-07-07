package com.prince.samarth_shastri.myscribe;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.prince.samarth_shastri.myscribe.data.DataUpdateTask;
import com.prince.samarth_shastri.myscribe.data.ScribeContract;

public class ListDetailActivity extends AppCompatActivity {
    private ScribeAdapter mscribeAdapter;
    String forecastStr,id,s;
    //private ScribeAdapter mscribeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        id = intent.getStringExtra("id").toString();
        setTitle(id);
        setContentView(R.layout.activity_list_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (intent != null) {
            forecastStr = intent.getStringExtra("text").toString();
            id = intent.getStringExtra("id").toString();
           // Log.d("sam911",forecastStr);
          /*  ((TextView) this.findViewById(R.id.list_detail_item))
                    .setText(forecastStr);*/
            Uri scribeUri = ScribeContract.ScribeEntry.buildSribeWithName(forecastStr);
            // Now that we have some dummy forecast data, create an ArrayAdapter.
            // The ArrayAdapter will take data from a source (like our dummy forecast) and
            // use it to populate the ListView it's attached to.
//            Cursor cur = getBaseContext().getContentResolver().query(scribeUri,null,null,null,null);
//            int id_noteName = cur.getColumnIndex(ScribeContract.ScribeEntry.COLUMN_NOTE_CONTENT);
//            String al = cur.getString(id_noteName);
            Cursor cur = getBaseContext().getContentResolver().query(scribeUri, null, null, null, null);
           // int id_noteName = cur.getColumnIndex(ScribeContract.ScribeEntry.COLUMN_NOTE_NAME);
          //  String s = cur.getString(0);
            EditText editText = (EditText) findViewById(R.id.list_detail_item);
            editText.setText(forecastStr);

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText=(EditText)findViewById(R.id.list_detail_item);
                s = editText.getText().toString();
              //  Log.d("Sam3", s);
                DataUpdateTask dataUpdateTask = new DataUpdateTask(getBaseContext(),id,s);
                dataUpdateTask.execute();
                Toast.makeText(getBaseContext(), "Content Saved",
                        Toast.LENGTH_LONG).show();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onBackPressed()
    {

        EditText editText=(EditText)findViewById(R.id.list_detail_item);
        s = editText.getText().toString();
        //  Log.d("Sam3", s);
        DataUpdateTask dataUpdateTask = new DataUpdateTask(getBaseContext(),id,s);
        dataUpdateTask.execute();
        Toast.makeText(getBaseContext(), "Content Saved",
                Toast.LENGTH_LONG).show();
        NavUtils.navigateUpFromSameTask(this);
    }

}
