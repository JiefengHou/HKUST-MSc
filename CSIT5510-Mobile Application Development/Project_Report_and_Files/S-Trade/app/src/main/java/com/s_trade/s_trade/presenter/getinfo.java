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

import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;
import java.lang.Exception;
import java.util.List;
import com.alibaba.fastjson.*;
import com.s_trade.s_trade.model.*;
/**
 * Created by saber on 2016/11/28.
 */

public class getinfo {

public static List<String> getimgurl(String itemid)
{
    try {
        FormBody body = new FormBody.Builder()
                .add("itemid", itemid)
                .build();
        String jsonstr= httphelper.post(httphelper.url + "getimgurl", body);
        return JSON.parseArray(jsonstr,String.class);
    }
    catch (Exception e)
    {
        return null;
    }

}

    @Nullable
    public static List<item> getitemlistall()
    {
        try {
            FormBody body = new FormBody.Builder()
                    .add("category", "")
                    .add("sort", "createtime desc")
                    .build();
            String jsonstr= httphelper.post(httphelper.url + "getitemlist", body);
            return JSON.parseArray(jsonstr,item.class);
        }
        catch (Exception e)
        {
            return null;
        }

    }

    @Nullable
    public static List<item> getitemsort(String category, String sort)
    {


        try {

            FormBody body = new FormBody.Builder()
                    .add("category", category)
                    .add("sort", sort)
                    .build();
            String jsonstr= httphelper.post(httphelper.url + "getitemlist", body);
            return JSON.parseArray(jsonstr,item.class);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Nullable
    public static List<item> getitembykw(String keyword)
    {
        try {
            FormBody body = new FormBody.Builder()
                    .add("keyword", keyword)
                    .build();
            String jsonstr= httphelper.post(httphelper.url + "getitemlistbykw", body);
            return JSON.parseArray(jsonstr,item.class);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Nullable
    public static item getitemdetail(String itemid)
    {
        try {
            FormBody body = new FormBody.Builder()
                    .add("itemid", itemid)
                    .build();
            String jsonstr= httphelper.post(httphelper.url + "getitemdetail", body);
            return JSON.parseObject(jsonstr,item.class);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Nullable
    public static List<item> getfavitem(String userid)
    {
        try {
            FormBody body = new FormBody.Builder()
                    .add("userid", userid)
                    .build();
            String jsonstr= httphelper.post(httphelper.url + "getfavlist", body);
            return JSON.parseArray(jsonstr,item.class);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    @Nullable
    public static List<item>  getmysaleitem(String userid)
    {
        try {
            FormBody body = new FormBody.Builder()
                    .add("userid", userid)
                    .build();
            String jsonstr= httphelper.post(httphelper.url + "getmyitem", body);
            return JSON.parseArray(jsonstr,item.class);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    @Nullable
    public static user getuser(String userid)
    {
        try {
            FormBody body = new FormBody.Builder()
                    .add("userid", userid)
                    .build();
            String jsonstr= httphelper.post(httphelper.url + "getuserinfo", body);
            return JSON.parseObject(jsonstr,user.class);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    public static int loginvalid(String account,String password)
    {
        try {
            FormBody body = new FormBody.Builder()
                    .add("account", account)
                    .add("password", password)
                    .build();
            int jsonstr= httphelper.getint(httphelper.url + "loginvalid", body);
            return jsonstr;
        }
        catch (Exception e)
        {
            return 0;
        }
    }
    public static int newuser(String account,String password,String email,String phonenumber, String uname)
    {
        try {
            FormBody body = new FormBody.Builder()
                    .add("account", account)
                    .add("password", password)
                    .add("email", email)
                    .add("phonenumber", phonenumber)
                    .add("name", uname)
                    .build();
            int jsonstr= httphelper.getint(httphelper.url + "newuser", body);
            return jsonstr;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public static int updateuser(String userid,String password,String email,String phonenumber, String uname)
    {
        try {
            FormBody body = new FormBody.Builder()
                    .add("userid", userid)
                    .add("password", password)
                    .add("email", email)
                    .add("phonenumber", phonenumber)
                    .add("name", uname)
                    .build();
            int jsonstr= httphelper.getint(httphelper.url + "updateuser", body);
            return jsonstr;
        }
        catch (Exception e)
        {
            return 0;
        }
    }
    public  static int delfav(String userid, String itemid)
    {
        try
        {
            FormBody body = new FormBody.Builder()
                    .add("userid", userid)
                    .add("itemid", itemid)
                    .build();
            int jsonstr= httphelper.getint(httphelper.url + "delfavlist", body);
            return jsonstr;
        }
        catch (Exception e)
        {
            return 0;
        }
    }
     public  static int addfav(String userid, String itemid)
     {
         try
         {
             FormBody body = new FormBody.Builder()
                     .add("userid", userid)
                     .add("itemid", itemid)
                     .build();
             int jsonstr= httphelper.getint(httphelper.url + "addfavlist", body);
             return jsonstr;
         }
         catch (Exception e)
         {
             return 0;
         }
     }

    public static int sellitem(String itemid)
    {
        try
        {
            FormBody body = new FormBody.Builder()
                    .add("itemid", itemid)
                    .build();
            int jsonstr= httphelper.getint(httphelper.url + "solditem", body);
            return jsonstr;
        }
        catch (Exception e)
        {
            return 0;
        }
    }
    public static int getrandomitemid()
    {
        try
        {
            FormBody body = new FormBody.Builder()
                    .build();
            int jsonstr= httphelper.getint(httphelper.url + "getrandomtitemid", body);
            return jsonstr;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

}
