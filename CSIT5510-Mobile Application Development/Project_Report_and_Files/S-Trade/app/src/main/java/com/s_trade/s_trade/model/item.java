/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade.model;

/**
 * Created by saber on 2016/11/27.
 */

public class item {
    private int itemid;

    private int userid;

    private String itemname;

    private int mainpic;

    private String category;

    private double price;

    private String description;

    private String createtime;

    private String expiretime;

    private int isenabled;

    public void setItemid(int itemid){
        this.itemid = itemid;
    }
    public int getItemid(){
        return this.itemid;
    }
    public void setUserid(int userid){
        this.userid = userid;
    }
    public int getUserid(){
        return this.userid;
    }
    public void setItemname(String itemname){
        this.itemname = itemname;
    }
    public String getItemname(){
        return this.itemname;
    }
    public void setMainpic(int mainpic){
        this.mainpic = mainpic;
    }
    public int getMainpic(){
        return this.mainpic;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getCategory(){
        return this.category;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return this.price;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setCreatetime(String createtime){
        this.createtime = createtime;
    }
    public String getCreatetime(){
        return this.createtime;
    }
    public void setExpiretime(String expiretime){
        this.expiretime = expiretime;
    }
    public String getExpiretime(){
        return this.expiretime;
    }
    public void setIsenabled(int isenabled){
        this.isenabled = isenabled;
    }
    public int getIsenabled(){
        return this.isenabled;
    }

}
