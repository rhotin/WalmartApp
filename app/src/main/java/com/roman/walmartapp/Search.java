package com.roman.walmartapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements DownloadTask.Communicator {

    //"https://api.walmartlabs.com/v1/search?query=ipod&start=1&numItems=25&format=json&apiKey=k5wqd8ptq4m9ax3ynbgxbb49"
    public static String WALMART_URL = "";

    String titleSearch = PreferencesActivity.titleSearch.equalsIgnoreCase("") ?
            "ipods" : PreferencesActivity.titleSearch;

    ListView walListView;
    ProgressBar progressBar;
    TextView messageText;
    TextView searchText;
    Button searchBtn;

    int mStartItemCount = 1;
    int mShowItems = 25;
    public static final String mAPIKey = "k5wqd8ptq4m9ax3ynbgxbb49";
    String mItemSearch = titleSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        WALMART_URL = setURL(mItemSearch, mStartItemCount, mShowItems);

        walListView = (ListView) findViewById(R.id.walmartListView);
        progressBar = (ProgressBar) findViewById(R.id.dataProgressBar);
        messageText = (TextView) findViewById(R.id.noDataText);
        searchText = (TextView) findViewById(R.id.searchEditText);
        searchBtn = (Button) findViewById(R.id.searchButton);

        updateListView();

        walListView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        messageText.setVisibility(View.VISIBLE);
    }

    private void updateListView() {
        DownloadTask downloadTask = new DownloadTask(this);
        downloadTask.execute();
    }

    public String setURL(String mSearch, int mStart, int mCount) {
        String mURL = "https://api.walmartlabs.com/v1/search?query=" + mSearch + "&start=" +
                mStart + "&numItems=" + mCount + "&format=json&apiKey=" + mAPIKey;
        return mURL;
    }

    @Override
    public void updateProgressTo(int progress) {
        progressBar.setProgress(progress);
    }

    @Override
    public void updateUI(final ArrayList<ItemObject> itemsArrayList) {
        walListView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        messageText.setVisibility(View.GONE);

        ItemAdapter adapter = new ItemAdapter(this, itemsArrayList);
        walListView.setAdapter(adapter);

        walListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemObject objectToPass = itemsArrayList.get(position);
                Intent intent = new Intent(Search.this, DetailActivity.class);
                intent.putExtra("theObject", objectToPass);
                startActivity(intent);
            }
        });
    }

    public void searchItems(View view) {
        String itemSearchText = searchText.getText().toString().replace(" ", "");
        WALMART_URL = setURL(itemSearchText, mStartItemCount, mShowItems);
        updateListView();
    }
}
