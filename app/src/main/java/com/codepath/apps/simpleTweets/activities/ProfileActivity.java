package com.codepath.apps.simpleTweets.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.simpleTweets.R;
import com.codepath.apps.simpleTweets.fragments.ProfileHeaderFragment;
import com.codepath.apps.simpleTweets.fragments.UserTimelineFragment;
import com.codepath.apps.simpleTweets.helpers.TwitterApplication;
import com.codepath.apps.simpleTweets.helpers.TwitterClient;
import com.codepath.apps.simpleTweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    private TwitterClient client;
    User user;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // To show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // To show Logo on Action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.twitter_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //set title
        getSupportActionBar().setTitle(R.string.title_activity_profile);

        String screenName = getIntent().getStringExtra("screen_name");
        Log.d("MOIZ Profileactivity: ", "screenName: " + screenName);
        UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
        //Create the user Profile header fragment
        ProfileHeaderFragment fragmentProfileHeader = ProfileHeaderFragment.newInstance(screenName);
        //Display user timeline fragment within this activity (dynamically)
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flProfileHeaderContainer, fragmentProfileHeader, "profileHeader");
        ft.replace(R.id.flProfileTimelineContainer, fragmentUserTimeline, "profileTimeline");
        ft.commit(); //changes the fragment
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            // overridePendingTransition(R.animator.anim_left, R.animator.anim_right);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
