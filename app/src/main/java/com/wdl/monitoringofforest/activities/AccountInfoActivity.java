package com.wdl.monitoringofforest.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.wdl.common.app.Application;
import com.wdl.common.app.PresenterToolbarActivity;
import com.wdl.common.widget.PortraitView;
import com.wdl.common.widget.SelectPopupWindows;
import com.wdl.factory.Factory;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.net.UploadHelper;
import com.wdl.factory.presenter.account.UpdateContract;
import com.wdl.factory.presenter.account.UpdatePresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.media.GalleryFragment;
import com.wdl.utils.LogUtils;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressWarnings("unused")
public class AccountInfoActivity extends PresenterToolbarActivity<UpdateContract.Presenter> implements
        UpdateContract.View, SelectPopupWindows.OnSelectedListener {

    private static final int REQUEST_CAMERA = 0x01;
    private final int type_image = 4;
    private final int type_address = 5;
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
    private LocationClient locationClient;

    private Uri imageUri;

    @OnClick(R.id.update_portrait)
    void portrait() {
        popupWindows.showPopupWindow(this, linearLayout);
    }

    @OnClick(R.id.update_address)
    void address() {
        getLocation();
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
        locationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        locationClient.registerLocationListener(listener);
    }

    private BDAbstractLocationListener listener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            final String addr = bdLocation.getAddrStr();    //获取详细地址信息
            LogUtils.e(addr);
            if (TextUtils.isEmpty(addr)) return;
            //经度
            double lat = bdLocation.getLatitude();
            //维度
            double lot = bdLocation.getLongitude();
            LogUtils.e("lat:" + lat + " lot:" + lot);
            mPresenter.update(type_address, addr, lat, lot);
            locationClient.stop();
        }
    };

    private void getLocation() {
        initLocation();
        locationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setIsNeedAltitude(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setScanSpan(5000);
        option.setCoorType("bd09ll");
        //请求间隔
        //option.setOpenGps(true);
        locationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
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
                selectByCamera();
                popupWindows.dismissPopupWindow();
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

    private void selectByCamera() {
        //设置拍照意图
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //添加访问权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //保存的位置
        imageUri = getImageUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private Uri getImageUri() {
        File outputImg = new File(Environment.getExternalStorageDirectory(), "output.jpg");

        try {
            if (outputImg.exists()) outputImg.delete();
            outputImg.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT > 23) {
            return FileProvider
                    .getUriForFile(this, getPackageName() + ".fileprovider", outputImg);
        } else
            return Uri.fromFile(outputImg);
    }

    private void selectByGallery() {
        new GalleryFragment()
                .setListener(new GalleryFragment.OnSelectImageListener() {
                    @Override
                    public void onSelect(String path) {
                        uCrop(Uri.fromFile(new File(path)));
                    }
                }).show(getSupportFragmentManager(), AccountInfoActivity.class.getName());
    }

    private void uCrop(Uri uri) {
        UCrop.Options options = new UCrop.Options();
        //设置图片处理的格式
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        //设置图片压缩后的精度
        options.setCompressionQuality(96);
        //获取头像的缓存地址
        File targetPath = Application.getPortraitTmpFile();
        UCrop.of(uri, Uri.fromFile(targetPath))
                .withAspectRatio(1, 1)  //比例
                .withMaxResultSize(520, 520) //返回的像素
                .withOptions(options)
                .start(AccountInfoActivity.this);
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
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CAMERA) {
            if (null != imageUri) {
                uCrop(imageUri);
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
        path = resultUrl.getEncodedPath();
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
            mAddress.setText(user.getuIpaddress());
    }
}
