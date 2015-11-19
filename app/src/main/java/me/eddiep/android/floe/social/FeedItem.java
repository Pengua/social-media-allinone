package me.eddiep.android.floe.social;

public interface FeedItem {
    String getAuthor();

    long getTime();

    String getLocation();

    void buildContent();

    CommentItem[] getComments();

    String getOrigin();

    int getNumberOfLikes();

    String getTarget();

    boolean hasTarget();
}
