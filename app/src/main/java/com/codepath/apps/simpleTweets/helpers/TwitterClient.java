package com.codepath.apps.simpleTweets.helpers;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "4Iuz0CC1ARLbBBirqQXuOQXED";       // Change this
	public static final String REST_CONSUMER_SECRET = "GBfPMIbCAij2oPMd7u493kya4v3I5D0UACICbslLpHLEWcDcv7"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://simpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// Method == Endpoint

	// HomeTimeline: gets us the home timeline
	//GET statuses/home_timeline.json
	//		count=25
	//		since_id=1

	public void getHomeTimeline(long since_id, long max_id, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		//specify the params
		RequestParams params = new RequestParams();
		params.put("count", 25);
		if (max_id > 0) {
			params.put("max_id", String.valueOf(max_id));
		} else if (since_id > 0) {
			params.put("since_id", String.valueOf(since_id));
		}
		params.put("include_entities", true);
		//execute the request
		client.get(apiUrl, params, handler);
	}

	//Compose tweet

	public void postTweet(String body, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", body);
		getClient().post(apiUrl, params, handler);
	}

	public void getMentionsTimeline(long since_id, long max_id, JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		//specify the params
		RequestParams params = new RequestParams();
		params.put("count", 25);
		if (max_id > 0) {
			params.put("max_id", String.valueOf(max_id));
		} else if (since_id > 0) {
			params.put("since_id", String.valueOf(since_id));
		}
		params.put("include_entities", true);
		//execute the request
		client.get(apiUrl, params, handler);
	}

	public void getUserTimeline(String screenName, long since_id, long max_id, JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("screen_name", screenName);
		params.put("count", 25);
		if (max_id > 0) {
			params.put("max_id", String.valueOf(max_id));
		} else if (since_id > 0) {
			params.put("since_id", String.valueOf(since_id));
		}
		params.put("include_entities", true);
		client.get(apiUrl, params, handler);
	}

	public void getUserInfo(String screenName, JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("users/show.json");
		RequestParams params = null;
		if (screenName != null && !screenName.isEmpty()) {
			params = new RequestParams();
			params.put("screen_name", screenName);
		}
		client.get(apiUrl, params, handler);
	}

	public void getUserCredentials(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		client.get(apiUrl, null, handler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}