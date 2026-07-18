package com.codeying.component.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

@Component
public class CacheUtil {

    private Map<String, Ci> map = new Hashtable<>();

    public Map<String, Ci> all(){
        return map;
    }

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public void expire(String key,int time){
        if(time>0){
            Ci ci = map.get(key);
            if(ci!=null){
                ci.expireTime = DateUtils.addSeconds(new Date(),time);
            }
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    public void del(String ... key){
        if(key!=null&&key.length>0){
            for (String s : key) {
                map.remove(s);
            }
        }
    }

    public <T> T getV(String key){
        Ci ci = map.get(key);
        if(ci == null){
            return null;
        }
        if(ci.expireTime == null || new Date().before(ci.expireTime)){
            return ci.v();
        }
        return null;
    }

    public <T> T remove(String key){
        T t = getV(key);
        if(t!=null){
            map.remove(key);
            return t;
        }
       return null;
    }

    public <T> void set(String key,T value){
        map.put(key,new Ci(value));
    }
    public <T> void set(String key,T value,int seconds){
        map.put(key,new Ci(value,DateUtils.addSeconds(new Date(),seconds)));
    }
    public <T> void set(String key,T value,Date expireTime){
        map.put(key,new Ci(value,expireTime));
    }

    /**
     * cache item
     */
    public class Ci{
        public Object v;
        public Date expireTime;
        public Ci(Object v){
            this.v = v;
            this.expireTime = null;
        }
        public Ci(Object v,Date expireTime){
            this.v = v;
            this.expireTime = expireTime;
        }
        public <T> T v(){
            return (T)v;
        }
    }
}
