package com.study.studydemo;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 功能：图片缓存到内存中
 * <p>
 * Created by danke on 2017/7/6.
 */

public class ImageCache {
    private LruCache<String, Bitmap> mLruCache; // 图片缓存

    public ImageCache() {
        initLruCache();
    }

    /**
     * 初始化
     */
    public void initLruCache() {
        // 计算出最大可使用的内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024;
        // 取四分之一的可用内存作为缓存
        int cacheSize = maxMemory / 4;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    /**
     * 存储缓存
     * @param url
     * @param bitmap
     */
    public void put(String url, Bitmap bitmap) {
        mLruCache.put(url, bitmap);
    }

    /**
     * 从缓存中获取Bitmap
     * @param url
     * @return
     */
    public Bitmap get(String url) {
        return mLruCache.get(url);
    }
}
