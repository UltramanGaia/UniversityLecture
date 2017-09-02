package com.universitylecture.universitylecture.util;

import android.util.Log;

import com.universitylecture.universitylecture.pojo.Lecture;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by helloworld on 2017/6/3.
 */

public class HttpUtil {

    public static final String strUrl = "http://192.168.1.132:8080/UniversityLectureServer/";
    public static Object doPost(Object object,String surl) {
        if (object instanceof Lecture)
            Log.e("lecture", "doPost: "+((Lecture)object).getInstitute());
        try {
            URL url = new URL(strUrl + surl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setReadTimeout(8000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-type","application/x-java-serialized-object");
            Log.d("HttpUtil", "connect");

            OutputStream os = httpURLConnection.getOutputStream();
            ObjectOutputStream outObj = new ObjectOutputStream(os);
            outObj.writeObject(object);
            Log.e("HttpUtil", "writeObject");
            outObj.flush();
            outObj.close();
            os.close();

            InputStream input = httpURLConnection.getInputStream();
            ObjectInputStream inObj = new ObjectInputStream(input);
            Object readObj = inObj.readObject();
            Log.e("HttpUtil", "readObject");

            inObj.close();
            input.close();

            httpURLConnection.disconnect();
            return readObj;

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
