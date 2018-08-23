package com.wdl.monitoringofforest.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.wdl.factory.Factory;
import com.wdl.factory.persistence.Account;
import com.wdl.monitoringofforest.R;
import com.wdl.utils.LogUtils;

@SuppressWarnings("unused")
public class MapActivity extends AppCompatActivity {

    private LocationClient client = null;
    private MapView mMapView = null;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;

    /**
     * 显示的入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, MapActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new LocationClient(Factory.application());
        client.registerLocationListener(listener);
        setContentView(R.layout.activity_map);
        mMapView = findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);
        requestLocation();
    }
    private void requestLocation() {
        initLocation();
        client.start();
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
        option.setOpenGps(true);
        client.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        client.stop();
        mMapView.onDestroy();
        // 当不需要定位图层时关闭定位图层
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    private BDAbstractLocationListener listener = new BDAbstractLocationListener(){

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            if(bdLocation.getLocType()==BDLocation.TypeGpsLocation||
                    bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
                LogUtils.e("location:"+bdLocation.getAddrStr());
                navigateTo(bdLocation);
                client.stop();
            }
        }
    };

    private void navigateTo(BDLocation location){
        if(isFirstLocate){
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationData = new MyLocationData.Builder();
        locationData.latitude(location.getLatitude());
        locationData.longitude(location.getLongitude());
        MyLocationData myLocationData = locationData.build();
        baiduMap.setMyLocationData(myLocationData);

        mark(location);
    }

    private void mark(BDLocation location) {
        LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
        //构建mark 图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker);

        //构建markerOption,用于在地图上添加marker
        OverlayOptions overlayOptions = new MarkerOptions()
                .position(ll)
                .icon(bitmap);

        //在地图上添加
        baiduMap.addOverlay(overlayOptions);
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (Account.isLogin())
                    MainActivity.show(MapActivity.this);
                else
                    AccountActivity.show(MapActivity.this);
                finish();
                return false;
            }
        });
    }
}
