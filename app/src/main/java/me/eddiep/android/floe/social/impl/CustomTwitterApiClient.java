package me.eddiep.android.floe.social.impl;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public class CustomTwitterApiClient extends TwitterApiClient {
    public CustomTwitterApiClient(Session session) {
        super(session);
    }

    public HomeTimeline getHomeTimeline() {
        return getService(HomeTimeline.class);
    }

    public interface HomeTimeline {

        @GET("/1.1/statuses/home_timeline.json")
        void home_timeline(Callback<List<TwitterFeedItem>> cb);
    }
}
