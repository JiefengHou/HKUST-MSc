/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import android.content.SharedPreferences;


public class MainActivity extends AppCompatActivity {

    public int logInState = 1;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get log in data
        getLogInData();

//      The bottom navigationBar
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home, "Home"))
                .addItem(new BottomNavigationItem(R.drawable.ic_find, "Find"))
                .addItem(new BottomNavigationItem(R.drawable.ic_sell, "Sell"))
                .addItem(new BottomNavigationItem(R.drawable.ic_user, "Me"))
                .setFirstSelectedPosition(0)
                .initialise();

        setDefaultFragment();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                if (position == 0) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.content_fragment, new HomeFragment());
                    transaction.commit();
                } else if (position == 1) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.content_fragment, new FindFragment());
                    transaction.commit();
                } else if (position == 2) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id.content_fragment, new SellFragment());
                    transaction.commit();
                } else if (position == 3) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    if(logInState == 1) {
                        transaction.replace(R.id.content_fragment, new UserFragment());
                    } else {
                        transaction.replace(R.id.content_fragment, new LogInFragment());
                    }
                    transaction.commit();
                }
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }
        });
//      Navigation bar end

    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_fragment, new HomeFragment());
        transaction.commit();
    }

    public void logIn() {
        logInState = 0;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_fragment, new LogInFragment());
        transaction.commit();
    }

    public void logOut() {
        logInState = 1;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.content_fragment, new UserFragment());
        transaction.commit();

        //remove log in data
        removeLogInData();
    }

    public void getLogInData() {
        sharedPreferences = getSharedPreferences("logInState", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("userName", "");
        String usernID = sharedPreferences.getString("userID", "");
        logInState = sharedPreferences.getInt("loginState", 1);
    }

    public void removeLogInData() {
        sharedPreferences = getSharedPreferences("logInState", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
