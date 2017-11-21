package com.universitylecture.universitylecture.view.map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//状态栏不会被隐藏但activity布局会扩展到状态栏所在位置
                    // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//导航栏不会被隐藏但activity布局会扩展到导航栏所在位置
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //window.setNavigationBarColor(Color.TRANSPARENT);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            //半透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //半透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        Intent intent = getIntent();
        destination.setLatitude(intent.getDoubleExtra("latitude",0.0));
        destination.setLongitude(intent.getDoubleExtra("longitude",0.0));

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
