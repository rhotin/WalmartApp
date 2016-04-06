package com.roman.walmartapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class FavouriteActivity extends AppCompatActivity {

    TextView favTextView;

    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        favTextView = (TextView) findViewById(R.id.favTextView);

        db = new DBAdapter(this);


        db.open();
        Cursor c = db.getAllItems();
        if (c.moveToFirst())
        {
            do {
                favTextView.append(c.getString(1) + "\n\n");
            } while (c.moveToNext());
        }
        db.close();
    }

    public void resetDatabase(View view) {
        db.open();
        db.resetDB();
        db.close();
        favTextView.setText("");
    }
}
