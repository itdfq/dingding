package com.itdfq.dingding.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeExpiredPoolCache {
    //过期时间默认60分钟
    public static long DEFAULTCACHEDMILLIS = 60 * 60 * 1000L;
    //定时清理默认60分钟
    public static long TIMER_MILLIS = 60 * 60 * 1000L;
    /**
     * 对象池
     */
    private static ConcurrentHashMap<String, DataWrapper<?>> dataPool = new ConcurrentHashMap<>();
    /**
     * 对象单例
     */
    private static TimeExpiredPoolCache instance = null;

    private TimeExpiredPoolCache() {
        dataPool = new ConcurrentHashMap<String, DataWrapper<?>>();
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new TimeExpiredPoolCache();
            initTimer();
        }
    }

    public static TimeExpiredPoolCache getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }



    private static void initTimer() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(TimeExpiredPoolCache::clearExpiredCaches,
                0, TIMER_MILLIS, TimeUnit.MILLISECONDS);
    }

    /**
     * 缓存数据
     *
     * @param key          key值
     * @param data         缓存数据
     * @param cachedMillis 过期时间
     * @param dataRenewer  刷新数据
     * @return
     */
    @SuppressWarnings("unchecked")
    public static  <T> T put(String key, T data, long cachedMillis, DataRenewer<T> dataRenewer) throws Exception {
        DataWrapper<T> dataWrapper = (DataWrapper<T>) dataPool.get(key);
        if (data == null && dataRenewer != null) {
            data = dataRenewer.renewData();
        }
        //当重新获取数据为空，直接返回不做put
        if (data == null) {
            return null;
        }
        if (dataWrapper != null) {
            //更新
            dataWrapper.update(data, cachedMillis);
        } else {
            dataWrapper = new DataWrapper<T>(data, cachedMillis);
            dataPool.put(key, dataWrapper);
        }
        return data;
    }

    /**
     * 直接设置缓存值和时间
     *
     * @param key
     * @param data
     * @param cachedMillis
     * @return
     */
    @SuppressWarnings("unchecked")
    public static  <T> T put(String key, T data, long cachedMillis) throws Exception {
        DataWrapper<T> dataWrapper = (DataWrapper<T>) dataPool.get(key);
        if (dataWrapper != null) {
            //更新
            dataWrapper.update(data, cachedMillis);
        } else {
            dataWrapper = new DataWrapper<T>(data, cachedMillis);
            dataPool.put(key, dataWrapper);
        }
        return data;
    }


    /**
     * 获取缓存
     *
     * @param key
     * @param cachedMillis
     * @param dataRenewer
     * @return
     */
    @SuppressWarnings("unchecked")
    public static  <T> T get(String key, long cachedMillis, DataRenewer<T> dataRenewer) throws Exception {
        DataWrapper<T> dataWrapper = (DataWrapper<T>) dataPool.get(key);
        if (dataWrapper != null && !dataWrapper.isExpired()) {
            return dataWrapper.data;
        }
        return put(key, null, cachedMillis, dataRenewer);
    }

    @SuppressWarnings("unchecked")
    public static  <T> T get(String key) throws Exception {
        DataWrapper<T> dataWrapper = (DataWrapper<T>) dataPool.get(key);
        if (dataWrapper != null && !dataWrapper.isExpired()) {
            return dataWrapper.data;
        }
        return null;
    }

    /**
     * 清除缓存
     */
    public static void clear() {
        dataPool.clear();
    }

    /**
     * 删除指定key的value
     */
    public static void remove(String key) {
        dataPool.remove(key);
    }

    /**
     * 数据封装
     */
    private static class DataWrapper<T> {
        /**
         * 数据
         */
        private T data;
        /**
         * 到期时间
         */
        private long expiredTime;
        /**
         * 缓存时间
         */
        private long cachedMillis;

        private DataWrapper(T data, long cachedMillis) {
            this.update(data, cachedMillis);
        }

        public void update(T data, long cachedMillis) {
            this.data = data;
            this.cachedMillis = cachedMillis;
            this.updateExpiredTime();
        }

        public void updateExpiredTime() {
            this.expiredTime = System.currentTimeMillis() + cachedMillis;
        }

        /**
         * 数据是否过期
         *
         * @return
         */
        public boolean isExpired() {
            if (this.expiredTime > 0) {
                return System.currentTimeMillis() > this.expiredTime;
            }
            return true;
        }
    }

    /**
     * 数据构造
     */
    public interface DataRenewer<T> {
        public T renewData();
    }

    /**
     * 清除过期的缓存
     */
    private static void clearExpiredCaches() {
        List<String> expiredKeyList = new LinkedList<String>();

        for (Entry<String, DataWrapper<?>> entry : dataPool.entrySet()) {
            if (entry.getValue().isExpired()) {
                expiredKeyList.add(entry.getKey());
            }
        }
        for (String key : expiredKeyList) {
            dataPool.remove(key);
        }
    }
}
