package me.eddiep.android.floe.social.insta;

import com.google.gson.annotations.SerializedName;

import org.jinstagram.InstagramObject;
import org.jinstagram.entity.users.basicinfo.UserInfoData;

public class Follows extends InstagramObject {
    @SerializedName("data")
    private UserInfoData[] data;

    public Follows() {
    }

    public UserInfoData[] getData() {
        return this.data;
    }

    public void setData(UserInfoData[] data) {
        this.data = data;
    }

    public String toString() {
        return String.format("UserInfo [data=%s]", new Object[]{this.data});
    }
}
