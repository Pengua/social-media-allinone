package me.eddiep.android.floe.social.impl;

import android.app.ActionBar;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.eddiep.android.floe.social.AuthHolder;
import me.eddiep.android.floe.social.CommentItem;
import me.eddiep.android.floe.social.FeedItem;

public class TwitterFeedItem implements FeedItem {
    private static final Pattern twitterUrl = Pattern.compile("https:\\/\\/t.co\\/[a-zA-Z0-9]*");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZZZ");

    private String created_at;
    private String text;
    private TwitterUser user;
    private long id;
    private int retweet_count;
    private String in_reply_to_screen_name;
    private TwitterEntity entities;
    private boolean retweeted;
    private TwitterFeedItem retweeted_status;

    @Override
    public String getAuthor() {
        return user.getName();
    }

    @Override
    public long getTime() {
        try {
            return sdf.parse(created_at).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String getLocation() {
        return user.getLocation();
    }

    /*
    <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:minHeight="80dp"
                android:gravity="center_vertical"
                android:textColor="#000"
                android:text="If Phane Sorter does not get unbanned in five days, Swain will queue snipe you and press R on his keyboard."
                />
     */
    @Override
    public void buildContent(LinearLayout view) {
        final float scale = view.getContext().getResources().getDisplayMetrics().density;

        view.setPadding((int)(20 * scale + 0.5f), 0, (int)(20 * scale + 0.5f), 0);

        int pixels = (int) (80 * scale + 0.5f);

        String rawText = null;
        try {
            rawText = URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Matcher m = twitterUrl.matcher(rawText);

        List<String> images = new ArrayList<>();
        if (entities != null) {
            if (entities.media != null) {
                for (TwitterMediaEntity entity : entities.media) {
                    images.add(entity.media_url);
                    rawText = rawText.replace(entity.url, "");
                }
            }

            if (entities.urls != null) {
                for (TwitterURLEntity entity : entities.urls) {
                    rawText = rawText.replace(entity.url, entity.display_url);
                }
            }
        }

        if (!rawText.trim().equals("")) {
            TextView tweet = new TextView(view.getContext());
            tweet.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            tweet.setMinHeight(pixels);
            tweet.setGravity(Gravity.CENTER_VERTICAL);
            tweet.setTextColor(Color.BLACK);
            tweet.setText(rawText);

            view.addView(tweet);
        }

        if (images.size() > 0) {
            HorizontalScrollView scrollView = new HorizontalScrollView(view.getContext());
            scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            LinearLayout imageViews = new LinearLayout(view.getContext());
            imageViews.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            scrollView.addView(imageViews);

            for (String image1 : images) {
                ImageView image = new ImageView(view.getContext());
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                parms.setMargins((int) (10 * scale + 0.5f), (int) (15 * scale + 0.5f), (int) (10 * scale + 0.5f), 0);
                parms.gravity = Gravity.CENTER_HORIZONTAL;

                image.setLayoutParams(parms);

                Picasso.with(view.getContext()).load(image1).into(image);

                imageViews.addView(image);
            }

            view.addView(scrollView);
        }
    }

    @Override
    public CommentItem[] getComments() {
        return new CommentItem[0];
    }

    @Override
    public String getOrigin() {
        return "Twitter";
    }

    @Override
    public int getNumberOfLikes() {
        return retweet_count;
    }

    @Override
    public String getTarget() {
        return in_reply_to_screen_name;
    }

    @Override
    public boolean hasTarget() {
        return in_reply_to_screen_name != null;
    }

    @Override
    public String getAvatarUrl() {
        return user.getProfileImage();
    }

    @Override
    public String getCommentName() {
        return "Retweets";
    }

    @Override
    public boolean doesLike() {
        return retweeted;
    }

    @Override
    public void like(final Runnable callback) {
        CustomTwitterApiClient client = new CustomTwitterApiClient(AuthHolder.twitterSession);
        client.getStatusesService().retweet(id, true, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                retweeted = true;
                callback.run();
            }

            @Override
            public void failure(TwitterException e) {

            }
        });
    }

    private class TwitterUser {
        private String name;
        private String profile_image_url;
        private long id;
        private String location;

        public String getName() {
            return name;
        }

        public String getProfileImage() {
            return profile_image_url;
        }

        public long getId() {
            return id;
        }

        public String getLocation() {
            return location;
        }
    }

    private class TwitterEntity {
        private TwitterMediaEntity media[];
        private TwitterURLEntity urls[];
    }

    private class TwitterMediaEntity {
        private String media_url;
        private String url;
    }

    private class TwitterURLEntity {
        private String display_url;
        private String url;
    }
}
