package com.murphywu.eventrecover.evetn_parser;

import java.util.HashMap;

/**
 * Created by murphywu on 2016/12/22.
 */
public interface EventParserInterface {

    /**
     * 解析Event.java文件，将该java文件中定义的所有静态整型域解析到map中，map的key是域的值，map的value是域的名字
     * @param eventJavaPath Event.java 文件所在的目录
     * @return 不解释
     */
    HashMap<Integer, String> getEvents(String eventJavaPath);

}
