package com.roman.walmartapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton searchBtn;
    ImageButton mapBtn;
    ImageButton favouriteBtn;
    ImageButton walmartWebBtn;
    ImageButton settingsBtn;
    ImageButton aboutAppBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBtn = (ImageButton) findViewById(R.id.walmartSearchItemsBtn);
        mapBtn = (ImageButton) findViewById(R.id.walmartMapViewBtn);
        favouriteBtn = (ImageButton) findViewById(R.id.favouriteBtn);
        walmartWebBtn = (ImageButton) findViewById(R.id.walmartWebsiteBtn);
        settingsBtn = (ImageButton) findViewById(R.id.settingsBtn);
        aboutAppBtn = (ImageButton) findViewById(R.id.aboutAppBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActivity = new Intent(MainActivity.this, Search.class);
                startActivity(searchActivity);
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapActivity = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(mapActivity);
            }
        });
        favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favActivity = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(favActivity);
            }
        });

        walmartWebBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webActivity = new Intent(MainActivity.this, WalmartWebsiteActivity.class);
                startActivity(webActivity);
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsActivity = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivity(settingsActivity);
            }
        });


        aboutAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutActivity = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutActivity);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent settingsActivity = new Intent(MainActivity.this, PreferencesActivity.class);
            startActivity(settingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

