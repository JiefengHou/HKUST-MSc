/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.s_trade.s_trade.model.item;
import com.s_trade.s_trade.presenter.getinfo;
import com.s_trade.s_trade.presenter.httphelper;

import java.util.List;

/**
 * Created by JiefengHou on 2016/11/29.
 */

public class FavoriteListViewAdapter extends BaseAdapter {

    Context context;
    List<Item> itemList;

    public FavoriteListViewAdapter(Context context, List<Item> itemList) {
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
        SharedPreferences sharedPreferences =context.getSharedPreferences("logInState",Context.MODE_PRIVATE);
        final String uid=sharedPreferences.getString("userID","");

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_favorite, null);
        }
        final TextView itemID = (TextView) view.findViewById(R.id.list_itemID);
        TextView itemName = (TextView) view.findViewById(R.id.list_itemName);
        TextView itemPrice = (TextView) view.findViewById(R.id.list_itemPrice);
        ImageView itemImage = (ImageView) view.findViewById(R.id.list_itemImage);

         itemID.setText(Integer.toString((int)itemList.get(position).getItemId()));
        itemName.setText((String)itemList.get(position).getName());
        itemPrice.setText((String)itemList.get(position).getPrice());
        itemImage.setImageBitmap((Bitmap) itemList.get(position).getImage());

        Button button = (Button) view.findViewById(R.id.list_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = AskOption(position,uid,itemID.getText().toString());
                diaBox.show();
            }
        });
        return view;
    }

    private AlertDialog AskOption(final int position, final String uid, final String itemID)
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(context)
                //set message, title, and icon
                .setTitle("Delete Confirmation")
                .setMessage("Do you want to delete this item from favorite list?")
                .setIcon(R.drawable.icon_attention)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        MyTask myTask=new MyTask();
                        myTask.execute(uid,itemID);
                        itemList.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
    }

    private class MyTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            try {
               return getinfo.delfav(params[0],params[1]);


            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result!=null&&result!=3&&!(result!=0))
            {
                Toast toast = Toast.makeText(context, "database error",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else
            {
                Toast toast = Toast.makeText(context, "delete successfully",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            }
        }
    }

