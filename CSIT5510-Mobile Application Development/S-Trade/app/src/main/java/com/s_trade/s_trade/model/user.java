/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade.model;

/**
 * Created by saber on 2016/11/17.
 */

public class user {
        private int userid;

        private String account;

        private String password;

        private String email;

        private String phonenumber;

        private String name;

        private String createtime;

        private String lastmodifytime;

        private int isseller;

        private int isenabled;

        public void setUserid(int userid){
            this.userid = userid;
        }
        public int getUserid(){
            return this.userid;
        }
        public void setAccount(String account){
            this.account = account;
        }
        public String getAccount(){
            return this.account;
        }
        public void setPassword(String password){
            this.password = password;
        }
        public String getPassword(){
            return this.password;
        }
        public void setEmail(String email){
            this.email = email;
        }
        public String getEmail(){
            return this.email;
        }
        public void setPhonenumber(String phonenumber){
            this.phonenumber = phonenumber;
        }
        public String getPhonenumber(){
            return this.phonenumber;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setCreatetime(String createtime){
            this.createtime = createtime;
        }
        public String getCreatetime(){
            return this.createtime;
        }
        public void setLastmodifytime(String lastmodifytime){
            this.lastmodifytime = lastmodifytime;
        }
        public String getLastmodifytime(){
            return this.lastmodifytime;
        }
        public void setIsseller(int isseller){
            this.isseller = isseller;
        }
        public int getIsseller(){
            return this.isseller;
        }
        public void setIsenabled(int isenabled){
            this.isenabled = isenabled;
        }
        public int getIsenabled(){
            return this.isenabled;
        }
}
