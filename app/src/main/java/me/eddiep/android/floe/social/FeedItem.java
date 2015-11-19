package me.eddiep.android.floe.social;

import android.widget.RelativeLayout;

public interface FeedItem {
    String getAuthor();

    long getTime();

    String getLocation();

    void buildContent(RelativeLayout view);

    CommentItem[] getComments();

    String getOrigin();

    int getNumberOfLikes();

    String getTarget();

    boolean hasTarget();
}
