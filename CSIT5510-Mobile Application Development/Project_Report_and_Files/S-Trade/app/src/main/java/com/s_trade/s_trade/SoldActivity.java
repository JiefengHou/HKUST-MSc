/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.s_trade.s_trade.model.item;
import com.s_trade.s_trade.model.user;
import com.s_trade.s_trade.presenter.getinfo;
import com.s_trade.s_trade.presenter.httphelper;
import com.jude.rollviewpager.*;

import java.util.ArrayList;
import java.util.List;

public class SoldActivity extends AppCompatActivity {

    TextView txtdescription;
    TextView txtitemname;
    TextView itemId;
    TextView txtitemprice;
    //ImageView mainimage;
    Button button_sold;
    String itemid;
    user userr;
    List<Bitmap> bms = new ArrayList<Bitmap>();
    String usid;
    RollPagerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold);
        SharedPreferences sharedPreferences = getSharedPreferences("logInState", Context.MODE_PRIVATE);
        final String uid = sharedPreferences.getString("userID", "");
        usid = uid;


//      get itemID from MainActivity and set it to textView
        Intent intent = getIntent();
        itemId = (TextView) findViewById(R.id.item_ID);
        itemId.setText(intent.getStringExtra("itemID"));
        itemid = intent.getStringExtra("itemID");

        txtdescription = (TextView) findViewById(R.id.item_description);
        txtitemname = (TextView) findViewById(R.id.item_name);
        txtitemprice = (TextView) findViewById(R.id.item_price);
        rv = (RollPagerView) findViewById(R.id.rollpv);
        button_sold = (Button) findViewById(R.id.button_sold);

        MyTask my = new MyTask();
        my.execute();

        ImageView imageView_back = (ImageView) findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private class MyTask extends AsyncTask<String, Integer, item> {
        ProgressDialog dialog = new ProgressDialog(SoldActivity.this);

        @Override
        protected void onPreExecute() {

            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("loading...");
            dialog.show();
        }

        @Override
        protected item doInBackground(String... params) {
            try {
                item it = com.s_trade.s_trade.presenter.getinfo.getitemdetail(itemid);
                List<String> a = getinfo.getimgurl(itemid);
                bms = httphelper.getallimg(a);
                return it;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(final item result) {
            dialog.dismiss();
            //txtemail.setText(userr.getEmail());
            txtitemname.setText(result.getItemname());
            txtdescription.setText(result.getDescription());
            txtitemprice.setText(String.valueOf(result.getPrice()));
            rv.setAdapter(new TestNomalAdapter());
            button_sold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.app.AlertDialog diaBox = AskOption(result);
                    diaBox.show();
                }

            });
        }

        private android.app.AlertDialog AskOption(final item result)
        {
            android.app.AlertDialog myQuittingDialogBox =new android.app.AlertDialog.Builder(SoldActivity.this)
                    .setTitle("Sold out confirmation ")
                    .setMessage("Are you sure this item has been sold out?")
                    .setIcon(R.drawable.icon_attention)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            sellitem s = new sellitem();
                            s.execute(String.valueOf(result.getItemid()));
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            return myQuittingDialogBox;
        }


        //      The phone call function
        private class TestNomalAdapter extends StaticPagerAdapter {
            @Override
            public View getView(ViewGroup container, int position) {
                ImageView view = new ImageView(container.getContext());
                view.setImageBitmap(bms.get(position));
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                return view;
            }

            @Override
            public int getCount() {
                return bms.size();
            }
        }

        private class sellitem extends AsyncTask<String, Integer, Integer> {

            @Override
            protected Integer doInBackground(String... params) {
                try {
                    return getinfo.sellitem(params[0]);


                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Integer result) {
                if (result != null && result != 3 && !(result != 0)) {
                    Toast toast = Toast.makeText(SoldActivity.this, "database error",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {
                    startActivity(new Intent(SoldActivity.this, SaleActivity.class));

                    Toast toast = Toast.makeText(SoldActivity.this, "sold successfully",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }

        }
    }
}



