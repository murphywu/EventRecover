package com.murphywu.eventrecover.app;

import com.murphywu.eventrecover.utils.TextUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by murphywu on 2016/12/23.
 */
public class RunConfigs {

    private String mEventFilePath;

    private String mLogFilePath;

    private static volatile String SOURCE_CODE_PATH;

    private static volatile boolean isInited;

    private static final String mDefauleEventFileName = "Event.java";

    private static final String EVENT_FILE_INDICATOR = "-ef";

    private static final String LOG_FILE_INDICATOR = "-log";

    private RunConfigs(){
        initDefaultRunConfigs();
        mEventFilePath = SOURCE_CODE_PATH + File.separator + mDefauleEventFileName;
    }

    private static void initDefaultRunConfigs(){
        if(!isInited){
            synchronized (RunConfigs.class){
                if(!isInited){
                    URL jarUrl = RunConfigs.class.getProtectionDomain().getCodeSource().getLocation();
                    String jarPath = null;
                    try {
                        jarPath = URLDecoder.decode(jarUrl.getPath(), "utf-8");
                    }catch (UnsupportedEncodingException e){
                        SOURCE_CODE_PATH = "";
                        isInited = true;
                        return;
                    }
                    if(jarPath.endsWith(".jar")){
                        jarPath = jarPath.substring(0, jarPath.lastIndexOf("/") + 1);
                    }
                    File file = new File(jarPath);
                    SOURCE_CODE_PATH = file.getAbsolutePath();
                    isInited = true;
                }
            }
        }
    }
    public static RunConfigs buildRunConfigs(String[] args) throws IllegalArgumentException{
        if(args == null || args.length <= 0){
            return new RunConfigs();
        }
        RunConfigs config = new RunConfigs();
        for (int i = 0; i < args.length; i+=2){
            String indicator = args[i];
            String arg = args.length > i + 1 ? args[i + 1] : "";
            if (indicator.equals(EVENT_FILE_INDICATOR)){
                config.mEventFilePath = arg;
            }else if(indicator.equals(LOG_FILE_INDICATOR)){
                config.mLogFilePath = arg;
            }else {
                throw new  IllegalArgumentException(indicator +"not defined");
            }
        }
        return config;
    }

    public String getEventFilePath(){
        return mEventFilePath;
    }

    public String getLogFilePath(){
        return mLogFilePath;
    }

    public static boolean isValid(RunConfigs configs){
        return configs != null && !TextUtils.isEmpty(configs.mEventFilePath) && !TextUtils.isEmpty(configs.mLogFilePath);
    }

    @Override
    public String toString() {
        return "mEventFilePath = " + mEventFilePath + "\r\n" + "mLogFilePath = " + mLogFilePath;
    }
}
