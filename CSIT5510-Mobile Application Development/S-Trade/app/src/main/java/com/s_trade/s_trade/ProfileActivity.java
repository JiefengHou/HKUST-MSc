/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.s_trade.s_trade.model.user;
import com.s_trade.s_trade.presenter.getinfo;
import android.content.DialogInterface;

public class ProfileActivity extends AppCompatActivity {

    Toast toast;
    EditText userName;
    EditText password;
    EditText phoneNum;
    EditText uname;
    EditText email;
    //SharedPreferences sharedPreferences = SignInActivity.getSharedPreferences("logInState", Context.MODE_PRIVATE);
    String u;//=sharedPreferences.getString("userName","");
    String ui;//=sharedPreferences.getString("userID","");
    //sharedPreferences.getString("userID","");
    user tempu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("logInState", Context.MODE_PRIVATE);
        u=sharedPreferences.getString("userName","");
        ui=sharedPreferences.getString("userID","");
        userName = (EditText) findViewById(R.id.profile_userName);
        password = (EditText) findViewById(R.id.profile_password);
        phoneNum = (EditText) findViewById(R.id.profile_phoneNum);
        uname=(EditText) findViewById(R.id.profile_uname);
        email=(EditText) findViewById(R.id.profile_email);
//      back to last activity
        ImageView imageView = (ImageView) findViewById(R.id.imageView_back);
        imageView.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getuser neg=new getuser();
        neg.execute(ui);
        userName.setFocusable(false);
        userName.setFocusableInTouchMode(false);
        Button button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              check the username and password is empty or not


                if(TextUtils.isEmpty(userName.getText())) {
                    toast = Toast.makeText(getApplicationContext(), "UserName cannot be empty",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if(TextUtils.isEmpty(password.getText())) {
                    toast = Toast.makeText(getApplicationContext(), "Password cannot be empty",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if(TextUtils.isEmpty(phoneNum.getText())) {
                    toast = Toast.makeText(getApplicationContext(), "Phone Number cannot be empty",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    MyTask myTask=new MyTask();
                    myTask.execute(ui,password.getText().toString(),email.getText().toString()+"@connect.ust.hk",phoneNum.getText().toString(),uname.getText().toString());

                }
            }
        });
    }

    private class MyTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            try {
                int temp= getinfo.updateuser(params[0],params[1],params[2],params[3],params[4]);
                return temp;


            } catch (Exception e) {
                return 0;
            }
        }

        @Override
        protected void onPostExecute(final Integer result) {
            if (result.equals(0)||result.equals(3))
            {
                toast = Toast.makeText(getApplicationContext(), "database error",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else if (result.equals(2))
            {
                toast = Toast.makeText(getApplicationContext(), "database error",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else {
                //storeLogInData(result);

                toast = Toast.makeText(getApplicationContext(), "Save Successfully",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                onBackPressed();
            }

            //set result value

        }
    }

    private class getuser extends AsyncTask<String, Integer, Integer> {

        ProgressDialog dialog =new ProgressDialog(ProfileActivity.this);

        protected void onPreExecute() {
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("loading...");
            dialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                 tempu= getinfo.getuser(ui);
                 return 1;


            } catch (Exception e) {
                return 0;
            }
        }

        @Override
        protected void onPostExecute(final Integer result) {
            if (result.equals(0)||result.equals(3))
            {
                dialog.dismiss();
                onBackPressed();
            }
            else if (result.equals(1))
            {
                userName.setText(tempu.getAccount());
                password.setText(tempu.getPassword());
                email.setText(tempu.getEmail().replace("@connect.ust.hk",""));
                phoneNum.setText(tempu.getPhonenumber());
                uname.setText(tempu.getName());
                dialog.dismiss();
            }
            else {
                //storeLogInData(result);
                dialog.dismiss();
                onBackPressed();
            }

            //set result value

        }
    }
}
