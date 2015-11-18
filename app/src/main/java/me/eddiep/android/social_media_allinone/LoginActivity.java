package me.eddiep.android.social_media_allinone;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.Arrays;
import java.util.Collections;


public class LoginActivity extends Activity {

    private CallbackManager facebookCallbackManager;
    private FloatingActionButton facebookButton;
    private FloatingActionButton twitterButton;
    private CardView continueButton;
    private int connectedNetworks = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        facebookCallbackManager = CallbackManager.Factory.create();

        if (getActionBar() != null)
            getActionBar().hide();

        facebookButton = (FloatingActionButton)findViewById(R.id.fButton);
        twitterButton = (FloatingActionButton)findViewById(R.id.tButton);
        continueButton = (CardView)findViewById(R.id.continueButton);

        facebookButton.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_facebook).color(Color.WHITE));
        twitterButton.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_twitter).color(Color.WHITE));

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        facebookButton.setEnabled(false);
                        connectedNetworks++;
                        continueButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });

                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Collections.singletonList("read_stream"));
            }
        });

        continueButton.setClickable(true);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Go to next activity
            }
        });
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
