package com.wdl.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.RequestManager;
import com.wdl.common.R;
import com.wdl.factory.model.Author;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.widget
 * 创建者：   wdl
 * 创建时间： 2018/8/1 10:07
 * 描述：    自定义圆形头像
 */
@SuppressWarnings("unused")
public class PortraitView extends CircleImageView {
    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setUp(RequestManager manager, Author author) {
        if (author == null) return;
        setUp(manager, author.getImage());
    }

    public void setUp(RequestManager manager, String url) {
        setUp(manager, R.drawable.default_portrait, url);
    }

    public void setUp(RequestManager manager, int resId, String url) {
        if (url == null) url = "";
        manager.load(url)
                .placeholder(resId)
                .centerCrop()
                .dontAnimate()
                .into(this);
    }


}
