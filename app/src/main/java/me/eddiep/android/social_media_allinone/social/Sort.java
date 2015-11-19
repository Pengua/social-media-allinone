package me.eddiep.android.social_media_allinone.social;

/**
 * Created by leec12 on 11/17/2015.
 */
public class Sort {

    public static FeedItem[] chronoSort(FeedItem[] a){
        FeedItem q;
        for (int i=0; i<a.length;i++){
            for (int j=i+1;j<a.length;j++){
                if(a[i].getTime()>a[j].getTime()){
                    q=a[i];
                    a[i]=a[j];
                    a[j]=q;
                }
            }
        }
        return a;
    }
    public static FeedItem[] popSort(FeedItem[] a){
        FeedItem q;
        for (int i=0; i<a.length;i++){
            for (int j=i+1;j<a.length;j++){
                if(a[i].getNumberOfLikes()+(a[i].getComments().length*10)<a[j].getNumberOfLikes()+(a[j].getComments().length*10)){
                    q=a[i];
                    a[i]=a[j];
                    a[j]=q;
                }
            }
        }
        return a;
    }

    public static FeedItem[] trendSort(FeedItem[] a){
        FeedItem q;
        for (int i=0; i<a.length;i++){
            for (int j=i+1;j<a.length;j++){
                if((a[i].getNumberOfLikes()+(a[i].getComments().length*5)/(a[i].getTime()/360))<(a[j].getNumberOfLikes()+(a[j].getComments().length*5)/(a[i].getTime()/360))){
                    q=a[i];
                    a[i]=a[j];
                    a[j]=q;
                }
            }
        }
        return a;
    }
}
