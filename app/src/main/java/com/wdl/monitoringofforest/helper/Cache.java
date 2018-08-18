package com.wdl.monitoringofforest.helper;

import android.content.Context;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.signature.EmptySignature;

import java.io.File;
import java.io.IOException;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.monitoringofforest.helper
 * 创建者：   wdl
 * 创建时间： 2018/8/18 20:16
 * 描述：    TODO
 */
public class Cache {
    public static File getCacheFile(Context context, String url) {
        OriginalKey originalKey = new OriginalKey(url, EmptySignature.obtain());
        SafeKeyGenerator safeKeyGenerator = new SafeKeyGenerator();
        String safeKey = safeKeyGenerator.getSafeKey(originalKey);
        try {
            DiskLruCache diskLruCache = DiskLruCache.open(new File(context.getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR), 1, 1, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
            DiskLruCache.Value value = diskLruCache.get(safeKey);
            if (value != null) {
                return value.getFile(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
