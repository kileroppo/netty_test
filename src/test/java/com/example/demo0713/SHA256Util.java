package com.example.demo0713;



import org.junit.platform.commons.util.StringUtils;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHA256Util {
    private static int a = 5;

    public static void main(String[] args)  {
        /*  1223011886560936298    /  19925130066   密码: 9999999  测试
        *     514                 / 19925130022    密码:  12345678   正式环境
         *   1223011886560936391   /  13667038520   密码: 123456   测试 / 正式
         */
        BufferedReader buffreader = new BufferedReader(new InputStreamReader((System.in)));
        String passwd;
        int inputCount =0;
        try{
                while( ! (inputCount > 10) ){
                    System.out.println("请输入密码：");
                    passwd = buffreader.readLine();
                    if(StringUtils.isBlank(passwd)){
                        throw new Exception("输入为空");
                    }
                    getEncryptPassword(passwd);
                    inputCount++;
                }
        }catch (Exception e){
          e.printStackTrace();
        }
    }

    private static  String getEncryptPassword(String password){
        Long currentTime = System.currentTimeMillis() / 1000;
        String dd = currentTime.toString().substring(0, currentTime.toString().length() - 2);

        String sha256StrJava = getSHA256StrJava(password).substring(40,64);
        System.out.println("输入的密码："+password);
        System.out.println("登录token：" + getSHA256StrJava(sha256StrJava + dd));
        return getSHA256StrJava(sha256StrJava + dd);
    }

    public static String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
    // 倒数5分钟自动退出
//    private static boolean fiveMinuteTimer(){
////        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        long time1 = new Date().getTime() / 1000;
//        long time2 = time1;
//        while(!(time2 - time1 > 30)){
//             time2 = new Date().getTime() / 1000;
//            if(time2 - time1 > 300){
//                return true;
//            }
//        }
//        return false;
//    }
}