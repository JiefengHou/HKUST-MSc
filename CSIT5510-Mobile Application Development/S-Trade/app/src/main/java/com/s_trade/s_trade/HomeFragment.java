/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import com.s_trade.s_trade.presenter.*;
import com.s_trade.s_trade.model.*;

/**
 * Created by JiefengHou on 2016/11/17.
 */

public class HomeFragment extends Fragment {

    List<Bitmap> bms = new ArrayList<Bitmap>();
    RecyclerView recyclerView;
    List<Product> productList = new ArrayList<Product>();
    MasonryAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.listView_wtf);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        MyTask myTask = new MyTask();
        myTask.execute();

        return view;
    }

    public void initldata(List<item> items) {
        productList.clear();
        for (int i = 0; i < items.size(); i++) {
            Product prd = new Product(bms.get(i), items.get(i).getItemname());
            productList.add(prd);
        }
    }

    private class MyTask extends AsyncTask<String, Integer, List<item>> {

        ProgressDialog dialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {

            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("loading...");
            dialog.show();
        }

        @Override
        protected List<item> doInBackground(String... params) {
            try {
                List<item> items = getinfo.getitemlistall();
                bms.clear();
                for (int i = 0; i < items.size(); i++) {
                    Bitmap bm = httphelper.getimage(String.valueOf(items.get(i).getItemid()));
                    bms.add(bm);
                }
                return items;


            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(final List<item> result) {
            initldata(result);
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

            adapter = new MasonryAdapter(getContext(),productList);
            recyclerView.setAdapter(adapter);

            SpacesItemDecoration decoration = new SpacesItemDecoration(16);
            recyclerView.addItemDecoration(decoration);
            adapter.setOnItemClickListener(new MasonryAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(int position, Product product) {
                    Intent intent = new Intent();
                    intent.putExtra("itemID",String.valueOf(result.get(position).getItemid()));
                    intent.setClass(getActivity(),ItemActivity.class);
                    startActivity(intent);
                }

            });

            dialog.dismiss();
        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }













}
