package com.wdl.monitoringofforest.fragments.set_info;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.common.widget.AppUpdateProgressDialog;
import com.wdl.factory.model.card.Version;
import com.wdl.factory.net.download.DownLoadHelper;
import com.wdl.factory.net.download.DownloadListener;
import com.wdl.factory.presenter.version.AppVersionContract;
import com.wdl.factory.presenter.version.AppVersionPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.FeedbackActivity;
import com.wdl.monitoringofforest.activities.NoticeActivity;
import com.wdl.utils.LogUtils;

import java.io.File;
import java.util.Objects;

import butterknife.OnClick;

import static com.wdl.common.common.Common.Constance.APP_URL;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class AboutFragment extends PresenterFragment<AppVersionContract.Presenter>
        implements AppVersionContract.View {

    private AppUpdateProgressDialog dialogA = null;
    private int currentProgress = 0;
    private int maxProgress = 100;
    private String mPath;

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

    @OnClick(R.id.his_notice)
    void notice(){
        NoticeActivity.show(Objects.requireNonNull(getContext()));
    }

    //检查更新
    @OnClick(R.id.check_update)
    void checkVersion() {
        mPresenter.checkVersion();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (currentProgress < maxProgress) {
//                    try {
//                        currentProgress++;
//                        Message msg =myHandler.obtainMessage();
//                        msg.what = 100;
//                        msg.obj = currentProgress;
//                        myHandler.sendMessage(msg);
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }

//    @SuppressLint("HandlerLeak")
//    Handler myHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 100:
//                    int progress = (int) msg.obj;
//                    dialog.setProgress(progress);
//                    if (100 == progress) {
//                        dialog.dismiss();
//                        Toast.makeText(getContext(),"下载完成，跳转到安装界面",Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//            }
//        }
//    };

    @OnClick(R.id.feedback)
    void feedback() {
        FeedbackActivity.show(Objects.requireNonNull(getContext()));
    }

    @Override
    protected AppVersionContract.Presenter initPresenter() {
        return new AppVersionPresenter(this);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        dialogA = new AppUpdateProgressDialog(getContext());
        dialogA.setOnKeyListener(new DialogInterface.OnKeyListener() {
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
    }

    /**
     * 检测到有新版本,弹窗提示
     *
     * @param version json
     */
    @Override
    public void showDialog(Version version) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.label_new_version)
                .setCancelable(false)
                .setNegativeButton(R.string.label_update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        DownLoadHelper helper = new DownLoadHelper();
                        helper.downloadFile(APP_URL, new DownloadListener() {
                            @Override
                            public void onStart() {
                                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        currentProgress = 0;
                                        dialogA.show();
                                    }
                                });

                            }

                            @Override
                            public void onProgress(final int currentLength) {
                                LogUtils.e("进度:" + currentLength);
                                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialogA.setProgress(currentLength);
                                    }
                                });
                            }

                            @Override
                            public void onFinish(String path) {
                                mPath = path;
                                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dialogA.dismiss();
                                    }
                                });
                                LogUtils.e("path:" + mPath);
                                openAPK(mPath);
                            }

                            @Override
                            public void onFailure(String error) {

                            }
                        });
                        dialog.cancel();
                    }

                })
                .setPositiveButton(R.string.label_update_later, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setMessage(version.getVAppcontent())
                .show();
    }

    /**
     * 安装apk
     *
     * @param fileSavePath 文件路径
     */
    private void openAPK(String fileSavePath) {
        File file = new File(Uri.parse(fileSavePath).getPath());
        String filePath = file.getAbsolutePath();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断版本大于等于7.0
            // 生成文件的uri，，
            // 注意 下面参数com.wdl.monitoringofforest.fileprovider 为apk的包名加上.fileprovider，
            data = FileProvider.getUriForFile(Objects.requireNonNull(getContext()),
                    "com.wdl.monitoringofforest.fileprovider", new File(filePath));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);// 给目标应用一个临时授权
        } else {
            data = Uri.fromFile(file);
        }

        intent.setDataAndType(data, "application/vnd.android.package-archive");
        startActivity(intent);
    }

}
