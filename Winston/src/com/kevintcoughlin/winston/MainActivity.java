package com.kevintcoughlin.winston;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.commonsware.cwac.locpoll.LocationPoller;
import com.kevintcoughlin.winston.receivers.LocationReceiver;

public class MainActivity extends SherlockActivity {
	private static final int 	PERIOD 	= 900000;  				// 3 Minutes
	private PendingIntent 		pi 		= null;
	private AlarmManager 		mgr 	= null;
	private static Context 		context; 						
	private ToggleButton 		tb;
	private NotificationManager mNotificationManager;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MainActivity.context = getApplicationContext();
		
		this.getActionBar().setDisplayShowHomeEnabled(false);
		
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		// Attach ToggleButton Click Listener
		tb = (ToggleButton) findViewById(R.id.toggleService);
		tb.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				// ToggleButton is Checked
				if(!tb.isChecked()){
					// Stop Location Polling Service
					stopLocationPoller();
				}
				// ToggleButton is Unchecked
				else{
					// Start Location Polling Service
					startLocationPoller();
				}
			}
		});
	}

	/**
	 * Stop Location Poller Service
	 */
	private void stopLocationPoller(){
		// Remove Persistant Notification
		mNotificationManager.cancel(1);

		Intent stop_intent = new Intent(MainActivity.this, LocationPoller.class);
		PendingIntent senderstop = PendingIntent.getBroadcast(MainActivity.this, 1337, stop_intent, 0);

		mgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		mgr.cancel(senderstop);

		// Show Toast for User saying service has stopped3
		Toast.makeText(MainActivity.this, "Location Poller Stopped", Toast.LENGTH_LONG).show();	
		Log.d("TOGGLE", "POLLING STOPPED");
	}

	/** 
	 * Start Location Poller Service
	 */
	private void startLocationPoller(){
		Intent i = new Intent(MainActivity.this, LocationPoller.class);

		// Pass Location Receiver in intent
		i.putExtra(LocationPoller.EXTRA_INTENT, new Intent(MainActivity.this, LocationReceiver.class));

		// Pass GPS via intent
		i.putExtra(LocationPoller.EXTRA_PROVIDER, LocationManager.GPS_PROVIDER);
		pi = PendingIntent.getBroadcast(MainActivity.this, 1337, i, 0);

		// Repeat intent as defined in PERIOD variable
		mgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		mgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), PERIOD, pi);

		// Show Toast for User saying Service has started
		Toast.makeText(MainActivity.this, "Location Poller Started", Toast.LENGTH_LONG).show();

		// Show Persistant Notification
		showPersistantNotification();
		Log.d("TOGGLE", "POLLING STARTED");
	}

	/**
	 * On MENU button press
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    return super.onCreateOptionsMenu(menu);
	}
	
	/**
	 * On MENU item press
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Show Persistant Notification in Notification Bar
	 */
	@SuppressLint("NewApi")
	private void showPersistantNotification(){
		// Intent to Bring back Main Activity
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		
		// Pending Intent to bring back Main Activity
		PendingIntent mainactivity_intent = PendingIntent.getActivity(getApplicationContext(),   0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Build Truman Notification
		Notification notification = new Notification.Builder(context)
			.setContentTitle("Truman")
			.setContentText("Location Poller is running")
			.setSmallIcon(R.drawable.gps)
			.setOngoing(true)
			.setContentIntent(mainactivity_intent)
			.setPriority(Notification.PRIORITY_MAX)
			.build();

		mNotificationManager.notify(1, notification);
	}
	
	/**
	 * Return Activity's Context
	 * @return
	 */
	public static Context getAppContext() {
		return MainActivity.context;		
	}

	/** 
	 * On Activity Resume 
	 */
	protected void onResume(){
		super.onResume();
	}

	/**
	 * On Activity Pause
	 */
	protected void onPause(){
		super.onPause();
	}
}
