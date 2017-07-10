package com.universitylecture.universitylecture.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by fengqingyundan on 2017/6/26.
 */

public class BaseActivity extends AppCompatActivity {

    private ForceOfflineReceiver receiver;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    protected void onResume(){
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.universitylecture.universitylecture.FORCE_OFFLINE");
        receiver = new ForceOfflineReceiver();
        registerReceiver(receiver,intentFilter);
    }

    protected void onPause(){
        super.onPause();
        if( receiver != null ){
            unregisterReceiver(receiver);
            receiver = null;
        }
    }
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    class ForceOfflineReceiver extends BroadcastReceiver {
        public void onReceive(final Context context , Intent intent ){
            //设置弹窗
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("警告");
            builder.setMessage("确认退出？");
            builder.setCancelable(false);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCollector.finishAll();
                    //Intent intent1 = new Intent(context , );
                    //context.startActivity(intent);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
    }
}
