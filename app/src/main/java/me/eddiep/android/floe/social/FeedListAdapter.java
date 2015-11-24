package me.eddiep.android.floe.social;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.eddiep.android.floe.R;

public class FeedListAdapter extends ArrayAdapter<FeedItem> {
    LayoutInflater vi;
    Activity owner;
    public FeedListAdapter(Context context, List<FeedItem> objects, Activity owner) {
        super(context, -1, objects);
        vi = LayoutInflater.from(getContext());
        this.owner = owner;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        ViewHolder holder;

        if (v == null) {
            v = vi.inflate(R.layout.item_feed, parent, false);
        }

        if (v.getTag() != null) {
            holder = (ViewHolder)v.getTag();
        } else {
            holder = new ViewHolder();
            holder.profileImage = (CircleImageView)v.findViewById(R.id.profile_image);
            holder.username = (TextView)v.findViewById(R.id.username);
            holder.location = (TextView)v.findViewById(R.id.location);
            holder.source = (TextView)v.findViewById(R.id.source);
            holder.commentsText = (TextView)v.findViewById(R.id.comments_text);
            holder.view = (LinearLayout)v.findViewById(R.id.post_content);
            holder.favButton = (ImageButton)v.findViewById(R.id.fav_button);

            v.setTag(holder);
        }

        final FeedItem item = getItem(position);
        if (item != null) {
            holder.username.setText(item.getAuthor());
            holder.location.setText(item.getLocation());
            holder.source.setText(item.getOrigin());
            holder.commentsText.setText(item.getNumberOfLikes() + " " + item.getCommentName());

            if (item.doesLike()) {
                holder.favButton.setColorFilter(Color.argb(255, 210, 49, 50));
            } else {
                holder.favButton.setColorFilter(Color.argb(255, 129, 127, 121));
            }

            holder.favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.like(new Runnable() {
                        @Override
                        public void run() {
                            owner.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    notifyDataSetChanged();
                                }
                            });
                        }
                    });
                }
            });

            holder.view.removeAllViews();
            item.buildContent(holder.view);

            Picasso.with(getContext()).load(item.getAvatarUrl()).into(holder.profileImage);
        }

        return v;
    }

    static class ViewHolder {
        CircleImageView profileImage;
        TextView username;
        TextView location;
        TextView source;
        TextView commentsText;
        LinearLayout view;
        boolean built = false;
        ImageButton favButton;

    }
}
