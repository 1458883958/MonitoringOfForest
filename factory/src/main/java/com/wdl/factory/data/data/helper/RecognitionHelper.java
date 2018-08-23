package com.wdl.factory.data.data.helper;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdl.factory.Factory;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.model.card.RecResult;
import com.wdl.factory.persistence.Account;
import com.wdl.utils.LogUtils;
import com.wdl.utils.baidu.Base64Util;
import com.wdl.utils.baidu.FileUtil;
import com.wdl.utils.baidu.HttpUtil;

import java.net.URLEncoder;
import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.helper
 * 创建者：   wdl
 * 创建时间： 2018/8/22 14:53
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class RecognitionHelper {
    public static void recognition(String path, String accessToken, DataSource.Callback<RecResult> callback) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/plant";
        try {
            byte[] imgData = FileUtil.readFileByBytes(path);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;
            if ("".equals(accessToken)) return;
            String result = HttpUtil.post(url, accessToken, param);
            Gson gson = Factory.getGson();
            RecResult results = gson.fromJson(result,RecResult.class);
            if (callback!=null)
                callback.onLoaded(results);
            LogUtils.e("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
