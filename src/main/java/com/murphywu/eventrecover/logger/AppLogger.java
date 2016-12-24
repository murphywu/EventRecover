package com.murphywu.eventrecover.logger;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by murphywu on 2016/12/22.
 */
public class AppLogger {

    private static volatile Logger _INSTANCE;

    private static final String LOG_NAME = "AppLogger";

    private static final int LOG_LEVEL_V = 0;
    private static final int LOG_LEVEL_D = 1;
    private static final int LOG_LEVEL_I = 2;
    private static final int LOG_LEVEL_W = 3;
    private static final int LOG_LEVEL_E = 4;
    private static final int LOG_LEVEL_A = 5;

    private static final int LOG_LEVEL = LOG_LEVEL_D;

    private AppLogger(){
    }

    public static void init(){
        if(_INSTANCE == null){
            synchronized (AppLogger.class){
                if(_INSTANCE == null){
                    _INSTANCE = Logger.getLogger(LOG_NAME);
                    ConsoleHandler handler = new ConsoleHandler();
                    handler.setFormatter(new SimpleFormatter());
                    _INSTANCE.addHandler(new ConsoleHandler());
                }
            }
        }
    }

    public static void d(String tag, String message){
    }

    private static void log(String tag, String message){

    }

}
