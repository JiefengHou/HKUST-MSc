/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yongchun.library.view.ImageSelectorActivity;

import static android.R.attr.mode;
import static com.yongchun.library.view.ImageSelectorActivity.start;

/**
 * Created by JiefengHou on 2016/11/17.
 */

public class SellFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, container, false);

        Button button_sell = (Button) view.findViewById(R.id.button_sell);
        button_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), NewItemActivity.class));
            }
        });

        return view;
    }


}
