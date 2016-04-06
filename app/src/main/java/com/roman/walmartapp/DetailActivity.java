package com.roman.walmartapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class DetailActivity extends AppCompatActivity {

    TextView itemName;
    TextView priceText;
    TextView captionText;
    TextView modelText;
    ImageView photoView;
    TextView walmartItemURL;
    ItemObject obj;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = new DBAdapter(this);

        obj = getIntent().getParcelableExtra("theObject");
        itemName = (TextView) findViewById(R.id.itemName);
        priceText = (TextView) findViewById(R.id.priceText);
        captionText = (TextView) findViewById(R.id.captionText);
        modelText = (TextView) findViewById(R.id.modelText);
        photoView = (ImageView) findViewById(R.id.itemImage);
        walmartItemURL = (TextView) findViewById(R.id.URLTextView);

        itemName.setText(obj.itemName);
        NumberFormat priceFormat = NumberFormat.getCurrencyInstance();
        priceText.setText(priceFormat.format(Double.parseDouble(obj.priceText)));
        captionText.setText(obj.captionText);
        modelText.setText(obj.modelText);
        photoView.setImageBitmap(obj.thumbnail);
        walmartItemURL.setText(R.string.detailViewLinkItemToWalmart);

    }

    public void searchURLWalmart(View view) {
        Uri uri = Uri.parse(obj.mItemUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void saveToFavourites(View view) {
        db.open();
        Cursor c = db.getItem(obj.itemName);
        if (c.moveToFirst()) {
            db.deleteItem(obj.itemName);
            Toast.makeText(this, "Item removed from favourites", Toast.LENGTH_SHORT).show();
        }else {
            db.insertItem(obj.itemName);
            Toast.makeText(this, "Item added to favourites", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
