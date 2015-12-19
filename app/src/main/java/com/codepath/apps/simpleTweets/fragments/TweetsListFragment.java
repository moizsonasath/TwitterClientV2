package com.codepath.apps.simpleTweets.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.codepath.apps.simpleTweets.R;
import com.codepath.apps.simpleTweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.simpleTweets.helpers.EndlessScrollListener;
import com.codepath.apps.simpleTweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by m.sonasath on 12/15/2015.
 */
public class TweetsListFragment extends Fragment {
    private ArrayList<Tweet> tweets;
    public TweetsArrayAdapter aTweets;
    public ListView lvTweets;
    public SwipeRefreshLayout swipeContainer;

    // inflation logic
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        //find the listview
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        //connect adapter to listview
        lvTweets.setAdapter(aTweets);
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        return v;
    }

    //creation lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create the arraylist (data source)
        tweets = new ArrayList<>();
        //construct the adapter from data source
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
    }

    public TweetsArrayAdapter getAdapter() {
        return aTweets;
    }
}
