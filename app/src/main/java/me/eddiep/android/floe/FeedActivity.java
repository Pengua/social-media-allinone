package me.eddiep.android.floe;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.eddiep.android.floe.social.AuthHolder;
import me.eddiep.android.floe.social.FeedItem;
import me.eddiep.android.floe.social.FeedListAdapter;
import me.eddiep.android.floe.social.impl.CustomTwitterApiClient;
import me.eddiep.android.floe.social.impl.TwitterFeedItem;

public class FeedActivity extends Activity {

    private List<FeedItem> items = new ArrayList<>();
    private static final Object syncLock = new Object();
    private int gotCount;

    private ProgressBar progressBar;
    private ListView feedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feed);

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(255, 92, 231, 221)));

        progressBar = (ProgressBar)findViewById(R.id.progress);
        feedList = (ListView)findViewById(R.id.feedList);
        final FeedListAdapter adapter = new FeedListAdapter(this, items);
        feedList.setAdapter(adapter);


        new Thread(new Runnable() {
            @Override
            public void run() {
                if (AuthHolder.twitterEnabled) {
                    CustomTwitterApiClient client = new CustomTwitterApiClient(AuthHolder.twitterSession);

                    CustomTwitterApiClient.HomeTimeline timeline = client.getHomeTimeline();
                    timeline.home_timeline(new Callback<List<TwitterFeedItem>>() {
                        @Override
                        public void success(Result<List<TwitterFeedItem>> result) {
                            items.addAll(result.data);

                            synchronized (syncLock) {
                                gotCount++;
                                if (gotCount >= AuthHolder.socialCount) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                            feedList.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void failure(TwitterException e) {

                        }
                    });
                }
            }
        }).start();
    }

    private void finishLoading() {
    }
}
