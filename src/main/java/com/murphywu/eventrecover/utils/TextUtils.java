package com.murphywu.eventrecover.utils;

/**
 * Created by murphywu on 2016/12/22.
 */
public class TextUtils {

    private TextUtils(){

    }

    public static boolean isEmpty(CharSequence str){
        return str == null || str.length() <= 0;
    }
}
