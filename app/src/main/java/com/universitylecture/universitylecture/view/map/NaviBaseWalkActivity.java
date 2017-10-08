package com.universitylecture.universitylecture.view.map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.model.NaviLatLng;
import com.universitylecture.universitylecture.R;

public class NaviBaseWalkActivity extends NaviBaseActivity implements
        LocationSource,AMapLocationListener {
    private NaviLatLng source = new NaviLatLng( 23.050546405028143,113.40182036161423);
    private NaviLatLng destination = new NaviLatLng( 23.04584466695405,113.4075616300106);

    //定位需要的声明
    private AMapLocationClient mLocationClient = null;//定位发起端
    private AMapLocationClientOption mLocationOption = null;//定位参数
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        destination.setLatitude(Double.parseDouble(intent.getStringExtra("latitude")));
        destination.setLongitude(Double.parseDouble(intent.getStringExtra("longitude")));

        setContentView(R.layout.activity_navi_base_walk);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);

        //initLocation();//确定自己的位置
    }

    public void activate(OnLocationChangedListener onLocationChangedListener) {
        Log.d("tests", "activate: ");
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mLocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 只是为了获取当前位置，所以设置为单次定位
            //mLocationOption.setOnceLocation(true);
            // 设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    public void onLocationChanged(AMapLocation amapLocation) {
        Log.d("tests", "onLocationChanged: ");
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                source.setLatitude(amapLocation.getLatitude());
                source.setLongitude(amapLocation.getLongitude());
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": "
                        + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }
    @Override
    public void onInitNaviSuccess() {
        mAMapNavi.calculateWalkRoute(source, destination);
    }
}
