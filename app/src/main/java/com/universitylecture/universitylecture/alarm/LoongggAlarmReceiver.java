package com.universitylecture.universitylecture.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;

/**
 * Created by fengqingyundan on 2017/10/19.
 */
//接收闹钟到点提醒的广播
public class LoongggAlarmReceiver extends BroadcastReceiver {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        String msg = intent.getStringExtra("msg");
        long intervalMillis = intent.getLongExtra("intervalMillis", 0);
        if (intervalMillis != 0) {
            AlarmManagerUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis,
                    intent);
        }
        int flag = intent.getIntExtra("soundOrVibrator", 0);
        Intent clockIntent = new Intent(context, ClockAlarmActivity.class);
        clockIntent.putExtra("msg", msg);
        clockIntent.putExtra("flag", flag);
        clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(clockIntent);
    }


}