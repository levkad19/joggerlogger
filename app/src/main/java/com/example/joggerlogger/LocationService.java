package com.example.joggerlogger;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private static final long INTERVAL = 1000*2;
    private final long createdMillis = System.currentTimeMillis();
    private static final long FASTEST_INTERVAL=1000;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation, lStart, lEnd;
    static double distance=0;
    private final IBinder mBinder = new LocalBinder();

    private static List<Double> kmhList = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks (this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public LocationService getService(){
            return LocationService.this;
        }
    }
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval (INTERVAL);
        mLocationRequest.setFastestInterval (FASTEST_INTERVAL);
        mLocationRequest.setPriority (LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onLocationChanged (Location location) {
        MainActivity.progressDialog.dismiss();
        mCurrentLocation=location;
        if (lStart==null)
        {
            lStart= lEnd = mCurrentLocation;
        } else

            lEnd=mCurrentLocation;
        //Update Information
        updateUI();
    }

    @Override
    public void onConnected (@Nullable Bundle bundle) {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
        } catch (SecurityException e) {
        }


    }
    @Override
    public void onConnectionSuspended ( int i){

    }

    @Override
    public void onConnectionFailed (@NonNull ConnectionResult connectionResult){

    }

    public Double getDistance(){
        return distance;
    }

    private void updateUI() {

        long nowMillis = System.currentTimeMillis();
        if (MainActivity.p == 0) {
            int time=(int)((nowMillis - this.createdMillis) / 1000);
            distance = distance + (lStart.distanceTo(lEnd));
            MainActivity.endTime2 = System.currentTimeMillis();
            long diff = MainActivity.endTime2 - MainActivity.startTime2;
            double meters= distance/time;
            meters= (meters*3.6);
            String result= String.valueOf(meters);
            MainActivity.outputms.setText((int)meters+" km/h");
            distance= distance;
            lStart = lEnd;

            kmhList.add(meters);

            System.out.println("HUANSE:  " + kmhList);

        }
    }

    public List<Double> getKmhList(){
        return kmhList;
    }

    @Override
    public boolean onUnbind(Intent intent){
        stopLocationUpdates();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
        lStart = lEnd=null;
        distance=0;
        return super.onUnbind(intent);
    }
    private void stopLocationUpdates () {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (LocationListener) this);
        distance=0;

    }


}
