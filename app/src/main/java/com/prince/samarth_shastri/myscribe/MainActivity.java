package com.prince.samarth_shastri.myscribe;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {

                TextView textView = (TextView) findViewById(R.id.textView);
                Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
                textView.setTypeface(custom_font);

                TextView textView1 = (TextView) findViewById(R.id.textView2);
                textView1.setTypeface(custom_font);

                Button b1 = (Button) findViewById(R.id.button);
                b1.setTypeface(custom_font);
                b1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(getBaseContext(), ListActivity.class);
                        startActivity(i);

                    }
                });
            }
        });

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        Intent intent = new Intent(this, ListActivity.class);
        // button.setOnClickListener(startActivity(intent););




}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
