package com.wdl.factory.utils;

import com.wdl.common.app.Application;
import com.wdl.factory.net.Network;
import com.wdl.utils.HashUtil;
import com.wdl.utils.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author：   wdl
 * time： 2018/9/21 10:39
 * des：    文件缓存 实现文件下载
 * 下载成功后回调相应方法
 */
public class FileCache<Holder> {
    //文件路径
    private File baseDir;
    //后缀
    private String ext;
    //下载监听
    private CacheListener<Holder> listener;
    //最后一个点击的目标holder
    private SoftReference<Holder> holderSoftReference;

    public FileCache(String baseDir, String ext, CacheListener<Holder> listener) {
        this.baseDir = new File(Application.getCacheDirFile(), baseDir);
        this.ext = ext;
        this.listener = listener;
    }

    public void download(Holder holder, String path) {
        //如果路径就是本地缓存路径,那么不需要进行下载
        if (path.startsWith(Application.getCacheDirFile().getAbsolutePath())) {
            this.listener.succeed(holder, new File(path));
            return;
        }

        //构建基础路径
        final File cacheFile = buildCacheFile(path);
        //如果文件存在
        if (cacheFile.exists() && cacheFile.length() > 0) {
            this.listener.succeed(holder, cacheFile);
            return;
        }

        //进行软引用
        holderSoftReference = new SoftReference<>(holder);
        OkHttpClient client = Network.getClient();
        Request request = new Request.Builder()
                .url(path)
                .get()
                .build();
        //发起请求
        Call call = client.newCall(request);
        call.enqueue(new NetCallback(holder,cacheFile));

    }

    //获取最后一次的holder 只能使用一次
    private Holder getLastHolderAndClear() {
        if (holderSoftReference == null) return null;
        else {
            Holder holder = holderSoftReference.get();
            holderSoftReference.clear();
            return holder;
        }
    }

    //下载
    private class NetCallback implements Callback {
        private final SoftReference<Holder> holderSoftReference;
        private final File file;

        public NetCallback(Holder holder, File file) {
            this.holderSoftReference = new SoftReference<>(holder);
            this.file = file;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            Holder holder = holderSoftReference.get();
            //最后一次才是有效的
            if (holder != null && holder == getLastHolderAndClear()) {
                FileCache.this.listener.failed(holder);
            }
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            InputStream inputStream = response.body().byteStream();
            if (inputStream != null && StreamUtil.copy(inputStream, file)) {
                Holder holder = holderSoftReference.get();
                //最后一次才是有效的
                if (holder != null && holder == getLastHolderAndClear()) {
                    FileCache.this.listener.succeed(holder,file);
                }
            } else {
                onFailure(call, null);
            }

        }
    }

    /**
     * 构建一个缓存文件
     *
     * @param path 网络路径
     * @return
     */
    private File buildCacheFile(String path) {
        String key = HashUtil.getMD5String(path);
        return new File(baseDir, key + "." + ext);
    }

    //下载的监听
    public interface CacheListener<Holder> {
        /**
         * 下载成功
         *
         * @param file   本地路径
         * @param holder 绑定的holder
         */
        void succeed(Holder holder, File file);

        void failed(Holder holder);
    }
}
