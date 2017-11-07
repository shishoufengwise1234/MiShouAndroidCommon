package com.mishou.common.utils;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xingzhezuomeng on 16/8/8.
 */
public class PhoneUtils {

    /**
     * 判断设备是否是手机
     */
    public static boolean isPhone(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取当前设备的IMIE
     * <p>需与上面的isPhone一起使用
     * <p>需添加权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     */
    public static String getDeviceIMEI(Context context) {
        String deviceId;
        if (isPhone(context)) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        } else {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    /**
     * 获取手机状态信息
     * <p>需添加权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     * <p>返回如下
     * <pre>
     * DeviceId(IMEI) = 99000311726612
     * DeviceSoftwareVersion = 00
     * Line1Number =
     * NetworkCountryIso = cn
     * NetworkOperator = 46003
     * NetworkOperatorName = 中国电信
     * NetworkType = 6
     * honeType = 2
     * SimCountryIso = cn
     * SimOperator = 46003
     * SimOperatorName = 中国电信
     * SimSerialNumber = 89860315045710604022
     * SimState = 5
     * SubscriberId(IMSI) = 460030419724900
     * VoiceMailNumber = *86
     * <pre/>
     */
    public static String getPhoneStatus(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String str = "";
        str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
        str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
        str += "Line1Number = " + tm.getLine1Number() + "\n";
        str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
        str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
        str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
        str += "NetworkType = " + tm.getNetworkType() + "\n";
        str += "honeType = " + tm.getPhoneType() + "\n";
        str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
        str += "SimOperator = " + tm.getSimOperator() + "\n";
        str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
        str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
        str += "SimState = " + tm.getSimState() + "\n";
        str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
        str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
        return str;
    }

    /**
     * 跳至填充好phoneNumber的拨号界面
     */
    public static void dial(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    }

    /**
     * 拨打phoneNumber
     * <p>需添加权限<uses-permission android:name="android.permission.CALL_PHONE"/>
     */
    public static void call(Context context, String phoneNumber) {
        context.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber)));
    }

    /**
     * 发送短信
     */
    public static void sendSms(Context context, String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }

    /**
     * 获取手机联系人
     * <p>需添加权限<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
     * <p>需添加权限<uses-permission android:name="android.permission.READ_CONTACTS" />
     */
    public static List<HashMap<String, String>> getAllContactInfo(Context context) {
        SystemClock.sleep(3000);
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        // 1.获取内容解析者
        ContentResolver resolver = context.getContentResolver();
        // 2.获取内容提供者的地址:com.android.contacts
        // raw_contacts表的地址 :raw_contacts
        // view_data表的地址 : data
        // 3.生成查询地址
        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri date_uri = Uri.parse("content://com.android.contacts/data");
        // 4.查询操作,先查询raw_contacts,查询contact_id
        // projection : 查询的字段
        Cursor cursor = resolver.query(raw_uri, new String[]{"contact_id"},
                null, null, null);
        // 5.解析cursor
        while (cursor.moveToNext()) {
            // 6.获取查询的数据
            String contact_id = cursor.getString(0);
            // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
            // : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
            // 判断contact_id是否为空
            if (!TextUtils.isEmpty(contact_id)) {//null   ""
                // 7.根据contact_id查询view_data表中的数据
                // selection : 查询条件
                // selectionArgs :查询条件的参数
                // sortOrder : 排序
                // 空指针: 1.null.方法 2.参数为null
                Cursor c = resolver.query(date_uri, new String[]{"data1",
                                "mimetype"}, "raw_contact_id=?",
                        new String[]{contact_id}, null);
                HashMap<String, String> map = new HashMap<String, String>();
                // 8.解析c
                while (c.moveToNext()) {
                    // 9.获取数据
                    String data1 = c.getString(0);
                    String mimetype = c.getString(1);
                    // 10.根据类型去判断获取的data1数据并保存
                    if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                        // 电话
                        map.put("phone", data1);
                    } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                        // 姓名
                        map.put("name", data1);
                    }
                }
                // 11.添加到集合中数据
                list.add(map);
                // 12.关闭cursor
                c.close();
            }
        }
        // 12.关闭cursor
        cursor.close();
        return list;
    }

    /**
     * 打开手机联系人界面点击联系人后便获取该号码
     * <p>参照以下注释代码
     */
    public static void getContantNum() {
        Log.i("tips", "U should copy the follow code.");
        /**
         Intent intent = new Intent();
         intent.setAction("android.intent.action.PICK");
         intent.setType("vnd.android.cursor.dir/phone_v2");
         startActivityForResult(intent, 0);
         @Override protected void onActivityResult ( int requestCode, int resultCode, Intent data){
         super.onActivityResult(requestCode, resultCode, data);
         if (data != null) {
         Uri uri = data.getData();
         String num = null;
         // 创建内容解析者
         ContentResolver contentResolver = getContentResolver();
         Cursor cursor = contentResolver.query(uri,
         null, null, null, null);
         while (cursor.moveToNext()) {
         num = cursor.getString(cursor.getColumnIndex("data1"));
         }
         cursor.close();
         num = num.replaceAll("-", "");//替换的操作,555-6 -> 5556
         }
         }
         */
    }

    /**
     * 获取手机短信并保存到xml中
     * <p>需添加权限<uses-permission android:name="android.permission.READ_SMS"/>
     * <p>需添加权限<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     */
    public static void getAllSMS(Context context) {
        //1.获取短信
        //1.1获取内容解析者
        ContentResolver resolver = context.getContentResolver();
        //1.2获取内容提供者地址   sms,sms表的地址:null  不写
        //1.3获取查询路径
        Uri uri = Uri.parse("content://sms");
        //1.4.查询操作
        //projection : 查询的字段
        //selection : 查询的条件
        //selectionArgs : 查询条件的参数
        //sortOrder : 排序
        Cursor cursor = resolver.query(uri, new String[]{"address", "date", "type", "body"}, null, null, null);
        //设置最大进度
        int count = cursor.getCount();//获取短信的个数
        //2.备份短信
        //2.1获取xml序列器
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            //2.2设置xml文件保存的路径
            //os : 保存的位置
            //encoding : 编码格式
            xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")), "utf-8");
            //2.3设置头信息
            //standalone : 是否独立保存
            xmlSerializer.startDocument("utf-8", true);
            //2.4设置根标签
            xmlSerializer.startTag(null, "smss");
            //1.5.解析cursor
            while (cursor.moveToNext()) {
                SystemClock.sleep(1000);
                //2.5设置短信的标签
                xmlSerializer.startTag(null, "sms");
                //2.6设置文本内容的标签
                xmlSerializer.startTag(null, "address");
                String address = cursor.getString(0);
                //2.7设置文本内容
                xmlSerializer.text(address);
                xmlSerializer.endTag(null, "address");
                xmlSerializer.startTag(null, "date");
                String date = cursor.getString(1);
                xmlSerializer.text(date);
                xmlSerializer.endTag(null, "date");
                xmlSerializer.startTag(null, "type");
                String type = cursor.getString(2);
                xmlSerializer.text(type);
                xmlSerializer.endTag(null, "type");
                xmlSerializer.startTag(null, "body");
                String body = cursor.getString(3);
                xmlSerializer.text(body);
                xmlSerializer.endTag(null, "body");
                xmlSerializer.endTag(null, "sms");
                System.out.println("address:" + address + "   date:" + date + "  type:" + type + "  body:" + body);
            }
            xmlSerializer.endTag(null, "smss");
            xmlSerializer.endDocument();
            //2.8将数据刷新到文件中
            xmlSerializer.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * added by wangsg  on 2016/12/08
     */
    private static final String defaultDeviceID = "1";

    private static List<String> INVALID_DEVICEID = new ArrayList<String>();

    static {
        INVALID_DEVICEID.add("358673013795895");
        INVALID_DEVICEID.add("004999010640000");
        INVALID_DEVICEID.add("00000000000000");
        INVALID_DEVICEID.add("000000000000000");
    }

    /**
     * 获取手机的基本信息如型号、API 级别；
     *
     * @return 手机信息
     */
    public static String getBasicInfo() {
        return Build.MODEL + "," + Build.VERSION.SDK_INT + "," + Build.VERSION.RELEASE;
    }

    /**
     * 获取应用版本号；
     *
     * @param context
     * @return 应用版本号，如1
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        if (context != null) {
            try {
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                verCode = info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return verCode;
    }

    /**
     * 获取版本名称；
     *
     * @param context
     * @return 应用版本名称，如1.0
     */
    public static String getVerName(Context context) {
        String verName = "";
        if (context != null) {
            try {
                PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                verName = info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return verName;

    }

    /**
     * 获取MAC地址，注意：手机重启，mac地址为null；
     *
     * @param context
     * @return mac地址；
     */
    public static String getMac(Context context) {
        if (context != null) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } else {
            return "";
        }
    }

    /**
     * 获取手机号码；
     *
     * @param context
     */
    public static String getPhoneNumber(Context context) {
        String number = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            number = tm.getLine1Number();
        }
        return number;
    }

    /**
     * 获取屏幕宽度；
     */
    public static int getScreenWidth(Context context) {
        return context.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度；
     */
    public static int getScreenHeight(Context context) {
        return context.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 验证手机号码是否正确;
     *
     * @param number 手机号码
     * @return true为合法，否则非法；
     */
    public static boolean checkPhoneNumber(String number) {
        if (number.length() == 11
                && (number.startsWith("13") || number.startsWith("14") || number.startsWith("15") || number.startsWith("16") || number.startsWith("18"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取设备号，缺点如下；
     * <p>
     * 非手机设备：如果只带有Wifi的设备或者音乐播放器没有通话的硬件功能的话就没有这个DEVICE_ID
     * 权限：获取DEVICE_ID需要READ_PHONE_STATE权限，但如果我们只为了获取它，没有用到其他的通话功能， 那这个权限有点大才小用
     * bug：在少数的一些手机设备上，该实现有漏洞，会返回垃圾，如:zeros或者asterisks的产品
     *
     * @param context 可能返回null
     * @deprecated Implement {@link #getDeviceID(Intent, int, int)}
     */
    public static String getDeviceID(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String uniqueID = tm.getDeviceId();
            if (StringUtils.isEmpty(uniqueID)) {
                uniqueID = getAndroidID(context);
            }
            return uniqueID;
        } catch (Exception e) {
            return defaultDeviceID;
        }
    }

    /**
     * 获取AndroidID，缺点如下：
     * <p>
     * 它在Android <=2.1 or Android >=2.3的版本是可靠、稳定的，但在2.2的版本并不是100%可靠的
     * 在主流厂商生产的设备上，有一个很经常的bug，就是每个设备都会产生相同的ANDROID_ID：9774d56d682e549c
     *
     * @param context
     */
    public static String getAndroidID(Context context) {
        String androidid = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidid;
    }


    /**
     * 获取手机品牌和型号；
     *
     * @param context
     */
    public static String getBrandModel(Context context) {
        String brand = Build.BRAND + Build.MODEL;
        return brand;
    }

    /**
     * 获取手机系统版本；
     *
     * @param context
     */
    public static String getPhoneVersion(Context context) {
        String version = Build.VERSION.RELEASE;
        return version;
    }

    /**
     * 获取屏幕分辨率；
     */
    public static String getResolution(Context context) {
        return getScreenWidth(context) + "*" + getScreenHeight(context);
    }

    /**
     * 检测网络类型；平板电脑可能没有电话service; 联通3GNETWORK_TYPE_UMTS，移动3GNETWORK_TYPE_HSDPA，
     * 电信3GNETWORK_TYPE_EVDO_0NETWORK_TYPE_EVDO_A
     *
     * @param context
     * @return boolean
     */
    public static String getNetWork(Context context) {
        String strNetworkType = "";

        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }

            }
        }
        return strNetworkType;
    }

    /**
     * 获取operator：运营商名称；
     *
     * @param context
     * @return 运营商名称
     */
    public static String getOperator(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String operator = telManager.getSimOperator();

        if (operator != null) {

            if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {

                // 中国移动
                return "中国移动";

            } else if (operator.equals("46001")) {

                // 中国联通
                return "中国联通";
            } else if (operator.equals("46003")) {

                // 中国电信
                return "中国电信";
            }
        }
        return "未知";

    }

    /**
     * 获取AppKey；
     *
     * @param context
     * @return AppKey
     */
    public static String getAppKey(Context context) {
        String appkey = "";
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            appkey = ai.metaData.getString("APPKEY") + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appkey;
    }

    /**
     * 获取渠道channelID；
     *
     * @param context
     * @return channelID
     */
    public static String getChannelID(Context context) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String appKey = ai.metaData.getString("CHANNELID") + "";
            return appKey;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取包名；
     *
     * @param context
     * @return channelID
     */
    public static String getPacakgeName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String pn = pi.packageName;
            return pn;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取UMENG_APPKEY；
     *
     * @param context
     * @return UMENG_APPKEY
     */
    public static String getUmeng(Context context) {
        String umeng_appkey = "";
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            umeng_appkey = ai.metaData.getString("UMENG_APPKEY") + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return umeng_appkey;
    }


    /**
     * UMENG_CHANNEL
     *
     * @param context
     * @return UMENG_CHANNEL
     */
    public static String getUmengChannel(Context context) {
        String umeng_appkey = "";
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            umeng_appkey = ai.metaData.getString("UMENG_CHANNEL") + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return umeng_appkey;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 当前Android系统版本是否在（ Donut） Android 1.6或以上
     *
     * @return
     */
    public static boolean hasDonut() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT;
    }

    /**
     * 当前Android系统版本是否在（ Eclair） Android 2.0或 以上
     *
     * @return
     */
    public static boolean hasEclair() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR;
    }

    /**
     * 当前Android系统版本是否在（ Froyo） Android 2.2或 Android 2.2以上
     *
     * @return
     */
    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * 当前Android系统版本是否在（ Gingerbread） Android 2.3x或 Android 2.3x 以上
     *
     * @return
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * 当前Android系统版本是否在（ Honeycomb） Android3.1或 Android3.1以上
     *
     * @return
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * 当前Android系统版本是否在（ HoneycombMR1） Android3.1.1或 Android3.1.1以上
     *
     * @return
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * 当前Android系统版本是否在（ IceCreamSandwich） Android4.0或 Android4.0以上
     *
     * @return
     */
    public static boolean hasIcecreamsandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * 当前Android系统版本是否在（ JELLY_BEAN_MR1） Android4.2或 Android4.2以上
     *
     * @return
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * 当前Android系统版本是否在（ KITKAT） Android4.4或 Android4.4以上
     * Build.VERSION_CODES.KITKAT=19
     *
     * @return
     */
    public static boolean hasKitkat() {
        return Build.VERSION.SDK_INT >= 19;
    }

    /**
     * 当前Android系统版本是否（ KITKAT） Android4.4 Build.VERSION_CODES.KITKAT=19
     *
     * @return
     */
    public static boolean isKitkat() {
        return Build.VERSION.SDK_INT == 19;
    }

    public static boolean isDroidXDroid2() {
        return (Build.MODEL.trim().equalsIgnoreCase("DROIDX") || Build.MODEL.trim().equalsIgnoreCase("DROID2")
                || Build.FINGERPRINT.toLowerCase().contains("shadow") || Build.FINGERPRINT.toLowerCase().contains("droid2"));
    }

    /**
     * Get the memory class of this device (approx. per-app memory limit)
     *
     * @param context
     * @return
     */
    public static int getMemoryClass(Context context) {
        return ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
    }

    /**
     * 获取CPU名字
     *
     * @param @return
     * @return String
     */
    public static String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            br.close();
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
