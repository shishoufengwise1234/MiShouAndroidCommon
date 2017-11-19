package com.mishou.demo.bean;

/**
 * Created by ${shishoufeng} on 2016/9/3 0003.
 * email:shishoufeng1227@126.com
 */
public class RequestUserLogin {

    private String userName;
    private String password;

    /**
     * modify by wangsg
     * @Date 2016-10-22
     * @Description    登录添加参数im服务器 使用
     * @return
     */
    /** IOS用户推送设备号 */
    private String pushDeviceNum;
    /** 服务器端接口版本号(区分不同版本的同一个接口区别) 目前最高接口版本 01*/
    private String serverInterfaceVerNum;
    /** 本次用户登陆唯一标识号 /用户身份唯一标识号(指代该次登陆唯一标识) (推荐UUID去横杠)*/
    private String loginUserUniqueID;

    public String getPushDeviceNum() {
        return pushDeviceNum;
    }

    public void setPushDeviceNum(String pushDeviceNum) {
        this.pushDeviceNum = pushDeviceNum;
    }

    public String getServerInterfaceVerNum() {
        return serverInterfaceVerNum;
    }

    public void setServerInterfaceVerNum(String serverInterfaceVerNum) {
        this.serverInterfaceVerNum = serverInterfaceVerNum;
    }

    public String getLoginUserUniqueID() {
        return loginUserUniqueID;
    }

    public void setLoginUserUniqueID(String loginUserUniqueID) {
        this.loginUserUniqueID = loginUserUniqueID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
