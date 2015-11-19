package me.eddiep.android.floe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

public class ApplicationEntry extends Activity {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "D1wRJQmianwYq5A4SklSeozGB";
    private static final String TWITTER_SECRET = "HT6Ptz3KRiYBz1PJWh7UAR3W03ciJjlC9gaFvSjYnns4kYhxiQ";


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        Iconify
                .with(new FontAwesomeModule())
                .with(new MaterialCommunityModule())
                .with(new MaterialModule());

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
