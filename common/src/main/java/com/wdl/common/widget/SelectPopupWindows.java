package com.wdl.common.widget;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.wdl.common.R;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.widget
 * 创建者：   wdl
 * 创建时间： 2018/8/18 15:53
 * 描述：    popupWindows 的简单封装
 */
@SuppressWarnings("unused")
public class SelectPopupWindows extends PopupWindow implements View.OnClickListener {

    private Button selectedByCamera;
    private Button selectedByGallery;
    private Button cancel;
    private View menuView;
    private PopupWindow popupWindow;

    private OnSelectedListener listener;
    private Context context;
    private LayoutInflater inflater;

    public SelectPopupWindows(Context context) {
        super(context);
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        menuView = inflater.inflate(R.layout.popupwindow_layout, null);
        selectedByCamera = menuView.findViewById(R.id.select_camera);
        selectedByGallery = menuView.findViewById(R.id.select_album);
        cancel = menuView.findViewById(R.id.btn_cancel);
        selectedByCamera.setOnClickListener(this);
        selectedByGallery.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }


    /**
     * 将view添加到popupWindow上并显示
     *
     * @param activity
     */
    public void showPopupWindow(final Activity activity, View view) {
        popupWindow = new PopupWindow(menuView, ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT, true);
        setBackgroundAlpha(activity, 0.0f);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(menuView);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.color.grey_300));
        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(activity, 1.0f);
            }
        });
        //设置显示popupWindow的根布局,以及位置
        popupWindow.showAtLocation(view,
                Gravity.BOTTOM, 0, 0);
    }

    /**
     * 移除PopupWindow
     */
    public void dismissPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param activity
     * @param bgAlpha
     */
    public void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    public void setListener(OnSelectedListener listener) {
        this.listener = listener;
    }

    /**
     * 选择监听接口
     */
    public interface OnSelectedListener {
        void onSelected(View v, int position);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.select_camera) {
            if (null != listener) {
                listener.onSelected(v, 0);
            }

        } else if (i == R.id.select_album) {
            if (null != listener) {
                listener.onSelected(v, 1);
            }

        } else if (i == R.id.btn_cancel) {
            if (null != listener) {
                listener.onSelected(v, 2);
            }

        } else {
        }
    }
}
