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

public class ItemActivity extends AppCompatActivity {
    TextView contact_phoneNum;
    TextView txtusername;
    TextView txtemail;
    TextView txtdescription;
    TextView txtitemname;
    TextView itemId;
    TextView txtitemprice;
    Button button_sell;
    String itemid;
    user userr;
    List<Bitmap> bms=new ArrayList<Bitmap>();
    String usid;
    RollPagerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        SharedPreferences sharedPreferences = getSharedPreferences("logInState", Context.MODE_PRIVATE);
        final String uid=sharedPreferences.getString("userID","");
        usid=uid;


//      get itemID from MainActivity and set it to textView
        Intent intent = getIntent();
        TextView itemId = (TextView) findViewById(R.id.item_ID);
        itemId.setText(intent.getStringExtra("itemID"));
        itemId = (TextView) findViewById(R.id.item_ID);
        itemId.setText(intent.getStringExtra("itemID"));
        itemid=intent.getStringExtra("itemID");

        txtusername=(TextView) findViewById(R.id.contact_name);
        txtdescription=(TextView) findViewById(R.id.item_description);
        txtitemname=(TextView) findViewById(R.id.item_name);
        txtitemprice=(TextView) findViewById(R.id.item_price);
        txtemail=(TextView) findViewById(R.id.contact_email);
        contact_phoneNum=(TextView) findViewById(R.id.contact_phoneNum);
        rv=(RollPagerView) findViewById(R.id.rollpv);


        MyTask my=new MyTask();
        my.execute();

//      back to last activity



//      back to last activity
        ImageView imageView_back = (ImageView) findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private class MyTask extends AsyncTask<String, Integer, item> {
        ProgressDialog dialog =new ProgressDialog(ItemActivity.this);
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
                userr= getinfo.getuser(String.valueOf(it.getUserid()));
                 List<String> a=getinfo.getimgurl(itemid);
                 bms=httphelper.getallimg(a);
                return it;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(item result) {
            dialog.dismiss();
            txtemail.setText(userr.getEmail());
            txtitemname.setText(result.getItemname());
            txtdescription.setText(result.getDescription());
            txtitemprice.setText(String.valueOf( result.getPrice()));
            txtusername.setText(userr.getName());
            contact_phoneNum.setText(userr.getPhonenumber());
            rv.setAdapter(new TestNomalAdapter());
            final String phoneNumber = userr.getPhonenumber();
            button_sell = (Button) findViewById(R.id.button_call);
            button_sell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + phoneNumber));
                    startActivity(callIntent);
                }
            });
            ImageView imageView_like = (ImageView) findViewById(R.id.item_like);
            if (result.getIsenabled()==0)
            {
                Dialog alertDialog = new AlertDialog.Builder(ItemActivity.this).
                        setTitle("MESSAGE").
                        setMessage("this good has been sold").
                        create();
                alertDialog.show();

                imageView_like.setEnabled(false);
            }


            imageView_like.setOnClickListener(new TextView.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if (usid.isEmpty()||usid==null)
                   {
                       Toast.makeText(getApplicationContext(), "You have not sign in yet",
                               Toast.LENGTH_SHORT).show();
                   }
                    else {
                       Addfav myadd = new Addfav();
                       myadd.execute(usid, itemid);
                   }

                }
            });
        }
    }

    private class Addfav extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            try {
                return getinfo.addfav(params[0],params[1]);


            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            ImageView like = (ImageView) findViewById(R.id.item_like);
            if (result!=null&&result!=3&&!(result!=0))
            {
                Toast.makeText(getApplicationContext(), "You have already added this item to my Favorites",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Add to My Favorites Successfully",
                        Toast.LENGTH_SHORT).show();
                like.setImageResource(R.drawable.image_like);
            }
        }

    }

    private class TestNomalAdapter extends StaticPagerAdapter {
        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageBitmap(bms.get(position));
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return bms.size();
        }
    }
}

