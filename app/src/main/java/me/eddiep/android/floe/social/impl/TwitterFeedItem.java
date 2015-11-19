package me.eddiep.android.floe.social.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.eddiep.android.floe.social.CommentItem;
import me.eddiep.android.floe.social.FeedItem;

public class TwitterFeedItem implements FeedItem {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZZZ");

    private String created_at;
    private String text;
    private TwitterUser user;
    private int id;
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

    @Override
    public void buildContent() {

    }

    @Override
    public CommentItem[] getComments() {
        return new CommentItem[0];
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

    private class TwitterUser {
        private String name;
        private String profile_image_url;
        private int id;
        private String location;

        public String getName() {
            return name;
        }

        public String getProfileImage() {
            return profile_image_url;
        }

        public int getId() {
            return id;
        }

        public String getLocation() {
            return location;
        }
    }
}
