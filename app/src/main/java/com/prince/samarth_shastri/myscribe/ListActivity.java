package com.prince.samarth_shastri.myscribe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.prince.samarth_shastri.myscribe.data.DataDeleteTask;
import com.prince.samarth_shastri.myscribe.data.ScribeContract;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    int i =1;
    private final Context mContext = getBaseContext();

    private ScribeAdapter mscribeAdapter;
    final List<String> scribeList = new ArrayList<String>();
   // ListView listView = new ListView()
    private ListView listView;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_list);

        Uri scribeUri = ScribeContract.ScribeEntry.CONTENT_URI;
        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        final Cursor cur = getBaseContext().getContentResolver().query(scribeUri,null,null,null,null);
        mscribeAdapter = new ScribeAdapter(getBaseContext(),cur,0);
        listView = (ListView) findViewById(R.id.content_list);
        listView.setAdapter(mscribeAdapter);
        registerForContextMenu(listView);
        mscribeAdapter.notifyDataSetChanged();


//        ListView listView = (ListView) findViewById(R.id.content_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);


                intent = new Intent(getBaseContext(), ListDetailActivity.class).setData(ScribeContract.ScribeEntry.buildScribeURI());
                // String s = cur.getString(position);
                // mscribeAdapter.getItem(position)
                int id_note = cursor.getColumnIndex(ScribeContract.ScribeEntry.COLUMN_NOTE_NAME);
                String s = cursor.getString(id_note);
                int id_note1 = cursor.getColumnIndex(ScribeContract.ScribeEntry.COLUMN_NOTE_CONTENT);
                String s1 = cursor.getString(id_note1);
                intent.putExtra("text", s1);
                intent.putExtra("id",s);
               // Log.d("sam88",s1);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            String m_Text;

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle("Enter Name for the Scribe");

// Set up the input
                final EditText input = new EditText(ListActivity.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        scribeList.add("Note");
                       // Log.d("sam", m_Text);
                        DataAddTask dataAddTask = new DataAddTask(getApplicationContext(), m_Text);
                        i++;
                        dataAddTask.execute();
                        intent = new Intent(getBaseContext(), ListDetailActivity.class).setData(ScribeContract.ScribeEntry.buildScribeURI());
                        String s = "";
                        intent.putExtra("text", s);

                        intent.putExtra("id",m_Text);
                        Uri scribeUri = ScribeContract.ScribeEntry.CONTENT_URI;

                        Cursor cur = getBaseContext().getContentResolver().query(scribeUri,null,null,null,null);
                        mscribeAdapter = new ScribeAdapter(getBaseContext(),cur,0);
                        ListView listView = (ListView) findViewById(R.id.content_list);
                       // intent.putExtra("text",mscribeAdapter.toString());
                        listView.setAdapter(mscribeAdapter);
                        startActivity(intent);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();



            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.content_list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            //menu.setHeaderTitle(Countries[info.position]);
           String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];
        if(menuItemName.equalsIgnoreCase("Delete"))
        {
            Cursor cursor = (Cursor) mscribeAdapter.getItem(info.position);
            int id_note = cursor.getColumnIndex(ScribeContract.ScribeEntry.COLUMN_NOTE_NAME);
            String s = cursor.getString(id_note);
            DataDeleteTask dataDeleteTask = new DataDeleteTask(getBaseContext(),s);
            dataDeleteTask.execute();
            this.mscribeAdapter.notifyDataSetChanged();
            this.listView.invalidateViews();


            //mscribeAdapter.swapCursor(cursor);
        }
       // String listItemName = Countries[info.position];
        Intent intent = new Intent(getBaseContext(),ListActivity.class);
        startActivity(intent);

        return true;
    }



    public void listcreate()  {


        Uri scribeUri = ScribeContract.ScribeEntry.buildScribeURI();
        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        Cursor cur = getBaseContext().getContentResolver().query(scribeUri,null,null,null,null);
        mscribeAdapter = new ScribeAdapter(getBaseContext(),cur,0);
        ListView listView = (ListView) findViewById(R.id.content_list);
        listView.setAdapter(mscribeAdapter);

    }
    @Override
    public void onBackPressed()
    {
        NavUtils.navigateUpFromSameTask(this);
    }


}



