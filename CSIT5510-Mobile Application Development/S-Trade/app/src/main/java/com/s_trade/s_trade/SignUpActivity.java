/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

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

import com.s_trade.s_trade.presenter.getinfo;

public class SignUpActivity extends AppCompatActivity {

    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ImageView imageView = (ImageView) findViewById(R.id.imageView_back);
        imageView.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button button_signUp = (Button) findViewById(R.id.button_signUp);
        button_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              check the username, password, email and phone number is empty or not

                EditText userName = (EditText) findViewById(R.id.signUp_userName);
                EditText password = (EditText) findViewById(R.id.signUp_password);
                EditText email = (EditText) findViewById(R.id.signUp_email);
                EditText phoneNum = (EditText) findViewById(R.id.signUp_phoneNum);
                EditText uname = (EditText) findViewById(R.id.signUp_uname);

                if(TextUtils.isEmpty(userName.getText())) {
                    toast = Toast.makeText(getApplicationContext(), "Account cannot be empty",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if(TextUtils.isEmpty(password.getText())) {
                    toast = Toast.makeText(getApplicationContext(), "Password cannot be empty",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if(TextUtils.isEmpty(email.getText())){
                    toast = Toast.makeText(getApplicationContext(), "Email cannot be empty",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if(TextUtils.isEmpty(phoneNum.getText())) {
                    toast = Toast.makeText(getApplicationContext(), "Phone Number cannot be empty",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    MyTask my=new MyTask();
                    my.execute(userName.getText().toString(),password.getText().toString(),
                            email.getText().toString()+"@connect.ust.hk",phoneNum.getText().toString(),
                            uname.getText().toString());
                }
            }
        });
    }
    private class MyTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            try {
                int temp= getinfo.newuser(params[0],params[1],params[2],params[3],params[4]);
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
                toast = Toast.makeText(getApplicationContext(), "the account has been exist",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else {
                toast = Toast.makeText(getApplicationContext(), "Sign Up Successfully, Please Sign In",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                onBackPressed();
            }
        }
    }
}
