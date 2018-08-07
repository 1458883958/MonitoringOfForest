package com.wdl.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.widget
 * 创建者：   wdl
 * 创建时间： 2018/7/31 14:20
 * 描述：    正方形布局
 */
@SuppressWarnings("unused")
public class SquareLayout extends FrameLayout {
    public SquareLayout(@NonNull Context context) {
        super(context);
    }

    public SquareLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重新绘制
     *
     * @param widthMeasureSpec  宽度
     * @param heightMeasureSpec 高度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //将测量后的高度设置成宽度,宽度不变
        //形成正方形
        //给父类传递的宽高都为宽度
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
