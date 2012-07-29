package com.kevintcoughlin.winston;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

/**
 * Winston Service to track GPS Location changes and send to server
 * 
 * @author Kevin
 * With help from (http://stackoverflow.com/questions/8095030/background-service-need-to-send-gps-location-on-server)
 */
@SuppressWarnings("unused")
public class WinstonService extends IntentService {
	
	// Notification Manager
	NotificationManager mNotificationManager;
	Thread triggerService;
	
	// Result of Service
	private int result = Activity.RESULT_CANCELED;

	// Location variables
	private LocationManager locationManager = null;
	protected MyLocationListener MyLocationListener;
	
	// Logging Tag
	private static final String TAG = "WINSTON_SERVICE";
	
	/**
	 * Instantiate Winston Service
	 */
	public WinstonService() {
		super("WinstonService");
	}
	
	/**
	 * When service start is called begin service by adding location listener
	 */
	public int onStartCommand(Intent intent, int flags, int startId) {
	    super.onStartCommand(intent, flags, startId);      
	    Log.d("LOC_SERVICE", "Service start command called. Adding Location Listener.");
	    addLocationListener();
	    return START_STICKY;
	}
	
	// My Location Listener
	static class MyLocationListener implements LocationListener
	{
	    @Override
	    public void onLocationChanged(Location location)
	    {
	    	Log.d("LOC_SERVICE", "Location has changed!");
	        updateLocation(location);
	    }

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub		
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub		
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub	
		}
	}

	private void addLocationListener()
	{
		// Location Loop thread for GPS Updates
	    triggerService = new Thread(new Runnable(){
	        public void run(){
	            try{
	            	// Initialize looping thread
	                Looper.prepare();
	                locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	                
	                // Coarse Accuracy for GPS Provider
	                Criteria c = new Criteria();
	                c.setAccuracy(Criteria.ACCURACY_COARSE);
	                
	                // Get best provider from location manager
	                final String PROVIDER = locationManager.getBestProvider(c, true);
	                
	                // Create Location Listener for GPS changes
	                MyLocationListener = new MyLocationListener();
	                
	                // Request Location Updates every 10 minutes minimum with 1 meter minimum distance
	                locationManager.requestLocationUpdates(PROVIDER, 600000, 10, MyLocationListener);
	                
	                Log.d(TAG, "Thread loop started.");
	                Looper.loop();                
	            }catch(Exception ex){
	                ex.printStackTrace();
	            }
	        }
	    }, "LocationThread");
	    
	    // Start Looping Thread
	    triggerService.start();
	    Log.d("LOC_SERVICE", "Looping thread started.");
	}

	public static void updateLocation(Location location)
	{
		Log.d(TAG, "Updating location.");
	    Context appCtx = MainActivity.getAppContext();

	    double latitude, longitude;

	    latitude = location.getLatitude();
	    longitude = location.getLongitude();

	    // Prepare intent to send location
	    Intent filterRes = new Intent();
	    filterRes.setAction("com.kevintcoughlin.winston.intent.action.LOCATION");
	    filterRes.putExtra("latitude", latitude);
	    filterRes.putExtra("longitude", longitude);
	    
	    appCtx.sendBroadcast(filterRes);
	    Log.d(TAG, "Location broadcast sent.");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		// Handle Intent to Service
	}
	
	// Create Service Notification (API >11)
	/*
	@SuppressLint("NewApi")
	protected void createNotification(String latitude, String longitude){
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		Notification noti = new Notification.Builder(this)
        .setContentTitle("Location Sent")
        .setContentText("Latitude: "+latitude+" Longitude: "+longitude+" sent to Heroku.")
        .setSmallIcon(R.drawable.ic_launcher)
        .build();
		
		mNotificationManager.notify(0, noti);
	}
	*/
} 

