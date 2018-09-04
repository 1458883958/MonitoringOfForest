package com.wdl.monitoringofforest.activities;

import android.content.Context;
import android.content.Intent;
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
import com.wdl.common.app.PresenterActivity;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.presenter.pi.MapContract;
import com.wdl.factory.presenter.pi.MapPresenter;
import com.wdl.monitoringofforest.R;

import java.util.List;

import butterknife.BindView;

@SuppressWarnings("unused")
public class MapActivity extends PresenterActivity<MapContract.Presenter> implements MapContract.View {

    @BindView(R.id.bmapView)
    MapView mMapView;
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
    protected void initWidget() {
        super.initWidget();
        baiduMap = mMapView.getMap();
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                baiduMap.setOnMarkerClickListener(null);
                MainActivity.show(MapActivity.this);
                finish();
                return false;
            }
        });
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.query();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
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


    private void navigateTo(PiDb db){
        if(isFirstLocate){
            LatLng ll = new LatLng(db.getLatitude(),db.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationData = new MyLocationData.Builder();
        locationData.latitude(db.getLatitude());
        locationData.longitude(db.getLongitude());
        MyLocationData myLocationData = locationData.build();
        baiduMap.setMyLocationData(myLocationData);

    }

    private void marks(List<PiDb> dbs) {
        for (PiDb db : dbs) {
            mark(db);
        }
    }

    private void mark(PiDb db) {
        LatLng ll = new LatLng(db.getLatitude(),db.getLongitude());
        //构建mark 图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.marker);

        //构建markerOption,用于在地图上添加marker
        OverlayOptions overlayOptions = new MarkerOptions()
                .position(ll)
                .icon(bitmap);

        //在地图上添加
        baiduMap.addOverlay(overlayOptions);
    }

    @Override
    protected MapContract.Presenter initPresenter() {
        return new MapPresenter(this);
    }



    @Override
    public void result(List<PiDb> dbs) {
        //移动到第一个设备位置
        navigateTo(dbs.get(0));
        //标记
        marks(dbs);

    }

    @Override
    public void failed() {
       showToast(R.string.data_un_device);
       MainActivity.show(this);
       finish();
    }
}
