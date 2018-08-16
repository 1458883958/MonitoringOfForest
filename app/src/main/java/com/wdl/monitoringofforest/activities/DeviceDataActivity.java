package com.wdl.monitoringofforest.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.wdl.common.app.ToolbarActivity;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.fragments.device_data.PictureFragment;
import com.wdl.monitoringofforest.fragments.device_data.SensorFragment;
import com.wdl.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@SuppressWarnings("unused")
public class DeviceDataActivity extends ToolbarActivity {

    public static final String P_ID = "P_ID";
    private List<String> mTitle;
    private List<Fragment> fragmentList;
    private int pId;

    @BindView(R.id.mTabLayout)
    TabLayout tabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    /**
     * 显示入口
     *
     * @param context 上下文
     */
    public static void show(Context context, int pId) {
        Intent intent = new Intent(context, DeviceDataActivity.class);
        intent.putExtra(P_ID, pId);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        pId = bundle.getInt(P_ID);
        LogUtils.e("pId"+pId);
        return pId >= 0;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_device_data;
    }

    @Override
    protected void initBefore() {
        super.initBefore();
        mTitle = new ArrayList<>();
        mTitle.add(getResources().getString(R.string.title_picture));
        mTitle.add(getResources().getString(R.string.title_data));
        LogUtils.e("size:"+mTitle.size());
        fragmentList = new ArrayList<>();
        fragmentList.add(PictureFragment.newInstance());
        fragmentList.add(SensorFragment.newInstance());
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("设备"+pId);
        //进行mViewPager的预加载
        mViewPager.setOffscreenPageLimit(fragmentList.size());
        //滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(P_ID,pId);
                fragmentList.get(position).setArguments(bundle);
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //绑定
        tabLayout.setupWithViewPager(mViewPager);
    }
}
