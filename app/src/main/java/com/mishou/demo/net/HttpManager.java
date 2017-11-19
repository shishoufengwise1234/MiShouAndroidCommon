package com.mishou.demo.net;

import com.mishou.demo.net.request.CustomGetRequest;
import com.mishou.demo.net.request.CustomPostRequest;

/**
 * Created by ${shishoufeng} on 17/11/17.
 * email:shishoufeng1227@126.com
 */

public class HttpManager {


    public static CustomGetRequest get(String url){
        return new CustomGetRequest(url);
    }


    public static CustomPostRequest post(String url){
        return new CustomPostRequest(url);
    }



}
