package com.tech42labs.stayalert.listeners;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Created by mari on 3/17/17.
 */

public class GPSLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        Context context=null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("latitude" , (long) location.getLatitude());
        editor.putLong("longitude" , (long) location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
