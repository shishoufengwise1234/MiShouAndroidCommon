package com.mishou.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${shishoufeng} on 17/4/14.
 * email:shishoufeng1227@126.com
 *
 * 获取打开指定文件的软件
 */

public class IntentFileUtils {

    private static final Map<String, String> typeMap = new HashMap<>();

    static {
        typeMap.put(".3gp", "video/3gpp");
        typeMap.put(".apk", "application/vnd.android.package-archive");
        typeMap.put(".asf", "video/x-ms-asf");
        typeMap.put(".avi", "video/x-msvideo");
        typeMap.put(".bin", "application/octet-stream");
        typeMap.put(".bmp", "image/bmp");
        typeMap.put(".c", "text/plain");
        typeMap.put(".class", "application/octet-stream");
        typeMap.put(".conf", "text/plain");
        typeMap.put(".cpp", "text/plain");
        typeMap.put(".doc", "application/msword");
        typeMap.put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        typeMap.put(".xls", "application/vnd.ms-excel");
        typeMap.put(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        typeMap.put(".exe", "application/octet-stream");
        typeMap.put(".gif", "image/gif");
        typeMap.put(".gtar", "application/x-gtar");
        typeMap.put(".gz", "application/x-gzip");
        typeMap.put(".h", "text/plain");
        typeMap.put(".htm", "text/html");
        typeMap.put(".html", "text/html");
        typeMap.put(".jar", "application/java-archive");
        typeMap.put(".java", "text/plain");
        typeMap.put(".jpeg", "image/jpeg");
        typeMap.put(".jpg", "image/jpeg");
        typeMap.put(".js", "application/x-javascript");
        typeMap.put(".log", "text/plain");
        typeMap.put(".m3u", "audio/x-mpegurl");
        typeMap.put(".m4a", "audio/mp4a-latm");
        typeMap.put(".m4b", "audio/mp4a-latm");
        typeMap.put(".m4p", "audio/mp4a-latm");
        typeMap.put(".m4u", "video/vnd.mpegurl");
        typeMap.put(".m4v", "video/x-m4v");
        typeMap.put(".mov", "video/quicktime");
        typeMap.put(".mp2", "audio/x-mpeg");
        typeMap.put(".mp3", "audio/x-mpeg");
        typeMap.put(".mp4", "video/mp4");
        typeMap.put(".mpc", "application/vnd.mpohun.certificate");
        typeMap.put(".mpe", "video/mpeg");
        typeMap.put(".mpeg", "video/mpeg");
        typeMap.put(".mpg", "video/mpeg");
        typeMap.put(".mpg4", "video/mp4");
        typeMap.put(".mpga", "audio/mpeg");
        typeMap.put(".msg", "application/vnd.ms-outlook");
        typeMap.put(".ogg", "audio/ogg");
        typeMap.put(".pdf", "application/pdf");
        typeMap.put(".png", "image/png");
        typeMap.put(".pps", "application/vnd.ms-powerpoint");
        typeMap.put(".ppt", "application/vnd.ms-powerpoint");
        typeMap.put(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        typeMap.put(".prop", "text/plain");
        typeMap.put(".rc", "text/plain");
        typeMap.put(".rmvb", "audio/x-pn-realaudio");
        typeMap.put(".rtf", "application/rtf");
        typeMap.put(".sh", "text/plain");
        typeMap.put(".tar", "application/x-tar");
        typeMap.put(".tgz", "application/x-compressed");
        typeMap.put(".txt", "text/plain");
        typeMap.put(".wav", "audio/x-wav");
        typeMap.put(".wma", "audio/x-ms-wma");
        typeMap.put(".wmv", "audio/x-ms-wmv");
        typeMap.put(".wps", "application/vnd.ms-works");
        typeMap.put(".xml", "text/plain");
        typeMap.put(".z", "application/x-compress");
        typeMap.put(".zip", "application/x-zip-compressed");
        typeMap.put("", "*/*");
    }

    /**
     * 打开文件
     * @param filePath
     * @return
     */
    public static Intent openFile(String filePath) {

        File file = new File(filePath);
        if (!file.exists()) return null;
        /* 取得扩展名 */
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
        /* 依扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") ||
                end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            return getAudioFileIntent(filePath);
        } else if (end.equals("3gp") || end.equals("mp4")) {
            return getAudioFileIntent(filePath);
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
                end.equals("jpeg") || end.equals("bmp")) {
            return getImageFileIntent(filePath);
        } else if (end.equals("apk")) {
            return getApkFileIntent(filePath);
        } else if (end.equals("ppt")) {
            return getPptFileIntent(filePath);
        } else if (end.equals("xlsx")) {//xlsx xls
            return getExcelFileIntent(filePath);
        }else if (end.equals("xls")){
            return getExcelFileIntent(filePath);
        }else if (end.equals("doc")) {
            return getWordFileIntent(filePath);
        } else if (end.equals("pdf")) {
            return getPdfFileIntent(filePath);
        } else if (end.equals("chm")) {
            return getChmFileIntent(filePath);
        } else if (end.equals("txt")) {
            return getTextFileIntent(filePath, false);
        } else {
            return getAllIntent(filePath);
        }
    }

    //Android获取一个用于打开APK文件的intent
    public static Intent getAllIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    //Android获取一个用于打开APK文件的intent
    public static Intent getApkFileIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    //Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    //Android获取一个用于打开AUDIO文件的intent
    public static Intent getAudioFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    //Android获取一个用于打开Html文件的intent
    public static Intent getHtmlFileIntent(String param) {

        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    //Android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    //Android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    //Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    //Android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    //Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String param, boolean paramBoolean) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    //Android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    private static String getFileType(File file) {
        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /*获取文件的后缀名*/
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if ("".equals(end)) return type;
        String typeFound = typeMap.get(end);
        return typeFound == null ? type : typeFound;
    }

    /**
     * 打开文件
     * @param context context对象
     * @param file 待打开文件
     */
    @SuppressWarnings("unchecked")
    public static void openFile(@NonNull Context context, @NonNull File file) {
        if (context == null)return;
        if (file == null) return;

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的类型
        String type = getFileType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(Uri.fromFile(file), type);
        //跳转
        context.startActivity(intent);
    }
}
