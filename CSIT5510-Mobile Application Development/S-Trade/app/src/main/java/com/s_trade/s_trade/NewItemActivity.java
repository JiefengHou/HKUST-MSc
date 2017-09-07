/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.s_trade.s_trade.model.user;
import com.s_trade.s_trade.presenter.getinfo;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.s_trade.s_trade.presenter.httphelper.url;
import static com.yongchun.library.view.ImageSelectorActivity.start;

import com.s_trade.s_trade.presenter.httphelper;
import com.yongchun.library.*;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewItemActivity extends AppCompatActivity {
    String u;//=sharedPreferences.getString("userName","");
    String ui;//=sharedPreferences.getString("userID","");
    EditText itemName;
    EditText itemDescription;
    EditText itemPrice;
    Spinner category;
    EditText contactName;
    EditText contactPhone;
    EditText contactEmail;
    private static final int REQUEST_CODE = 123;
    private ArrayList<String> mResults = new ArrayList<>();
    user tempu;
    Toast toast;
    URI uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        SharedPreferences sharedPreferences = getSharedPreferences("logInState", Context.MODE_PRIVATE);
        u=sharedPreferences.getString("userName","");
        ui=sharedPreferences.getString("userID","");

        itemName = (EditText) findViewById(R.id.item_name);
        itemDescription = (EditText) findViewById(R.id.item_description);
        itemPrice = (EditText) findViewById(R.id.item_price);
        category = (Spinner) findViewById(R.id.item_category);
        //category.removeAllViews();
        contactName = (EditText) findViewById(R.id.contact_name);
        contactPhone = (EditText) findViewById(R.id.contact_phoneNum);
        contactEmail = (EditText) findViewById(R.id.contact_email);

        getuser gu=new getuser();
        gu.execute(ui);

        //      back to last activity
        ImageView imageView_back = (ImageView) findViewById(R.id.imageView_back);
        imageView_back.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Button button_selectPhoto=(Button) findViewById(R.id.button_selectPhoto);
        button_selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // start multiple photos selector
                Intent intent = new Intent(NewItemActivity.this, ImagesSelectorActivity.class);
                // max number of images to be selected
                intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5);
                // min size of image which will be shown; to filter tiny images (mainly icons)
                intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
                // show camera or not
                intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                // pass current selected images as the initial value
                intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
                // start the selector
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        Button button_sell = (Button) findViewById(R.id.button_sell);

        button_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              check the username and password is empty or not

                if(TextUtils.isEmpty(itemName.getText())) {
                    toast = Toast.makeText(getApplicationContext(), "Item Name cannot be empty",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if(TextUtils.isEmpty(itemDescription.getText())) {
                    toast = Toast.makeText(getApplicationContext(), "Item Description cannot be empty",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if(TextUtils.isEmpty(itemPrice.getText())) {
                    toast = Toast.makeText(getApplicationContext(), "Item Price cannot be empty",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (category.getSelectedItem().toString().equals("Category")) {
                    toast = Toast.makeText(getApplicationContext(), "Please Select a Category",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if(TextUtils.isEmpty(contactName.getText())) {
                    toast = Toast.makeText(getApplicationContext(), "Contact Name cannot be empty",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if(TextUtils.isEmpty(contactPhone.getText())) {
                    toast = Toast.makeText(getApplicationContext(), "Phone Number cannot be empty",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if(TextUtils.isEmpty(contactEmail.getText())) {
                    toast = Toast.makeText(getApplicationContext(), "Email cannot be empty",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    try {
                        Toast toast;
                        newitem ni = new newitem();
                        ni.execute(ui.toString(), itemName.getText().toString(), itemDescription.getText().toString(), itemPrice.getText().toString(),category.getSelectedItem().toString());
                    }
                    catch (Exception e)
                    {
                        toast = Toast.makeText(getApplicationContext(), "Database Error",
                            Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
//                    toast = Toast.makeText(getApplicationContext(), "Post New Item Successfully",
//                            Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                    onBackPressed();
                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // get selected images from selector
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;
                //chosenphotonumber.setText("Totally "+String.valueOf(mResults.size())+" images selected");
                toast = Toast.makeText(getApplicationContext(), "Totally "+String.valueOf(mResults.size())+" images selected",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class newitem extends AsyncTask<String,Integer,Integer>
    {
        ProgressDialog dialog =new ProgressDialog(NewItemActivity.this);
        @Override
        protected void onPreExecute() {

            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("uploading...");
            dialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                OkHttpClient mOkHttpClient = new OkHttpClient();
                MultipartBody.Builder builder=  new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("userid", params[0]);
                builder.addFormDataPart("itemname",params[1] );
                builder.addFormDataPart("itemdescription", params[2]);
                builder.addFormDataPart("itemprice", params[3]);
                builder.addFormDataPart("category", params[4]);
                for (int i=0;i<mResults.size();i++)
                {
                    builder.addFormDataPart("image",getFileName(mResults.get(i)),
                        RequestBody.create(MediaType.parse("image/*"), new File(mResults.get(i))));
                }
                RequestBody requestBody=builder.build();
                Request request = new Request.Builder()
                        //.header("Authorization", "Client-ID " + "...")
                        .url(httphelper.uploadurl)
                        .post(requestBody)
                        .build();

                Response response = mOkHttpClient.newCall(request).execute();
                String b=response.body().toString();
                response.close();

                return 1;
            } catch (Exception e) {
                return 0;
            }
        }
        @Override
        protected void onPostExecute(final Integer result) {
            dialog.dismiss();
            toast = Toast.makeText(getApplicationContext(), "Post New Item Successfully",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            finish();
        }

    }

    private class getuser extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            try {
                tempu= getinfo.getuser(ui);
                if (tempu==null)
                {
                    return 3;
                }
                else {
                    return 1;
                }


            } catch (Exception e) {
                return 0;
            }
        }

        @Override
        protected void onPostExecute(final Integer result) {
            if (result.equals(0))
            {
                toast = Toast.makeText(getApplicationContext(), "database error",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                onBackPressed();
            }
            else if(result.equals(3))
            {
                toast = Toast.makeText(getApplicationContext(), "you have not sign in",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                onBackPressed();
            }
            else if (result.equals(1))
            {
                //contactName.setText(tempu.getAccount());
                contactEmail.setText(tempu.getEmail());
                contactPhone.setText(tempu.getPhonenumber());
                contactName.setText(tempu.getName());
            }

            //set result value

        }
    }

    public String getFileName(String pathandname){

        int start=pathandname.lastIndexOf("/");
        int end=pathandname.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return pathandname.substring(start+1);
        }else{
            return null;
        }

    }



}
