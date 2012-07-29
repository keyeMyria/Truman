package com.kevintcoughlin.winston;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
    // Location variables
    private String provider;
    
    // Service Receiver
    private WinstonReceiver receiver = null;
    
    // UI Elements
    private Button checkin;
	private ListView listview;
	private ArrayAdapter<Coordinate> adapter;
	private List<Coordinate> values;
	
	// Context
	private static Context context;
	
	// SQLite Datasource
	//private CoordinatesDataSource datasource;
	
	// Logging Tag
	private static final String TAG = "WINSTON_ACTIVITY";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        MainActivity.context=getApplicationContext();
    	
        // SQLite
        //datasource = new CoordinatesDataSource(this);
		//datasource.open();

		// List of all coordinates from database
		//values = datasource.getAllCoordinates();
        
        // Get app's listview for locations
        //listview = (ListView)findViewById(R.id.locationlist);
    	
    	// Array adapter for list
    	//adapter = new ArrayAdapter<Coordinate>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
    	//listview.setAdapter(adapter);
    	
    	// Define check in button
    	checkin = (Button)findViewById(R.id.checkin);
    	checkin.setOnClickListener(checkinListener);
    	
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
    
	// Called when user presses checkin button
	public void saveLocation(){	
		// Create coordinate and insert into database
		//datasource.createCoordinate(LATITUDE, LONGITUDE);
		
		// List of all coordinates from database
		//values = datasource.getAllCoordinates();
    	//adapter = new ArrayAdapter<Coordinate>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
    	//listview.setAdapter(adapter);
		//adapter.notifyDataSetChanged();
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
		
	// On Checkin button click perform save coordinate action
    private OnClickListener checkinListener = new OnClickListener() {
        public void onClick(View v) {
            // Save location to database     	
            //saveLocation();
            //sendData();
            //values = datasource.getAllCoordinates();
        }
    };
    
	// On Listview item press
    @SuppressWarnings("unchecked")
	protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>)l.getItemAtPosition(position);
    }

    // Send data using Async Task
	public void sendData() {
    	//Intent i = new Intent(MainActivity.this, WinstonService.class);
    	//i.putExtra("latitude", LATITUDE);
    	//i.putExtra("longitude", LONGITUDE);
    	//MainActivity.this.startService(i);
	}
    // On Activity Resume request GPS updates
    protected void onResume(){
    	//datasource.open();
    	super.onResume();
    }
    
    // On Activity pause remove GPS updates
    protected void onPause(){
    	//datasource.close();
    	super.onPause();
    }
}
