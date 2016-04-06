package com.roman.walmartapp;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DownloadLocation extends AsyncTask<Void, Integer, Void> {

    ArrayList<LocationObject> locationsArrayList = new ArrayList<>();
    Communicator context;

    DownloadLocation(Context c) {
        this.context = (Communicator) c;
    }

    @Override
    protected Void doInBackground(Void... params) {
        URL theUrlLoc = null;
        try {
            theUrlLoc = new URL(MapsActivity.WALMART_URL_LOCATION);
            BufferedReader readerLoc = new BufferedReader
                    (new InputStreamReader(theUrlLoc.openConnection().getInputStream(), "UTF-8"));
            String walmartLoc_json = readerLoc.readLine();
            JSONArray data_arrLoc = new JSONArray(walmartLoc_json);
            JSONObject walmart_objectLoc = data_arrLoc.getJSONObject(0);
            walmart_objectLoc.getJSONArray("coordinates").get(0);
            JSONArray mCoordinates = walmart_objectLoc.getJSONArray("coordinates");
            double mLong = mCoordinates.getDouble(0);
            double mLat = mCoordinates.getDouble(1);
            MapsActivity.objLoc.mLat = mLat;
            MapsActivity.objLoc.mLong = mLong;
            locationsArrayList.add(MapsActivity.objLoc);
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
        context.updateUI(locationsArrayList);
    }

    interface Communicator {
        public void updateProgressTo(int progress);

        public void updateUI(ArrayList<LocationObject> photosArrayList);
    }
}