/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.s_trade.s_trade.presenter.getinfo;

import java.util.List;

/**
 * Created by JiefengHou on 2016/11/29.
 */

public class SaleListViewAdapter extends BaseAdapter {

    Context context;
    List<Item> itemList;
    //Button soldButton;
    public SaleListViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public int getCount() {
        return itemList.size();
    }

    public Item getItem(int position) {
        return itemList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_sale, null);
        }
        TextView isenabled = (TextView) view.findViewById(R.id.list_isenabled);
        TextView itemID = (TextView) view.findViewById(R.id.list_itemID);
        TextView itemName = (TextView) view.findViewById(R.id.list_itemName);
        TextView itemPrice = (TextView) view.findViewById(R.id.list_itemPrice);
        ImageView itemImage = (ImageView) view.findViewById(R.id.list_itemImage);
        ImageView itemsoldIm=(ImageView) view.findViewById(R.id.list_itemSold);

        isenabled.setText(Integer.toString((int)itemList.get(position).getisenabled()));
        itemID.setText(Integer.toString((int)itemList.get(position).getItemId()));
        itemName.setText((String)itemList.get(position).getName());
        itemPrice.setText((String)itemList.get(position).getPrice());
        itemImage.setImageBitmap((Bitmap)itemList.get(position).getImage());
        itemsoldIm.setVisibility(itemList.get(position).getisenabled()==1?View.INVISIBLE:View.VISIBLE);
        return view;
    }
}
