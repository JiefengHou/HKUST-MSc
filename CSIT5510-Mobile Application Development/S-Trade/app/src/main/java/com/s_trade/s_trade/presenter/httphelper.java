/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade.presenter;

import java.io.IOException;
import java.io.InputStream;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.xmlpull.v1.XmlPullParser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

import com.alibaba.fastjson.JSON;
import com.s_trade.s_trade.model.*;
/**
 * Created by saber on 2016/11/17.
 */

public class httphelper {

    public static String url = "http://192.168.43.60/strade.asmx/";
    public static String picurl = "http://192.168.43.60/getimage.aspx?itemid=";
    public static String uploadurl = "http://192.168.43.60/newitem.aspx";
    public static String allpicurl = "http://192.168.43.60/uploadfile/";

    @Nullable
    public static List<Bitmap> getallimg(List<String> urls) throws IOException {
        String tempurl = "";
        Response response;
        List<Bitmap> bm=new ArrayList<Bitmap>();
        try {
            OkHttpClient client = new OkHttpClient();
            for (int i = 0; i < urls.size(); i++) {
                tempurl = allpicurl + urls.get(i);

                Request request = new Request.Builder().url(tempurl).build();
                response = client.newCall(request).execute();
                InputStream is = response.body().byteStream();
                bm.add( BitmapFactory.decodeStream(is));

            }
            return bm;

        }
            catch (Exception e) {
            return null;
        }
    }


    @Nullable
    public static Bitmap getimage(String itemid) throws IOException
    {
        String tempurl=picurl;
        tempurl+=itemid;
        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder().url(tempurl).build();
            Response response = client.newCall(request).execute();
            InputStream is = response.body().byteStream();
            Bitmap bm = BitmapFactory.decodeStream(is);
            response.close();
            return bm;
        } catch (Exception e) {
            return null;
        }
    }

 public static int getint(String url,FormBody body)throws IOException {
     OkHttpClient client = new OkHttpClient();
                 //body = new FormBody.Builder()
//                .add("category", "")
//                .add("sort","")
//                .build();

     Request request = new Request.Builder()
             .addHeader("Content-Type", "application/x-www-form-urlencoded")
             .url(url)
             .post(body)
             .build();

     Response response = client.newCall(request).execute();
     if (response.isSuccessful()) {
         InputStream inputStream= response.body().byteStream();
         try {
             String result=  parseintxml(inputStream);
             response.close();
             return Integer.parseInt(result);

         }
         catch (Exception e)
         {
             return 0;
         }


     } else  {
         throw new IOException("Unexpected code " + response);
     }
 }

    @Nullable
    public static String post(String url, FormBody body) throws IOException {
        OkHttpClient client = new OkHttpClient();
//                 body = new FormBody.Builder()
//                .add("category", "")
//                .add("sort","")
//                .build();

        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
           InputStream inputStream= response.body().byteStream();
            try {
                String result=  parseResponseXML(inputStream);
                response.close();
                return result;
            }
            catch (Exception e)
            {
                return null;
            }


        } else  {
            throw new IOException("Unexpected code " + response);
        }
    }



    public  static String   inputStream2String   (InputStream in)   throws IOException {
        StringBuffer   out   =   new   StringBuffer();
        byte[]   b   =   new   byte[4096];
        for   (int   n;   (n   =   in.read(b))   !=   -1;)   {
            out.append(new   String(b,   0,   n));
        }
        return   out.toString();
    }

    private static String parseResponseXML(InputStream inStream) throws Exception
    {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inStream, "UTF-8");
        int eventType = parser.getEventType();// 产生第一个事件
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            // 只要不是文档结束事件
            switch (eventType)
            {
                case XmlPullParser.START_TAG:
                    String name = parser.getName();// 获取解析器当前指向的元素的名称
                    if ("string".equals(name))
                    {
                        return parser.nextText();
                    }
                    break;
            }
            eventType = parser.next();
        }
        return null;
    }
    private static String parseintxml(InputStream inStream) throws Exception
    {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inStream, "UTF-8");
        int eventType = parser.getEventType();// 产生第一个事件
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            // 只要不是文档结束事件
            switch (eventType)
            {
                case XmlPullParser.START_TAG:
                    String name = parser.getName();// 获取解析器当前指向的元素的名称
                    if ("int".equals(name))
                    {
                        return parser.nextText();
                    }
                    break;
            }
            eventType = parser.next();
        }
        return null;
    }


}
