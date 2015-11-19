package me.eddiep.android.floe.social;

public interface FeedItem {
    String getAuthor();

    long getTime();

    String getLocation();

    void buildContent();

    CommentItem[] getComments();

    int getNumberOfLikes();

    String getTarget();

    boolean hasTarget();
}
