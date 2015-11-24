package me.eddiep.android.floe;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.eddiep.android.floe.social.FeedItem;

public class ImageActivity extends Activity {
    public static FeedItem IM_TO_LAZY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActionBar() != null)
            getActionBar().hide();

        setContentView(R.layout.activity_image);

        String url = getIntent().getExtras().getString("image_url");

        ImageView view = (ImageView)findViewById(R.id.full_image);
        final ImageButton favButton = (ImageButton)findViewById(R.id.fav_button_img);
        TextView text = (TextView)findViewById(R.id.comments_text_img);

        Picasso.with(this).load(url).into(view);
        text.setText(IM_TO_LAZY.getNumberOfLikes() + " " + IM_TO_LAZY.getCommentName());

        if (IM_TO_LAZY.doesLike()) {
            favButton.setColorFilter(Color.argb(255, 210, 49, 50));
        } else {
            favButton.setColorFilter(Color.argb(255, 255, 255, 255));
        }

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IM_TO_LAZY.like(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (IM_TO_LAZY.doesLike()) {
                                    favButton.setColorFilter(Color.argb(255, 210, 49, 50));
                                } else {
                                    favButton.setColorFilter(Color.argb(255, 129, 127, 121));
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}
