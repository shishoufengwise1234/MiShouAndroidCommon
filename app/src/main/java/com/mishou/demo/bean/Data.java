package com.mishou.demo.bean;

/**
 * Created by ${shishoufeng} on 17/11/21.
 * email:shishoufeng1227@126.com
 */

public class Data {

    /**
     * info : USERKEY_PLAT_NOMATCH
     * infocode : 10009
     * status : 0
     * sec_code_debug : d41d8cd98f00b204e9800998ecf8427e
     * key : 6c20603237eef36dd7c0d1fe9c402072
     * sec_code : a3cba04cf798be2f077fd405910cbb10
     */

    private String info;
    private String infocode;
    private String status;
    private String sec_code_debug;
    private String key;
    private String sec_code;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSec_code_debug() {
        return sec_code_debug;
    }

    public void setSec_code_debug(String sec_code_debug) {
        this.sec_code_debug = sec_code_debug;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSec_code() {
        return sec_code;
    }

    public void setSec_code(String sec_code) {
        this.sec_code = sec_code;
    }

    @Override
    public String toString() {
        return "Data{" +
                "info='" + info + '\'' +
                ", infocode='" + infocode + '\'' +
                ", status='" + status + '\'' +
                ", sec_code_debug='" + sec_code_debug + '\'' +
                ", key='" + key + '\'' +
                ", sec_code='" + sec_code + '\'' +
                '}';
    }
}
