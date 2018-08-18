package com.wdl.monitoringofforest.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wdl.common.app.Application;
import com.wdl.common.app.PresenterToolbarActivity;
import com.wdl.common.app.ToolbarActivity;
import com.wdl.common.widget.PortraitView;
import com.wdl.common.widget.SelectPopupWindows;
import com.wdl.factory.Factory;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.net.UploadHelper;
import com.wdl.factory.presenter.account.UpdateContract;
import com.wdl.factory.presenter.account.UpdatePresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.main.DeviceFragment;
import com.wdl.monitoringofforest.media.GalleryFragment;
import com.wdl.utils.LogUtils;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressWarnings("unused")
public class AccountInfoActivity extends PresenterToolbarActivity<UpdateContract.Presenter> implements
        UpdateContract.View, SelectPopupWindows.OnSelectedListener {

    private final int type_image = 4;
    private final int type_address =5;
    private SelectPopupWindows popupWindows;
    @BindView(R.id.mPortrait)
    PortraitView mPortrait;
    @BindView(R.id.mName)
    TextView mName;
    @BindView(R.id.mMail)
    TextView mMail;
    @BindView(R.id.mAddress)
    TextView mAddress;
    @BindView(R.id.linear)
    LinearLayout linearLayout;
    private String path;

    @OnClick(R.id.update_portrait)
    void portrait() {
        popupWindows.showPopupWindow(this,linearLayout);
    }

    @OnClick(R.id.update_address)
    void address() {
        String address = mAddress.getText().toString().trim();
        mPresenter.update(type_address,address);
    }


    @Override
    protected void onResume() {
        super.onResume();
        UserDb userDb = mPresenter.getUser();
        if (userDb == null) return;
        init(userDb);
    }

    private void init(UserDb userDb) {
        mPortrait.setUp(Glide.with(this), userDb);
        String name = userDb.getAlias();
        String mail = userDb.getMail();
        String address = userDb.getAddress();
        if (!TextUtils.isEmpty(name)) mName.setText(name);
        if (!TextUtils.isEmpty(mail)) mMail.setText(mail);
        if (!TextUtils.isEmpty(address)) mAddress.setText(address);
    }


    /**
     * 显示入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountInfoActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_acount_info;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle(R.string.label_account_info);
        popupWindows = new SelectPopupWindows(this);
        popupWindows.setListener(this);
    }

    @OnClick(R.id.update_name)
    void updateName() {
        UpdateInfoActivity.show(this, UpdateInfoActivity.TYPE_UPDATE_NAME);
    }

    @OnClick(R.id.update_mail)
    void updateMail() {
        UpdateInfoActivity.show(this, UpdateInfoActivity.TYPE_UPDATE_MAIL);
    }

    @Override
    protected UpdateContract.Presenter initPresenter() {
        return new UpdatePresenter(this);
    }

    @Override
    public void onSelected(View v, int position) {
        switch (position) {
            case 0:
                //TODO 拍照选取
                break;
            case 1:
                selectByGallery();
                popupWindows.dismissPopupWindow();
                break;
            case 2:
                popupWindows.dismissPopupWindow();
                break;
            default:
        }
    }

    private void selectByGallery() {
        new GalleryFragment()
                .setListener(new GalleryFragment.OnSelectImageListener() {
                    @Override
                    public void onSelect(String path) {
                        UCrop.Options options = new UCrop.Options();
                        //设置图片处理的格式
                        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                        //设置图片压缩后的精度
                        options.setCompressionQuality(96);
                        //获取头像的缓存地址
                        File targetPath = Application.getPortraitTmpFile();
                        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(targetPath))
                                .withAspectRatio(1, 1)  //比例
                                .withMaxResultSize(520, 520) //返回的像素
                                .withOptions(options)
                                .start(AccountInfoActivity.this);

                    }
                }).show(getSupportFragmentManager(), AccountInfoActivity.class.getName());
    }

    /**
     * 收到从activity传过来的回调,取出值进行图片加载
     * 判断是否能够处理
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        Intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUrl = UCrop.getOutput(data);
            if (resultUrl != null) {
                loadPortrait(resultUrl);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**
     * 头像上传
     *
     * @param resultUrl Uri
     */
    private void loadPortrait(Uri resultUrl) {
        path = resultUrl.getPath();
        Factory.runOnAsy(new Runnable() {
            @Override
            public void run() {
                //网络图片url
                String url = UploadHelper.uploadImage(path);
                mPresenter.update(type_image, url);
            }
        });
    }

    @Override
    public void succeed(User user) {
        if (!TextUtils.isEmpty(user.getuImagepath()))
            mPortrait.setUp(Glide.with(this), user.getuImagepath());
        if (!TextUtils.isEmpty(user.getuIpaddress()))
            mAddress.setText(user.getuImagepath());
    }
}
