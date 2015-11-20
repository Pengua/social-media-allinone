package me.eddiep.android.floe.social.impl;

import android.app.ActionBar;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void buildContent(RelativeLayout view) {
        final float scale = view.getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (80 * scale + 0.5f);

        String rawText = text;
        Matcher m = twitterUrl.matcher(rawText);

        String[] images = null;
        if (m.find()) {
            images = new String[m.groupCount()];
            for (int i = 0; i < images.length; i++) {
                images[i] = m.group(i);
            }

            rawText = rawText.replaceAll("https:\\/\\/t.co\\/[a-zA-Z0-9]*", "");
        }

        TextView tweet = new TextView(view.getContext());
        tweet.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tweet.setMinHeight(pixels);
        tweet.setGravity(Gravity.CENTER_VERTICAL);
        tweet.setTextColor(Color.BLACK);
        tweet.setText(rawText);

        view.addView(tweet);

        if (images != null) {
            for (String image1 : images) {
                ImageView image = new ImageView(view.getContext());
                ViewGroup.MarginLayoutParams parms = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parms.setMargins((int) (10 * scale + 0.5f), (int) (15 * scale + 0.5f), (int) (10 * scale + 0.5f), 0);
                image.setLayoutParams(parms);

                Picasso.with(view.getContext()).load(image1).into(image);

                view.addView(image);
            }
        }
    }

    @Override
    public CommentItem[] getComments() {
        return new CommentItem[0];
    }

    @Override
    public String getOrigin() {
        return "twitter";
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
}
