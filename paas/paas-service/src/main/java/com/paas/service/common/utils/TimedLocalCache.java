package com.paas.service.common.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * create by sumerian on 2020/6/29
 * <p>
 * desc:使用weakedhashmap实现一个具有过期时间的本地缓存。
 **/
public class TimedLocalCache<K, V> extends WeakHashMap<K, TimedLocalCache.TimedLocalCachedDataDTO<V>> {
    //过期时间
    private final long timeout;
    //将过期时间放入构造函数中去
    public TimedLocalCache(long timeout) {
        this.timeout = timeout;
    }

    public TimedLocalCache() {
        //设置默认5分钟的过期时间
        this.timeout = TimeUnit.SECONDS.toSeconds(5);
    }

    public long getTimeout() {
        return timeout;
    }

    public TimedLocalCache.TimedLocalCachedDataDTO<V> putCache(K key, V value) {
        TimedLocalCache.TimedLocalCachedDataDTO<V> dataDTO = new TimedLocalCachedDataDTO<V>(value);
        return super.put(key, dataDTO);
    }

    public V getCache(K key) {
        TimedLocalCachedDataDTO<V> dataDTO = super.get(key);
        if (dataDTO == null) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime time = dataDTO.getTime();
        long seconds = Duration.between(time, now).getSeconds();
        if (seconds > getTimeout()) {
            //如果过期，则删除
            remove(key);
            return null;
        }
        return dataDTO.getData();
    }


    public static class TimedLocalCachedDataDTO<V> {
        private final LocalDateTime time;
        private final V data;

        public TimedLocalCachedDataDTO(V data) {
            this.time = LocalDateTime.now();
            this.data = data;
        }
        // getters

        public LocalDateTime getTime() {
            return time;
        }

        public V getData() {
            return data;
        }
    }

    public static void main(String[] args) {
        TimedLocalCache<String, TimedLocalCache.TimedLocalCachedDataDTO<String>> timedLocalCache = new TimedLocalCache();
        timedLocalCache.putCache("name",new TimedLocalCachedDataDTO<String>("jurongbing"));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TimedLocalCachedDataDTO<String> name = timedLocalCache.getCache("name");
        System.out.println(name==null?"key已过期":name.getData());
    }


}
