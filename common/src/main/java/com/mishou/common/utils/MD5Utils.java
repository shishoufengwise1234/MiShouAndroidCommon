package com.mishou.common.utils;

/**
 * Created by xingzhezuomeng on 16/8/8.
 */
public class MD5Utils {

//    /**
//     * MD5加密
//     *
//     * @param data 明文字符串
//     * @return 密文
//     */
//    public static String getMD5(String data) {
//        return getMD5(data.getBytes());
//    }
//
//    /**
//     * MD5加密
//     *
//     * @param data 明文字符串
//     * @param salt 盐
//     * @return 密文
//     */
//    public static String getMD5(String data, String salt) {
//        return bytes2Hex(encryptMD5((data + salt).getBytes()));
//    }
//
//    /**
//     * MD5加密
//     *
//     * @param data 明文字节数组
//     * @return 密文
//     */
//    public static String getMD5(byte[] data) {
//        return bytes2Hex(encryptMD5(data));
//    }
//
//    /**
//     * MD5加密
//     *
//     * @param data 明文字节数组
//     * @param salt 盐字节数组
//     * @return 密文
//     */
//    public static String getMD5(byte[] data, byte[] salt) {
//        byte[] dataSalt = new byte[data.length + salt.length];
//        System.arraycopy(data, 0, dataSalt, 0, data.length);
//        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
//        return bytes2Hex(encryptMD5(dataSalt));
//    }
//
//    /**
//     * MD5加密
//     *
//     * @param data 明文字节数组
//     * @return 密文字节数组
//     */
//    public static byte[] encryptMD5(byte[] data) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(data);
//            return md.digest();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return new byte[0];
//    }
//
//    /**
//     * 获取文件的MD5校验码
//     *
//     * @param filePath 文件路径
//     * @return 文件的MD5校验码
//     */
//    public static String getMD5File(String filePath) {
//        FileInputStream in = null;
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            in = new FileInputStream(filePath);
//            int len;
//            byte[] buffer = new byte[1024];
//            while ((len = in.read(buffer)) != -1) {
//                md.update(buffer, 0, len);
//            }
//            return bytes2Hex(md.digest());
//        } catch (NoSuchAlgorithmException | IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException ignored) {
//                }
//            }
//        }
//        return "";
//    }
}
