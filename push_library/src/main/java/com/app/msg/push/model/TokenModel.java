package com.app.msg.push.model;

import com.app.msg.push.Target;
import com.app.msg.push.utils.L;


/**
 * Created by ssf
 */

public class TokenModel {
    private String mToken;
    @Target
    private int mTarget;

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        L.d("device Token", mToken);
        this.mToken = mToken;
    }

    public int getTarget() {
        return mTarget;
    }

    public void setTarget(@Target int mTarget) {
        this.mTarget = mTarget;
    }
}
