package me.eddiep.android.floe.social;

import java.util.List;

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
    public static List<FeedItem> popSort(List<FeedItem> a){
        FeedItem q;
        for (int i=0; i<a.size();i++){
            for (int j=i+1;j<a.size();j++){
                if(a.get(i).getNumberOfLikes()+(a.get(i).getComments().length*10)<a.get(j).getNumberOfLikes()+(a.get(j).getComments().length*10)){
                    q=a.get(i);
                    a.set(i, a.get(j));
                    a.set(j, q);
                }
            }
        }
        return a;
    }

    public static List<FeedItem> trendSort(List<FeedItem> a){
        FeedItem q;
        for (int i=0; i<a.size();i++){
            for (int j=i+1;j<a.size();j++){
                if((a.get(i).getNumberOfLikes()+(a.get(i).getComments().length*5)/(a.get(i).getTime()/360))<(a.get(j).getNumberOfLikes()+(a.get(j).getComments().length*5)/(a.get(j).getTime()/360))){
                    q=a.get(i);
                    a.set(i, a.get(j));
                    a.set(j, q);
                }
            }
        }
        return a;
    }
}
