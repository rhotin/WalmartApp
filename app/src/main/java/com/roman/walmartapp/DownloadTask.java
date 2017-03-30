package com.roman.walmartapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DownloadTask extends AsyncTask<Void, Integer, Void> {

    ArrayList<ItemObject> itemsArrayList = new ArrayList<>();
    Communicator context;

    DownloadTask(Context c) {
        this.context = (Communicator) c;
    }

    @Override
    protected Void doInBackground(Void... params) {
        URL theUrl = null;
        try {
            theUrl = new URL(Search.WALMART_URL);
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(theUrl.openConnection().getInputStream(), "UTF-8"));
            String walmart_json = reader.readLine();
            JSONObject walmart_object = new JSONObject(walmart_json);
            JSONArray data_arr = walmart_object.getJSONArray("items");
            int totalImages = data_arr.length();
            for (int i = 0; i < totalImages; i++) {
                String item_name = data_arr.getJSONObject(i).getString("name");
                String caption = data_arr.getJSONObject(i).getString("upc");
                String model = data_arr.getJSONObject(i).getString("modelNumber");
                String price = data_arr.getJSONObject(i).getString("salePrice");
                String image_url = data_arr.getJSONObject(i).getString("thumbnailImage");
                String item_url = data_arr.getJSONObject(i).getString("productUrl");

                URL downloadURL = new URL(image_url);
                HttpURLConnection conn = (HttpURLConnection) downloadURL.openConnection();
                InputStream inputStream = conn.getInputStream();
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);

                ItemObject obj = new ItemObject(bmp, item_name, price, caption, model, item_url);
                itemsArrayList.add(obj);
                publishProgress((int) (((i + 1.0) / totalImages) * 100.0));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        context.updateProgressTo(values[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        context.updateUI(itemsArrayList);
    }

    interface Communicator {
        public void updateProgressTo(int progress);

        public void updateUI(ArrayList<ItemObject> photosArrayList);
    }
}