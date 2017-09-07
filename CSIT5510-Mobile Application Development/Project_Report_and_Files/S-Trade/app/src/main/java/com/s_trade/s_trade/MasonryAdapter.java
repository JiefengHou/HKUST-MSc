/**
 #Course Code	#Name	         Student ID	  Email address
 CSIT 5510       KWAN Chak Lam	 20095398	  clkwanaa@connect.ust.hk
 CSIT 5510       Deng Ken	 20400660	  kdengab@connect.ust.hk
 CSIT 5510       Hou Jiefeng	 20361723	  jhouad@connect.ust.hk
 */

package com.s_trade.s_trade;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DK on 2016/12/6.
 */

public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView> {
    private List<Product> products;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public MasonryAdapter(Context context,List<Product> list) {
        this.context=context;
        products=list;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.masonry_item, viewGroup, false);
        MasonryView vh = new MasonryView(view);
        return vh;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void onBindViewHolder(MasonryView masonryView, final int position) {
        masonryView.imageView.setImageBitmap(products.get(position).getImg());
        masonryView.textView.setText(products.get(position).getTitle());

        masonryView.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(position, products.get(position));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setOnItemClickListener() {

    }

    public  class MasonryView extends  RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;



        public MasonryView(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.masonry_item_img );
            textView= (TextView) itemView.findViewById(R.id.masonry_item_title);
        }


    }
    public interface OnItemClickListener {
        void onItemClickListener(int position, Product data);

    }







}
