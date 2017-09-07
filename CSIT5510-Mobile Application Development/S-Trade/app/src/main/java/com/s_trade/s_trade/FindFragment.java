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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Spinner;
import com.s_trade.s_trade.model.item;
import com.s_trade.s_trade.presenter.getinfo;
import com.s_trade.s_trade.presenter.httphelper;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.*;

/**
 * Created by JiefengHou on 2016/11/17.
 */

public class FindFragment extends Fragment {

    List<Item> itemList = new ArrayList<Item>();
    List<Bitmap> bms=new ArrayList<Bitmap>();
    ListView listView;
    Spinner spinnercate;
    Spinner spinnersort;
    boolean isSpinnerFirst1 = true ;
    boolean isSpinnerFirst2 = true ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);

//      add listener to textView (search textView)
        TextView textView = (TextView) view.findViewById(R.id.textView_search);

        textView.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));

            }
        });
        MyTask myTask=new MyTask();
        myTask.execute("","");
//      list view
        listView = (ListView) view.findViewById(R.id.listView_find);
        spinnercate=(Spinner) view.findViewById(R.id.spinner_category);
        spinnersort=(Spinner) view.findViewById(R.id.spinner_sort);
        spinnercate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int i, long l) {
                if (isSpinnerFirst1)
                {isSpinnerFirst1=false;
                    return;}else {
                    MyTask my = new MyTask();
                    String category = spinnercate.getSelectedItem().toString();
                    if (i == 0) {
                        category = "";
                    }
                    String sort = parsersort(spinnersort.getSelectedItemPosition());
                    if (i == 0 && spinnersort.getSelectedItemPosition() == 0) {
                        my.execute("", sort);
                    } else {
                        my.execute(category, sort);
                    }
                    return;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }
        });
        spinnersort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View view, int i, long l) {
                if (isSpinnerFirst2)
                {
                    isSpinnerFirst2=false;
                    return;
                }
                else {
                    MyTask my = new MyTask();
                    String category = spinnercate.getSelectedItem().toString();
                    if (spinnercate.getSelectedItemPosition() == 0) {
                        category = "";
                    }
                    String sort = parsersort(spinnersort.getSelectedItemPosition());
                    if (i == 0 && spinnercate.getSelectedItemPosition() == 0) {
                        my.execute("", sort);
                    } else {
                        my.execute(category, sort);
                    }
                    return;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }
        });


        return view;
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
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        ProgressDialog dialog =new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {

            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("loading...");
            dialog.show();
        }

        @Override
        protected List<item> doInBackground(String... params) {
            try {
                List<item> items = getinfo.getitemsort(params[0],params[1]);
                bms.clear();
                for (int i=0; i<items.size();i++) {
                    Bitmap bm = httphelper.getimage(String.valueOf(items.get(i).getItemid()));
                    bms.add(bm);
                }
                return items;


            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<item> result) {
            dialog.dismiss();
            setItemList(result);
            listView.setAdapter(new ListViewAdapter(getActivity(), itemList));
            listView.setOnItemClickListener(new OnItemClickListener(){
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //send itemID to itemActivity
                    TextView itemID_textView = (TextView) view.findViewById(R.id.list_itemID);
                    Intent intent = new Intent();
                    intent.putExtra("itemID",itemID_textView.getText().toString());
                    intent.setClass(getActivity(),ItemActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
    protected  String parsersort(int i)
    {
        if (i==0)
        {
            return "createtime desc";
        }
        else if (i==1)
        {
            return "createtime";
        }
        else if (i==2)
        {
            return "price desc";
        }
        else
        {
            return "price";
        }
    }
}
