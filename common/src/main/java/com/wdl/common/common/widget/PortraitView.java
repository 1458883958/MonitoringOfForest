package com.wdl.common.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.common.widget
 * 创建者：   wdl
 * 创建时间： 2018/8/1 10:07
 * 描述：    自定义圆形头像
 */
@SuppressWarnings("unused")
public class PortraitView extends CircleImageView{
    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
