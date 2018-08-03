package com.wdl.common.common;


/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common
 * 创建者：   wdl
 * 创建时间： 2018/7/31 13:30
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class Common {
    //公共且不可变数据
    public interface Constance{
        //电话号码的正则表达式
        String REGEX_PHONE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

        String API_URL = "http://www.xmhhs.top/api/";
    }
}
