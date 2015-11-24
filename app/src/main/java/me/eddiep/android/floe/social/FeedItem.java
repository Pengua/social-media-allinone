package me.eddiep.android.floe.social;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public interface FeedItem {
    String getAuthor();

    long getTime();

    String getLocation();

    void buildContent(LinearLayout view);

    CommentItem[] getComments();

    String getOrigin();

    int getNumberOfLikes();

    String getTarget();

    boolean hasTarget();

    String getAvatarUrl();

    String getCommentName();

    boolean doesLike();

    void like(final Runnable callback);
}
