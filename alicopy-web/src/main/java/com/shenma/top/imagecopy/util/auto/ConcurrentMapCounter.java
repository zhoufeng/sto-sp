package com.shenma.top.imagecopy.util.auto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ConcurrentMapCounter {
    private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();

    public  Integer increment(String s) {
        //如果s不存在，则放入key->1，否则执行replace方法
        if(!(map.putIfAbsent(s, 1) == null)){
            boolean flag = false;
            while(!flag) {
                flag = map.replace(s, map.get(s), map.get(s) + 1);
            }
        }
        return map.get(s);
    }

    public Map<String, Integer> getMap() {
        return map;
    }
}