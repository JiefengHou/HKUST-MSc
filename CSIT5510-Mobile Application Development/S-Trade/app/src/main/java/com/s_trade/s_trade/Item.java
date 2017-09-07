/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

import android.graphics.Bitmap;

/**
 * Created by JiefengHou on 2016/11/27.
 */

public class Item {
    private int itemID;
    private String name;
    private String price;
    private Bitmap image;
    private int isenabled;

    public void setItemId(int itemID) {
        this.itemID = itemID;
    }
    public int getItemId() {
        return itemID;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getPrice() {
        return price;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
    public Bitmap getImage() {
        return image;
    }

    public void setisenabled(int isenabled) {
        this.isenabled = isenabled;
    }
    public int getisenabled() {
        return isenabled;
    }

}


