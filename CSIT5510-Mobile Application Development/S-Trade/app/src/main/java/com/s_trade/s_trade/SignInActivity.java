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
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.s_trade.s_trade.model.item;
import com.s_trade.s_trade.presenter.getinfo;
import com.s_trade.s_trade.presenter.httphelper;

import java.util.List;

public class SignInActivity extends AppCompatActivity {

    EditText userName;
    boolean loginvalid=false;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//      back to last activity
        ImageView imageView = (ImageView) findViewById(R.id.imageView_back);
        imageView.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button button_signIn = (Button) findViewById(R.id.button_signIn);
        button_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              check the username and password is empty or not

                 userName = (EditText) findViewById(R.id.signIn_userName);
                EditText password = (EditText) findViewById(R.id.signIn_password);
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
                } else {
                    MyTask my=new MyTask();
                    my.execute(userName.getText().toString(),password.getText().toString());
                    //save login state

                }
            }
        });
    }
    public void storeLogInData(int i) {
        SharedPreferences sharedPreferences = getSharedPreferences("logInState", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("userName", userName.getText().toString());
        editor.putString("userID", String.valueOf(i));
        editor.putInt("loginState", 0);
        editor.commit();
    }

    private class MyTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            try {
                 int temp= getinfo.loginvalid(params[0],params[1]);
                 if (temp==0) {
                     return 0;
                 }
                else
                 {
                     return temp;
                 }


            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(final Integer result) {
            if (result.equals(0))
            {
                toast = Toast.makeText(getApplicationContext(), "Password or Account is wrong",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else {
                storeLogInData(result);
                int RESULT_SUCCESS = 1;
                setResult(RESULT_SUCCESS);
                finish();
            }
        }
    }
}
