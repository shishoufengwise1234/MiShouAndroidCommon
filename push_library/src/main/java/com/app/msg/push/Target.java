package com.app.msg.push;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ssf
 *
 * rom 类型
 */
@IntDef({Const.MIUI,Const.EMUI,Const.FLYME,Const.UMENG})
@Retention(RetentionPolicy.SOURCE)
public @interface Target {

}
