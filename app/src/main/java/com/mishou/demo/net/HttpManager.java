package com.mishou.demo.net;

import com.mishou.demo.net.request.CustomGetRequest;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 */

public class HttpManager {


    public static CustomGetRequest get(String url){
        return new CustomGetRequest(url);
    }

}
