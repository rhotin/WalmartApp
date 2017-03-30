package com.roman.walmartapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends Activity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,
        DownloadLocation.Communicator, OnMapReadyCallback {

    GoogleApiClient mGoogleApiClient;
    GoogleMap mGoogleMap;
    LocationRequest mLocationRequest;
    Marker marker;
    static LocationObject objLoc = new LocationObject(0,0);

    public static String WALMART_URL_LOCATION = "";
    double mLat = 0;
    double mLong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_map_found);

        if (googleServicesAvailable()) {
            setContentView(R.layout.activity_maps);
            if (initMap()) {
                mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).
                        addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
                mGoogleApiClient.connect();
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mGoogleMap.setMyLocationEnabled(true);
            }
        }
    }

    public String setURLLocation(double locLat, double locLong) {
        String mURLLoc = "http://api.walmartlabs.com/v1/stores?apiKey=" + Search.mAPIKey +
                "&format=json&lat=" + locLat + "&lon=" + locLong;
        objLoc.mLat = locLat;
        objLoc.mLong = locLong;
        DownloadLocation downloadLoc = new DownloadLocation(this);
        downloadLoc.execute();
        return mURLLoc;
    }

    public void geoLocate(View v) throws IOException {
        EditText et = (EditText) findViewById(R.id.mapEditText);
        String location = et.getText().toString();
        String locality = "";
        MarkerOptions options;
        if (!location.isEmpty()) {
            Geocoder gc = new Geocoder(this);
            List<Address> list = gc.getFromLocationName(location, 1);
            Address add = list.get(0);
            locality = add.getLocality();
            Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
            mLat = add.getLatitude();
            mLong = add.getLongitude();
            WALMART_URL_LOCATION = setURLLocation(mLat, mLong);
        } else {
            WALMART_URL_LOCATION = setURLLocation(mLat, mLong);
        }
        goToLocation(objLoc.mLat, objLoc.mLong, 15);           //mGoogleMap

        if (marker != null) {
            marker.remove();
        }

        if (!location.isEmpty()) {
            options = new MarkerOptions()
                    .title(locality).position(new LatLng(objLoc.mLat, objLoc.mLong));
        } else {
            options = new MarkerOptions()
                    .position(new LatLng(objLoc.mLat, objLoc.mLong));
        }
        marker = mGoogleMap.addMarker(options);
    }

    public void resetLocation(View v) {
        goToLocation(objLoc.mLat, objLoc.mLong, 15);           //mGoogleMap

        if (marker != null) {
            marker.remove();
        }
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(objLoc.mLat, objLoc.mLong));
        marker = mGoogleMap.addMarker(options);
    }

    private void goToLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mGoogleMap.moveCamera(update);
    }

    private void goToLocation(double lat, double lng, int zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
    }

    private boolean initMap() {
        if (mGoogleMap == null) {
            MapFragment mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);
        }
        return (mGoogleMap != null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mGoogleMap = googleMap;
        }
    }

    public boolean googleServicesAvailable() {
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle connectionHint) { // TODO Auto-generated method stub
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(3000000); // Update location every 3000 second
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            Toast.makeText(this, "Cant get Current location", Toast.LENGTH_LONG).show();
        } else {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
            mGoogleMap.animateCamera(update);
            mLong = location.getLongitude();
            mLat = location.getLatitude();
            objLoc = new LocationObject(mLat, mLong);
            WALMART_URL_LOCATION = setURLLocation(mLat, mLong);
            setMarker("Location", location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void setMarker(String locality, double lat, double lng) {
        if (marker != null) {
            marker.remove();
        }
        MarkerOptions options = new MarkerOptions().title(locality).position(new LatLng(lat, lng)).
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        marker = mGoogleMap.addMarker(options);
    }

    @Override
    public void updateProgressTo(int progress) {

    }

    @Override
    public void updateUI(ArrayList<LocationObject> photosArrayList) {
        goToLocation(objLoc.mLat, objLoc.mLong, 15);           //mGoogleMap

        if (marker != null) {
            marker.remove();
        }
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(objLoc.mLat, objLoc.mLong));
        marker = mGoogleMap.addMarker(options);
    }
}
