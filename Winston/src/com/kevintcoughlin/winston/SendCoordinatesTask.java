package com.kevintcoughlin.winston;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.NotificationManager;
import android.os.AsyncTask;
import android.util.Log;

class SendCoordinatesTask extends AsyncTask<String, Integer, String> {
	
	// Notification Manager
	NotificationManager mNotificationManager;
	
	@Override
	protected String doInBackground(String... params) {
			// Winston Server URL
			String URL = "http://secure-chamber-2921.herokuapp.com/add";
			
			// Create HTTP Client for POST Request
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost postMethod = new HttpPost(URL);
			
			// Get latitude and longitude from params
			String latitude = params[0];
			String longitude = params[1];
			
			// Get time of creation and format to date and time
			//
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			String creation = df.format(date);
			
			try {
		        // Create Coordinate POST Data
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
		        					nameValuePairs.add(new BasicNameValuePair("latitude", latitude));
		        					nameValuePairs.add(new BasicNameValuePair("longitude", longitude));
		        					nameValuePairs.add(new BasicNameValuePair("date", creation));
		        
		        // URL Encode Data for POST Request
		        postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        
		        // Debugging
		        Log.d("LOC_SERVICE", "Sending POST data: "+nameValuePairs);
		        
		        // Execute HTTP Post Request
		        HttpResponse response = client.execute(postMethod);
		        
			} catch (Exception e) {
				e.printStackTrace();
			}
		String response_string = "";
		return response_string;
	}
	@Override
	protected void onPostExecute(String result) {
		//
	}
}
