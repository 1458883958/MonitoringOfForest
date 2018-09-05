package com.wdl.factory.net.download;

import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;
import com.wdl.utils.FileUtils;
import com.wdl.utils.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.net.download
 * 创建者：   wdl
 * 创建时间： 2018/9/5 15:38
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class DownLoadHelper {
    private static final String PATH_CHALLENGE = Environment.getExternalStorageDirectory() + "/DownloadFile";
    //视频下载相关
    private Call<ResponseBody> mCall;
    private File mFile;
    private Thread mThread;
    private String mPath; //下载到本地的视频路径
    private RemoteService service;
    public DownLoadHelper(){
           service = Network.remoteService();
    }

    public void downloadFile(String url, final DownloadListener listener) {
        //通过Url得到保存到本地的文件名
        String name = url;
        if (FileUtils.createOrExistsDir(PATH_CHALLENGE)) {
            int i = name.lastIndexOf('/');//一定是找最后一个'/'出现的位置
            if (i != -1) {
                //name = SystemClock.currentThreadTimeMillis()+name.substring(i);
                name = name.substring(i);
                mPath = PATH_CHALLENGE +
                        name;
            }
        }
        if (TextUtils.isEmpty(mPath)) {
            return;
        }
        //建立一个文件
        mFile = new File(mPath);
        if (!FileUtils.isFileExists(mFile)&&FileUtils.createOrExistsFile(mFile)){
            if (service==null)return;
            mCall = service.downloadFile(url);
            mCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                    mThread = new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            //保存到本地
                            writeFile2Disk(response,mFile,listener);
                        }
                    };
                    mThread.start();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    listener.onFailure("网络错误!");
                }
            });
        }else {
            listener.onFinish(mPath);
        }
    }

    private void writeFile2Disk(Response<ResponseBody> response, File mFile, DownloadListener listener) {
        listener.onStart();
        long currentLength = 0;
        OutputStream os = null;
        ResponseBody body = response.body();
        if (body==null){
            listener.onFailure("资源错误！");
            return;
        }

        InputStream is = body.byteStream();
        long totalLength = body.contentLength();

        try{
            os = new FileOutputStream(mFile);
            int len;
            byte[] buff = new byte[1024];
            while((len = is.read(buff))!=-1){
                os.write(buff,0,len);
                currentLength += len;
               //LogUtils.e("当前下载进度为:"+currentLength);
                listener.onProgress((int)(100 * currentLength / totalLength));
                if ((int) (100 * currentLength / totalLength) == 100){
                    listener.onFinish(mPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
