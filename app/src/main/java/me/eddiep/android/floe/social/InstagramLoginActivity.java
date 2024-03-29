package me.eddiep.android.floe.social;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import org.jinstagram.Instagram;
import org.jinstagram.auth.model.Token;

import me.eddiep.android.floe.FeedActivity;
import me.eddiep.android.floe.LoginActivity;
import me.eddiep.android.floe.social.AuthHolder;
import me.eddiep.android.floe.social.insta.InstagramExt;

public class InstagramLoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            Uri data = intent.getData();
            if (data != null) {
                String[] split = data.toString().split("#");
                if (split.length > 1) {
                    String token = split[1].split("=")[1];
                    InstagramExt instagram = new InstagramExt(new Token(token, "6c3427ced9ab40c79c6fd25310e8900b"));
                    AuthHolder.instagramEnabled = true;
                    AuthHolder.instagramSession = instagram;
                    AuthHolder.socialCount++;
                }
            }
        }

        Intent switchIntent = new Intent(this, LoginActivity.class);
        startActivity(switchIntent);
    }
}
