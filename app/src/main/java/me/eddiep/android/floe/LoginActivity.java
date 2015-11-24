package me.eddiep.android.floe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import me.eddiep.android.floe.social.AuthHolder;


public class LoginActivity extends Activity {
    private static final String INSTAGRAM_AUTH_URL = "https://instagram.com/oauth/authorize/?client_id=d3952a7dd1ab4d1180d14ce18c18a44d&redirect_uri=floe://insta.log&response_type=token";


    private TwitterAuthClient client;
    private FloatingActionButton instagrameButton;
    private FloatingActionButton twitterButton;
    private CardView continueButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getActionBar() != null)
            getActionBar().hide();

        instagrameButton = (FloatingActionButton)findViewById(R.id.fButton);
        twitterButton = (FloatingActionButton)findViewById(R.id.tButton);
        continueButton = (CardView)findViewById(R.id.continueButton);
        final TextView continueButtonText = (TextView)findViewById(R.id.text);

        instagrameButton.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_instagram).color(Color.WHITE));
        twitterButton.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_twitter).color(Color.WHITE));

        if (AuthHolder.twitterSession != null) {
            twitterButton.setEnabled(false);
        }

        if (AuthHolder.instagramSession != null) {
            instagrameButton.setEnabled(false);
        }


        instagrameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(INSTAGRAM_AUTH_URL));
                startActivity(browserIntent);
            }
        });

        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client = new TwitterAuthClient();

                Twitter.logIn(LoginActivity.this, new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {
                        TwitterSession session = result.data;

                        AuthHolder.twitterEnabled = true;
                        AuthHolder.twitterSession = session;

                        Toast.makeText(getApplicationContext(), "Logged in as " + session.getUserName(), Toast.LENGTH_SHORT).show();

                        AuthHolder.socialCount++;
                        continueButtonText.setText("CONTINUE WITH " + AuthHolder.socialCount + " NETWORK" + (AuthHolder.socialCount == 1 ? "" : "s"));
                        continueButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void failure(TwitterException e) {
                        Toast.makeText(LoginActivity.this, ":c " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        continueButton.setClickable(true);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                startActivity(intent);
            }
        });

        if (AuthHolder.socialCount > 0) {
            continueButtonText.setText("CONTINUE WITH " + AuthHolder.socialCount + " NETWORK" + (AuthHolder.socialCount == 1 ? "" : "s"));
            continueButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (client != null) {
            client.onActivityResult(requestCode, resultCode, data);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
