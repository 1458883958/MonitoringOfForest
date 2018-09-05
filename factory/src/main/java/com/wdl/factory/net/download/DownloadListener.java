package com.wdl.factory.net.download;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.net.download
 * 创建者：   wdl
 * 创建时间： 2018/9/5 15:09
 * 描述：    文件下载的监听接口
 */
@SuppressWarnings("unused")
public interface DownloadListener {
    void onStart();

    void onProgress(int currentLength);

    void onFinish(String path);

    void onFailure(String error);
}
