package com.murphywu.eventrecover.app;

import com.murphywu.eventrecover.evetn_parser.EventParserImpl;
import com.murphywu.eventrecover.evetn_parser.EventParserInterface;

import java.util.HashMap;

/**
 * Created by murphywu on 2016/12/22.
 */
public class LogEventRecover {

    public static void main(String[] args){

        RunConfigs configs;
        try {
            configs = RunConfigs.buildRunConfigs(args);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return;
        }
        if(RunConfigs.isValid(configs)){
            EventParserInterface eventParser = new EventParserImpl();
            HashMap<Integer, String> events =  eventParser.getEvents(configs.getEventFilePath());
            if(events == null || events.isEmpty()){
                return;
            }
            RecoverLogCreater creater = new RecoverLogCreater(configs.getLogFilePath(), events);
            if(creater.createRecoverLog() == 0){
                System.out.println("SUCCESS");
            }else {
                System.out.println("FAILED");
            }
        }else {
            System.out.println("input arguments invaild, please check your input");
        }

    }
}
