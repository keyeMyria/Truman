package com.kevintcoughlin.winston.receivers;

/***
   Extension of the CommonsWare Location Poller
   Copyright (c) 2010 CommonsWare, LLC
	
	Licensed under the Apache License, Version 2.0 (the "License"); you may
	not use this file except in compliance with the License. You may obtain
	a copy of the License at
	  http://www.apache.org/licenses/LICENSE-2.0
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.commonsware.cwac.locpoll.LocationPoller;
import com.kevintcoughlin.helpers.TrumanRESTClient;
import com.loopj.android.http.RequestParams;

/**
 * Receiver for Location Poller intents
 * 
 * @author CWAC
 */
public class LocationReceiver extends BroadcastReceiver {
	private final String TAG = "L_REC";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "RECEIVED AN INTENT");
		
		Bundle b=intent.getExtras();
		Location loc=(Location)b.get(LocationPoller.EXTRA_LOCATION);
		String msg = null;

		if(loc == null) {
			loc=(Location)b.get(LocationPoller.EXTRA_LASTKNOWN);

			if(loc == null) {
				Log.e(TAG, intent.getStringExtra(LocationPoller.EXTRA_ERROR));
			}
			/*else {
				Log.e(TAG, "TIMEOUT, lastKnown="+loc.toString());
			}*/
		}
		else {
			msg=loc.toString();
		}
		if (msg==null) {
			Log.e(TAG, "Invalid broadcast received!");
		}
		
		// Send Coordinates to Heroku
		sendCoordinates(loc.getLatitude(), loc.getLongitude());
	}
	
	/**
	 * POST's coordinates to Heroku Server
	 * 
	 * @param latitude
	 * @param longitude
	 */
	@SuppressLint("SimpleDateFormat")
	private static void sendCoordinates(double latitude, double longitude){	
		// Get time of creation and format to date and time
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String creation = df.format(date);
		
		// Build POST params to send
		RequestParams 	params = new RequestParams();
						params.put("latitude", String.valueOf(latitude));
						params.put("longitude", String.valueOf(longitude));
						params.put("date", creation);
		
		// POST Params Bundle to Heroku Server at /add
		TrumanRESTClient.post(params);
	}
	
	/*
	public boolean isOnline() {
	    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	}  
	*/
}