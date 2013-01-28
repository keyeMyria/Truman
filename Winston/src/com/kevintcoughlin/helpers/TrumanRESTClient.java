package com.kevintcoughlin.helpers;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Class to interact with Truman RESTful API using Loopj's AsyncHTTP library
 * @url http://loopj.com/android-async-http/
 * @author Kevin
 *
 */
public class TrumanRESTClient {
	private static final String BASE_URL = "http://truman.herokuapp.com/coordinates";

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		//
	}

	public static void post(RequestParams params) {
		client.post(BASE_URL, params, null);
	}

	@SuppressWarnings("unused")
	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}
