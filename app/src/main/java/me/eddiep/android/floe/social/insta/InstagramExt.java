package me.eddiep.android.floe.social.insta;

import org.jinstagram.Instagram;
import org.jinstagram.auth.model.Token;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.exceptions.InstagramException;
import org.jinstagram.http.Verbs;
import org.jinstagram.utils.Preconditions;

import java.util.Map;

public class InstagramExt extends Instagram {
    public InstagramExt(Token accessToken) {
        super(accessToken);
    }

    public Follows getUserFollows() throws InstagramException {
        return (Follows)this.createInstagramObject(Verbs.GET, Follows.class, "/users/self/follows", (Map)null);
    }
}
