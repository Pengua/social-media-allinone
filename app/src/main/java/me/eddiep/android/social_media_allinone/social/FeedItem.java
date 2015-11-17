package me.eddiep.android.social_media_allinone.social;

public interface FeedItem {
    String getAuthor();

    long getTime();

    String getLocation();

    void buildContent();

    CommentItem[] getComments();

    int getNumberOfLikes();

    String getTarget();
}
