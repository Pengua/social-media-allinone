package me.eddiep.android.floe.social;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.eddiep.android.floe.R;

public class FeedListAdapter extends ArrayAdapter<FeedItem> {
    public FeedListAdapter(Context context, int resource, List<FeedItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
        }
    }

    static class ViewHolder {
        CircleImageView profileImage;
        TextView username;
        TextView location;
        TextView source;
        TextView commentsText;
        RelativeLayout view;
        boolean built = false;
        ImageButton favButton;

    }
}
