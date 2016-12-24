package com.murphywu.eventrecover.evetn_parser;

import com.murphywu.eventrecover.logger.AppLogger;
import com.murphywu.eventrecover.utils.TextUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by murphywu on 2016/12/22.
 */
public class EventParserImpl implements EventParserInterface{


    @Override
    public HashMap<Integer, String> getEvents(String eventJavaPath) {

        if(TextUtils.isEmpty(eventJavaPath)){
            return null;
        }

        File eventFile = new File(eventJavaPath);
        if(!eventFile.isFile()){
            return null;
        }

        StringBuffer strBuff = new StringBuffer("");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(eventFile));
            String line = "";
            while ((line = reader.readLine()) != null){
                strBuff.append(line).append("\r\n");
            }
            return getEventsFromString(strBuff.toString());
        }catch (FileNotFoundException e){
            System.out.println(eventJavaPath + "not exists");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private HashMap<Integer, String> getEventsFromString(String string){
        Pattern pattern = Pattern.compile("\\bpublic\\b\\s+((\\bstatic\\b\\s+\\bfinal\\b)|(\\bfinal\\b\\s+\\bstatic\\b))\\s+\\bint\\b\\s+([a-zA-Z_]\\w*)\\s*=\\s*(\\d+);");
        Matcher matcher = pattern.matcher(string);
        HashMap<Integer, String> eventMaps = new HashMap<>();
        while (matcher.find()){
            String filedName = matcher.group(4);
            int filedValue = Integer.parseInt(matcher.group(5));
            eventMaps.put(filedValue, filedName);
        }
        return eventMaps;
    }
}
