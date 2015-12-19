package com.codepath.apps.simpleTweets.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.simpleTweets.fragments.HomeTimelineFragment;
import com.codepath.apps.simpleTweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.simpleTweets.fragments.TweetsListFragment;
import com.codepath.apps.simpleTweets.R;
import com.codepath.apps.simpleTweets.helpers.MyIdentity;
import com.codepath.apps.simpleTweets.helpers.NetworkUtils;

public class TimelineActivity extends AppCompatActivity {
    public static final int REQ_CODE_COMPOSE_TWEET = 1;
    TweetsPagerAdapter tweetsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //save screen name for my account
        MyIdentity myIdentity = new MyIdentity();
        myIdentity.verifyCredentials(getApplicationContext());

        // To show logo on action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.twitter_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        //set title on action bar
        getSupportActionBar().setTitle(R.string.title_activity_timeline);

        //get the viewpager
        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);
        //set the viewpager adapter for the pager
        tweetsPagerAdapter = new TweetsPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(tweetsPagerAdapter);
        //find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        //Attach the tabstrip to viewpager
        tabStrip.setViewPager(vp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miCompose:
                //Toast.makeText(this, "Compose selected", Toast.LENGTH_SHORT)
                 //       .show();
                composeTweet();
                return true;
            case R.id.miProfile:
                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT)
                      .show();
                composeProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void composeTweet() {
        //first check if network connection is available
        if(NetworkUtils.isConnectedToNetwork((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)) == false) {
            Toast.makeText(this.getApplicationContext(), "Please check your network connection!", Toast.LENGTH_LONG).show();
            return;
        }
            Intent i = new Intent(this, ComposeActivity.class);
            startActivityForResult(i, REQ_CODE_COMPOSE_TWEET);
    }

    private void composeProfile() {
        //first check if network connection is available
        if(NetworkUtils.isConnectedToNetwork((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)) == false) {
            Toast.makeText(this.getApplicationContext(), "Please check your network connection!", Toast.LENGTH_LONG).show();
            return;
        }
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("screen_name", (String) null);
        startActivity(i);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_COMPOSE_TWEET && resultCode == RESULT_OK) {
            //HomeTimelineFragment fragmentHomeTimeline = (HomeTimelineFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
            HomeTimelineFragment fragmentHomeTimeline = (HomeTimelineFragment) tweetsPagerAdapter.getRegisteredFragment(0);
            fragmentHomeTimeline.refereshTimeline();
            Toast.makeText(this.getApplicationContext(), "Successfully Tweeted!!", Toast.LENGTH_SHORT).show();
        }
    }

    //return the order of fragments in viewpager
    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = {"Home", "Mentions"};
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        //adapter gets manager to insert/remove fragments from activity
        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //The order and creation of fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        //return tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        //how many fragments to swipe between
        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }
}
