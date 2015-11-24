package me.eddiep.android.floe.social.impl;

import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import org.jinstagram.entity.common.Images;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

import me.eddiep.android.floe.social.AuthHolder;
import me.eddiep.android.floe.social.CommentItem;
import me.eddiep.android.floe.social.FeedItem;

public class InstagramFeedItem implements FeedItem {
    private MediaFeedData data;

    public InstagramFeedItem(MediaFeedData data) {
        this.data = data;
    }

    @Override
    public String getAuthor() {
        return data.getUser().getUserName();
    }

    @Override
    public long getTime() {
        return Long.parseLong(data.getCreatedTime());
    }

    @Override
    public String getLocation() {
        if (data.getLocation() == null)
            return "";

        return data.getLocation().getName();
    }

    @Override
    public void buildContent(LinearLayout view) {
        final float scale = view.getContext().getResources().getDisplayMetrics().density;
        view.setPadding(0, 0, 0, 0);

        Images images = data.getImages();

        ImageView image = new ImageView(view.getContext());
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int)(200 * scale + 0.5f));
        parms.gravity = Gravity.CENTER_HORIZONTAL;
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);

        image.setLayoutParams(parms);

        Picasso.with(view.getContext()).load(images.getStandardResolution().getImageUrl()).into(image);

        view.addView(image);
    }

    @Override
    public CommentItem[] getComments() {
        return new CommentItem[0];
    }

    @Override
    public String getOrigin() {
        return "Instagram";
    }

    @Override
    public int getNumberOfLikes() {
        return data.getLikes().getCount();
    }

    @Override
    public String getTarget() {
        return "";
    }

    @Override
    public boolean hasTarget() {
        return false;
    }

    @Override
    public String getAvatarUrl() {
        return data.getUser().getProfilePictureUrl();
    }

    @Override
    public String getCommentName() {
        return "Comments";
    }

    @Override
    public boolean doesLike() {
        return data.isUserHasLiked();
    }

    @Override
    public void like(final Runnable callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AuthHolder.instagramSession.setUserLike(data.getId());
                    data.setUserHasLiked(true);
                } catch (InstagramException e) {
                    e.printStackTrace();
                }

                callback.run();
            }
        }).start();
    }
}
