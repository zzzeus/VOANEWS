package com.hujiang.offlineword;

import android.util.Base64;

/**
 * Created by Zhou on 2017/8/27.
 */

public class OfflinewordAPI {
    static {
        System.loadLibrary("e");
        System.loadLibrary("d");
    }
    public static String getContent(String t){
        int a=0;
        byte[] bs=Base64.decode(t,0);
        bs=decodeAndUnzip(0,bs,true);
        return new String(bs);
    }
    public static native void addSplitChar(String a);

    public static native int appendDict(String a,String b);

    public static native byte[] decodeAndUnzip(int a,byte[] b,boolean c);

    public static native int getLastSearchMethod();
}
