package me.eddiep.android.social_media_allinone;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;


public class LoginActivity extends Activity {

    FloatingActionButton facebookButton;
    FloatingActionButton twitterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getActionBar() != null)
            getActionBar().hide();

        facebookButton = (FloatingActionButton)findViewById(R.id.fButton);
        twitterButton = (FloatingActionButton)findViewById(R.id.tButton);

        facebookButton.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_facebook).color(Color.WHITE));
        twitterButton.setImageDrawable(new IconDrawable(this, FontAwesomeIcons.fa_twitter).color(Color.WHITE));
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
