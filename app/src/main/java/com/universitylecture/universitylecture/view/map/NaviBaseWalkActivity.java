package com.universitylecture.universitylecture.view.map;

import android.os.Bundle;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.model.NaviLatLng;
import com.universitylecture.universitylecture.R;

public class NaviBaseWalkActivity extends NaviBaseActivity {
    private NaviLatLng source = new NaviLatLng( 23.050546405028143,113.40182036161423);
    private NaviLatLng destination = new NaviLatLng( 23.04584466695405,113.4075616300106);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Intent intent = getIntent();
        //destination.setLatitude(intent.getDoubleExtra("positionx", 0));
        //destination.setLongitude(intent.getDoubleExtra("positiony", 0));

        setContentView(R.layout.activity_navi_base_walk);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);


    }

    @Override
    public void onInitNaviSuccess() {
        mAMapNavi.calculateWalkRoute(source, destination);
    }
}
