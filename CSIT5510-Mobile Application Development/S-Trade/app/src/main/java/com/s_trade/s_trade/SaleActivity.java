/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.s_trade.s_trade.model.item;
import com.s_trade.s_trade.presenter.getinfo;
import com.s_trade.s_trade.presenter.httphelper;

import java.util.ArrayList;
import java.util.List;

public class SaleActivity extends AppCompatActivity {

    List<Item> itemList = new ArrayList<Item>();
    List<Bitmap> bms=new ArrayList<Bitmap>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        SharedPreferences sharedPreferences = getSharedPreferences("logInState", Context.MODE_PRIVATE);
        String uid=sharedPreferences.getString("userID","");

//      back to last activity
        ImageView imageView = (ImageView) findViewById(R.id.imageView_back);
        imageView.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        listView = (ListView) findViewById(R.id.listView_sale);
//      list view
        MyTask myTask=new MyTask();
        myTask.execute(uid);
    }

    public void setItemList(List<item> items){
        itemList.clear();
        for (int i=0; i<items.size(); i++)
        {
            Item item = new Item();
            item.setName(items.get(i).getItemname());
            item.setItemId(items.get(i).getItemid());
            item.setPrice(String.valueOf(items.get(i).getPrice()));
            item.setImage(bms.get(i));
            item.setisenabled(items.get(i).getIsenabled());
            itemList.add(item);

        }

    }

    private class MyTask extends AsyncTask<String, Integer, List<item>> {

        ProgressDialog dialog =new ProgressDialog(SaleActivity.this);
        @Override
        protected void onPreExecute() {

            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("loading...");
            dialog.show();
        }

        @Override
        protected List<item> doInBackground(String... params) {
            try {
                List<item> items = getinfo.getmysaleitem(params[0]);
                if (items!=null&&!(items.isEmpty())) {
                    bms.clear();
                    for (int i = 0; i < items.size(); i++) {
                        Bitmap bm = httphelper.getimage(String.valueOf(items.get(i).getItemid()));
                        bms.add(bm);
                    }
                }
                return items;


            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(final List<item> result) {
            if (result!=null&&!(result.isEmpty())) {
                setItemList(result);
                listView.setAdapter(new SaleListViewAdapter(SaleActivity.this, itemList));
                 //put itemList to adapter
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //send itemID to itemActivity
                if(result.get(position).getIsenabled()==1) {
                    TextView itemID_textView = (TextView) findViewById(R.id.list_itemID);
                    Intent intent = new Intent();
                    intent.putExtra("itemID", String.valueOf(result.get(position).getItemid()));
                    intent.setClass(SaleActivity.this, SoldActivity.class);
                    startActivity(intent);
                }
            }
        });
            }
            dialog.dismiss();
        }
    }
}
