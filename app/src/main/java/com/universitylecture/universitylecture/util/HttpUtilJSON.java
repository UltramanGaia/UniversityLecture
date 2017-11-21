package com.universitylecture.universitylecture.util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by helloworld on 2017/9/12.
 */

public class HttpUtilJSON {

//    public static final String strUrl = "http://172.16.1.76:8080/UniversityLectureServer/";
    public static final String strUrl = "http://118.89.45.18:8080/UniversityLectureServer/";

    public static String doPost(String content,String surl) {
        try {
            URL url = new URL(strUrl + surl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setReadTimeout(8000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-type","application/json;charset=UTF-8");
            Log.e("HttpUtil", "connect : " + content);

            OutputStream os = httpURLConnection.getOutputStream();

            os.write(content.getBytes());
            Log.e("HttpUtil", "writeObject");
            os.flush();
            os.close();


            InputStream input = httpURLConnection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = input.read(data)) != -1)
                outputStream.write(data,0,len);
            Log.e("HttpUtil", "readObject");

            input.close();

            httpURLConnection.disconnect();
            return new String(outputStream.toByteArray());

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
