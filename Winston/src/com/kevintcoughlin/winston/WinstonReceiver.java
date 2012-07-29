package com.kevintcoughlin.winston;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Receiver for Winston Service
 * 
 * @author Kevin
 * 
 */
public class WinstonReceiver extends BroadcastReceiver { 
	double latitude, longitude;

    @Override
    public void onReceive(final Context context, final Intent calledIntent)
    {
        Log.d("LOC_RECEIVER", "Location RECEIVED!");

        // Get coordinates from intent bundle
        latitude = calledIntent.getDoubleExtra("latitude", -1);
        longitude = calledIntent.getDoubleExtra("longitude", -1);
        
        // Start Async Task with coords
        updateRemote(latitude, longitude);
    }

    /**
     * Call Async Task to POST coordinates to Server
     * @param latitude
     * @param longitude
     */
    private void updateRemote(final double latitude, final double longitude )
    {
	    // Location has changed, send location task
		SendCoordinatesTask task = new SendCoordinatesTask();
		task.execute(String.valueOf(latitude), String.valueOf(longitude));
    }
}
