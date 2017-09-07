/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.view.View;
import android.widget.TextView;

import com.s_trade.s_trade.model.item;
import com.s_trade.s_trade.presenter.getinfo;
import com.s_trade.s_trade.presenter.httphelper;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    List<Item> itemList = new ArrayList<Item>();
    SearchView searchView;
    ListView listView;
    List<Bitmap> bms=new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//      back to last activity
        TextView textView = (TextView) findViewById(R.id.search_cancel);
        textView.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String text) {

//              list view
                listView = (ListView) findViewById(R.id.listView_search);
                //set data to list
                MyTask myTask=new MyTask();
                myTask.execute(text);

                return true;
            }
        });
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
            itemList.add(item);

        }

    }

    private class MyTask extends AsyncTask<String, Integer, List<item>> {
        ProgressDialog dialog =new ProgressDialog(SearchActivity.this);
        @Override
        protected void onPreExecute() {

            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("loading...");
            dialog.show();
        }
        @Override
        protected List<item> doInBackground(String... params) {
            try {

                //List<item> items = getinfo.getitemlistall();

                List<item> items= getinfo.getitembykw(params[0]);
                        bms.clear();
                        if (items.size() != 0) {
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
            setItemList(result);
            dialog.dismiss();
            listView.setAdapter(new ListViewAdapter(SearchActivity.this, itemList)); //put itemList to adapter

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //send itemID to itemActivity
                    //TextView itemID_textView = (TextView) findViewById(R.id.list_itemID);
                    Intent intent = new Intent();
                    intent.putExtra("itemID",String.valueOf(result.get(position).getItemid()));
                    intent.setClass(SearchActivity.this,ItemActivity.class);
                    startActivity(intent);
                }
            });

            searchView.clearFocus();
        }
    }

}
