package com.kevintcoughlin.winston;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	
	// Activity Context
	private static Context context;
	
	// Logging Tag
	private static final String TAG = "WINSTON_ACTIVITY";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        MainActivity.context = getApplicationContext();
    	
    	// Call Winston Service
    	callService();
    }
	// Return Context
    public static Context getAppContext() {
        return MainActivity.context;
    }
    
	// Call WinstonService
    private void callService () {
    	Log.d(TAG, "Starting Winston Service.");
        Intent i = new Intent(this, WinstonService.class);
        startService(i);
        Log.d(TAG, "Winston Service started.");
    }
    
    // Check if Service is running 
    /*
    private void checkLocationService()
    {
        Log.d("CHECK_SERVICE", "Service running: " + (settings.getBoolean("locationService", false)?"YES":"NO"));

        if(settings.getBoolean("locationService", false))
            return;

        Intent mServiceIntent = new Intent(this, LocationService.class);
        startService(mServiceIntent);
    }
    */

    // On Activity Resume
    protected void onResume(){
    	super.onResume();
    }
    
    // On Activity Pause
    protected void onPause(){
    	super.onPause();
    }
}
