package com.mishou.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 网络类
 *
 * @author Wangsg
 */
public class NetUtil {

    /**
     * 检测网络是否可用;
     */
    public static boolean detectAvailable(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getWifiName(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getSSID();
    }

    /**
     * 检查信号强弱
     */
    public static int checkWifiRssi(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        wifiInfo = wifiManager.getConnectionInfo();
        // 获得信号强度值
        // 0到-50表示信号最好，-50到-70表示信号偏差，小于-70表示最差，有可能连接不上或者掉线，一般Wifi已断则值为-200
        int level = wifiInfo.getRssi();
        // 根据获得的信号强度发送信息
        return level;
    }

    /**
     * 判断是否为WIFI；
     */
    public static boolean detectWifi(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info != null
                        && info.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                }
            }
        }
        return false;
    }

    public static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return null;
        }
        final NetworkInfo activeInfo = connectivity.getActiveNetworkInfo();
        return activeInfo;
    }

    /**
     * 判断是否为cmwap，移动的为cmwap，联通的为3gwap、uniwap；
     *
     * @param context
     * @return boolean
     */
    public static boolean detectWap(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    if ((info.getTypeName().toLowerCase().indexOf("mobile") != -1)
                            && info.getExtraInfo() != null) {
                        // 移动网络；
                        if ((info.getExtraInfo().toLowerCase().indexOf("wap") != -1)) {
                            // 包含wap字符；
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取本机ip
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress != null && !inetAddress.isLoopbackAddress()) {
                        String ip = inetAddress.getHostAddress();
                        if (!StringUtils.isEmpty(ip)){
                            return ip;
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
            return "";
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        return "";
    }
}
