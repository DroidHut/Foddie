package com.foodproject.adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.foodproject.R;
import com.foodproject.pojo.ProductModel;

import java.util.ArrayList;

public class SubMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   private  ArrayList<ProductModel> arrayList;
   private Context context;
    private RecyclerView.ViewHolder vh;

    public SubMenuAdapter(Context context, ArrayList<ProductModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_menu_item, parent, false);
        vh= new ItemHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder holder1 = (ItemHolder) holder;

        holder1.name.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Bold.otf"));
        holder1.price.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf"));
        holder1.name.setText(arrayList.get(position).getSubMenuName());
        holder1.price.setText(arrayList.get(position).getSubMenuPrice() + " $");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        requestOptions.placeholder(R.drawable.appimg);
        requestOptions.error(R.drawable.appimg);

        Glide.with(context).load(arrayList.get(position).getSubMenuImage().toString()).apply(requestOptions).into(holder1.image);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return  arrayList== null ? 0 : arrayList.size();
    }

    private static class ItemHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        ImageView image;

        public ItemHolder(View convertView) {
            super(convertView);
            name = (TextView) convertView.findViewById(R.id.subMenuName);
            price = (TextView) convertView.findViewById(R.id.subMenuPrice);
            image = (ImageView) convertView.findViewById(R.id.subMenuImage);

        }
    }
}