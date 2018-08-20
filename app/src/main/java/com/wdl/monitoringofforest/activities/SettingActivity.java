package com.wdl.monitoringofforest.activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.wdl.common.app.PresenterToolbarActivity;
import com.wdl.common.app.ToolbarActivity;
import com.wdl.common.widget.Progress;
import com.wdl.factory.presenter.set.SetContract;
import com.wdl.factory.presenter.set.SetPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.utils.DataCleanManager;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressWarnings("unused")
public class SettingActivity extends PresenterToolbarActivity<SetContract.Presenter>
    implements SetContract.View{

    private ProgressDialog dialog = null;
    @BindView(R.id.cache)
    TextView mCache;

    @OnClick(R.id.clean_cache)
    void clean() {
        dialog.show();

        mPresenter.cleanCache();

    }

    /**
     * 显示入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle(R.string.label_account_setting);
        // /data/data/com.wdl.monio.../cache
        File file = new File(this.getCacheDir().getPath());
        try {
            mCache.setText(DataCleanManager.getCacheSize(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog = new ProgressDialog(this);
        dialog.setTitle(R.string.title_dealing);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected SetContract.Presenter initPresenter() {
        return new SetPresenter(this);
    }

    @Override
    public void showError(int res) {
        super.showError(res);
        dialog.dismiss();
    }

    @Override
    public void cleanSucceed() {
        mCache.setText(R.string.label_default_cache);
        dialog.dismiss();
    }
}
