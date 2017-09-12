package com.example.zhou.voanews.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Zhou on 2017/8/24.
 */

public class MySmaliTest {
    public static void main(String[] args) {

////        int t=getValue();
//            Byte[] bs=new   Byte[]{50,60,70,80,90};
////        int a = 0; a += 2;
//        for (Byte b:bs) {
//
//            System.out.println(getValue(b));
//        }
        getinfo();
        System.out.println("aaaaaaaaaa");

        uncompress("2Mz/fDjiIIsQOLuFhLy3q9Mempi3qUyXrI0vx743z/AM3rrSJbsgrKdmgEG1r2ijAU+nBavzx/cl\n" +
                "OsIsxOuIo24IKm9+jqReJNE8I1xgVj2+ZYPK6PnzkEH3Q6JSl6SSXc3rECp/uw5vH5YKL16EF58c\n" +
                "YM1gnj3ItWka0Ue0hpeQZIx4Cbu7gCIHD+GCiYF3Vwn3Hli7HKBhkrorERvFkjePv20aedNH39n0\n" +
                "fqVfkSrrCAZXIK9Z+VAbv1lH2ESNhJJ50YGWZqZ3fEKN3+oSZtr00Xqzt7UDojVPUgi8qsGNGuEU\n" +
                "DMD4wG6IP2vC2UiQpX67Xw8IlgthFSpnuVIw7OkFbpJMGhkh3J84gn4bUYuLW9+YKdKd5QregFKA\n" +
                "B5T4PLhq+1EWttq+iHTC58si6Z+cI+UHc7k0gwnFn/7qJobR8SBk2R1ZoDZX8wrnY0xwb5uc1JrO\n" +
                "Rsy91vHL59H/FoYeKM7UdTd+cHZkwvVYdtvyZtr5jvq6z1s37cVzEuOhBt+L2MiNZMDC2VQcU+ft\n" +
                "ldeuk97CXlU/NMGGG2CwLXKEV8oYvMK4E28uf8q4g+pO1HDg2NqVmNpjh4r3dCIWeNYA7ZcmErKN\n" +
                "wje2pPtf+dCBjL1YcdaH57PPY8OLELbDFZuTsj5UXfo6A6TEQbKmGuxw1daJktAZtIqEshU2DzVv\n" +
                "ZmcLo06ksBDQiEDbupUlxNnq9sR6qFytlxAJDsBrS5zBaSZqoh4C1STTvlNP1AF9mqzNlME16vnB\n" +
                "MZRdnf2MutcLLZnl7IAZc4oP1Rklg73MI1VWDT/bNJ5mM7NmpqWJVM1y1Ebyrb4nD+9t7t2DFcZg");

    }

        public static String compress(String str) {
            if (str == null || str.length() == 0) {
                return str;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = null;
            String s="";
            try {
                gzip = new GZIPOutputStream(out);
                gzip.write(str.getBytes());
                gzip.close();
                s=out.toString("ISO-8859-1");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return s;
        }

        // 解压缩
        public static String uncompress(String str) {
            if (str == null || str.length() == 0) {
                return str;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = null;
            try {
                in = new ByteArrayInputStream(str
                        .getBytes("ISO-8859-1"));
                GZIPInputStream gunzip = new GZIPInputStream(in);
                byte[] buffer = new byte[256];
                int n;
                while ((n = gunzip.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
            return out.toString();
        }
        private static int getValue(Byte t){
        String a="0123456789ABCDEF";
        int b=t>>0x4;
        b&=0xf;

        System.out.println("t = [" + t + "]");



        char c=a.charAt(b);
        return c;
    }
    public static void getinfo(){
//        String s1,String s2,String s3,String s4
        String id="b458dd683e237054f9a7302235dee675";
        String id2="3be65a6f99e98524e21e5dd8f85e2a9b";
        String sign=getL("en","cn","publisher","",id2);
        System.out.println(sign);
    }
    public static String getL(String s1,String s2,String s3,String s4,String s5){
        StringBuffer sb=new StringBuffer();
        sb.append("FromLang=");
        sb.append(s1);
        sb.append("&ToLang=");
        sb.append(s2);
        sb.append("&Word=");
        sb.append(s3);
        sb.append("&Word_Ext=");
        String s=sb.toString();
//        if(!TextUtils.isEmpty(s4)){
//            sb=new StringBuffer(s);
//            sb.append(s4);
//            s=sb.toString();
//        }
        sb=new StringBuffer(s);
        sb.append(s5);

        return getMD5(sb.toString());
    }
    public static String getMD5(String s){
        System.out.println("s = [" + s + "]");
        byte[] bs=null;
        try {
            bs=MessageDigest.getInstance("MD5").digest(s.getBytes());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return getString(bs);
    }
    public static String getString(byte[] bs){
//        String s="";
        int length=bs.length;
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i <length ; i++) {
//            System.out.println(bs[i]+0);
            mm(sb,bs[i]);
        }
        return sb.toString();
    }
    private static void mm(StringBuffer sb,byte b){
        String s="0123456789ABCDEF";
        int v=b>>0x4;
        v=v&0xf;
        char c=s.charAt(v);
        sb.append(c);
        v=b&0xf;
        c=s.charAt(v);
        sb.append(c);
    }
}
