package me.eddiep.android.social_media_allinone;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;

public class ApplicationEntry extends Activity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Iconify
                .with(new FontAwesomeModule())
                .with(new MaterialCommunityModule())
                .with(new MaterialModule());

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}