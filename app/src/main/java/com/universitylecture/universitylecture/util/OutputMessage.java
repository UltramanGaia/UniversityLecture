package com.universitylecture.universitylecture.util;

import android.os.Looper;
import android.widget.Toast;

/**
 * Created by helloworld on 2017/11/12.
 */

public class OutputMessage {
    public static void outputMessage(String message) {
        Looper.prepare();
        Toast.makeText(MyApplication.getContext(),message,Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}
