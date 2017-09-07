/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by JiefengHou on 2016/11/26.
 */

public class LogInFragment extends Fragment {
    TextView txtaccount;
    TextView txtuserid;
    Toast toast;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_login, container, false);

        txtaccount=(TextView) view.findViewById(R.id.account);
        txtuserid=(TextView) view.findViewById(R.id.userid);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("logInState", Context.MODE_PRIVATE);
        String u=sharedPreferences.getString("userName","");
        String ui=sharedPreferences.getString("userID","");
        if (!u.isEmpty())
        {
            txtaccount.setText(u);
        }
        if (!ui.isEmpty())
        {
            txtuserid.setText(ui);
        }
//      my favorite
        Button button_favorite = (Button) view.findViewById(R.id.button_favorite);
        button_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),FavoriteActivity.class));
            }
        });

//      my sale
        Button button_sale = (Button) view.findViewById(R.id.button_sale);
        button_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SaleActivity.class));
            }
        });

//      edit profile
        Button button_profile = (Button) view.findViewById(R.id.button_edit);
        button_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ProfileActivity.class));
            }
        });

//      log out
        Button button_sell = (Button) view.findViewById(R.id.button_logOut);
        button_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).logOut();
            }
        });

        Button button_pay = (Button) view.findViewById(R.id.button_pay);
        button_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (Exception e) {
                    toast = Toast.makeText(getContext(), "you have not install ALIPAY yet",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        Button button_paid = (Button) view.findViewById(R.id.button_getpay);
        button_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=20000056");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (Exception e) {
                    toast = Toast.makeText(getContext(), "you have not install ALIPAY yet",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });


        Button button_shake = (Button) view.findViewById(R.id.button_shake);
        button_shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ShakeActivity.class));
            }
        });
        return view;
    }




}
