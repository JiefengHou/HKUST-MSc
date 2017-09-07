/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by DK on 2016/12/5.
 */

public class DemoApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();

        // the following line is important
        Fresco.initialize(getApplicationContext());
    }
}
