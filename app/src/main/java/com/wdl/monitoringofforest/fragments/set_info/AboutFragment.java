package com.wdl.monitoringofforest.fragments.set_info;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;

import com.wdl.common.app.Fragment;
import com.wdl.common.widget.AppUpdateProgressDialog;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.FeedbackActivity;

import java.util.Objects;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class AboutFragment extends Fragment {

    private AppUpdateProgressDialog dialog = null;
    private int currentProgress = 0;
    private int maxProgress = 100;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_about;
    }

    //检查更新
    @OnClick(R.id.check_update)
    void checkVersion() {
        currentProgress = 0;
        dialog = new AppUpdateProgressDialog(getContext());
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    Toast.makeText(getContext(), "正在下载请稍后", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            }
        });
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (currentProgress < maxProgress) {
                    try {
                        currentProgress++;
                        Message msg =myHandler.obtainMessage();
                        msg.what = 100;
                        msg.obj = currentProgress;
                        myHandler.sendMessage(msg);
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    int progress = (int) msg.obj;
                    dialog.setProgress(progress);
                    if (100 == progress) {
                        dialog.dismiss();
                        Toast.makeText(getContext(),"下载完成，跳转到安装界面",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @OnClick(R.id.feedback)
    void feedback() {
        FeedbackActivity.show(Objects.requireNonNull(getContext()));
    }

}
