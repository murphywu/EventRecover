package com.murphywu.eventrecover.app;

import com.murphywu.eventrecover.utils.TextUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by murphywu on 2016/12/23.
 */
public class RecoverLogCreater {

    public static final String SUFFIX = ".recover";

    private String mLogFilePath;

    private HashMap<Integer, String> mEventMap;

    public RecoverLogCreater(String logFilePath, HashMap<Integer, String> events){
        mLogFilePath = logFilePath;
        mEventMap = events;
    }

    public int  createRecoverLog(){
        String logFilePath = mLogFilePath.substring(0, mLogFilePath.lastIndexOf(File.separatorChar) + 1);
        String logFileName = mLogFilePath.substring(mLogFilePath.lastIndexOf(File.separatorChar) + 1);
        File recoverLogFile = new File(logFilePath + logFileName + SUFFIX);
        if(recoverLogFile.exists() && !recoverLogFile.delete()){
            System.out.println("delete file " + recoverLogFile.getAbsolutePath() + " failed!");
            return -1;
        }
        OutputStream os = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(mLogFilePath), "UTF-8"));
            os = new FileOutputStream(recoverLogFile);
            String readLine = null;
            String regx = "\\[PlayerEvent] (\\d+)";
            Pattern playerEventPattern = Pattern.compile(regx);
            final int buffSize = 1024 * 1024;
            byte[] buffer = new byte[buffSize];
            int currentCacheLength = 0;
            while ((readLine = reader.readLine()) != null){
                Matcher matcher = playerEventPattern.matcher(readLine);
                if(matcher.find()){
                    int value = Integer.parseInt(matcher.group(1));
                    String name = mEventMap.get(value);
                    if(!TextUtils.isEmpty(name)){
                        readLine = readLine.replaceFirst(regx, "[PlayerEvent] " + name);
                    }
                }
                readLine = readLine + "\r\n";
                byte[] strContentByte = readLine.getBytes("UTF-8");
                if(strContentByte.length + currentCacheLength > buffSize){
                    os.write(buffer, 0 , currentCacheLength);
                    currentCacheLength = 0;
                }
                System.arraycopy(strContentByte, 0, buffer, currentCacheLength, strContentByte.length);
                currentCacheLength += strContentByte.length;
            }
            if(currentCacheLength > 0){
                os.write(buffer, 0 , currentCacheLength);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if(os != null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;

    }
}
