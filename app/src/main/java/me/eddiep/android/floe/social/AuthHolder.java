package me.eddiep.android.floe.social;

import com.twitter.sdk.android.core.TwitterSession;

import org.jinstagram.Instagram;

import me.eddiep.android.floe.social.insta.InstagramExt;

public class AuthHolder {
    public static boolean twitterEnabled;
    public static boolean instagramEnabled;
    public static TwitterSession twitterSession;
    public static InstagramExt instagramSession;
    public static int socialCount;
}
