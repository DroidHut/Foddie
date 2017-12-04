package com.foodproject.adapter;


import android.content.Context;
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

public class SubMenuAdapter extends BaseAdapter {
    ArrayList<ProductModel> arrayList;
    Context context;

    public SubMenuAdapter(Context context, ArrayList<ProductModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.sub_menu_item, null);
            
            holder = new ItemHolder();
            
            holder.name=(TextView)convertView.findViewById(R.id.subMenuName);
            holder.price=(TextView)convertView.findViewById(R.id.subMenuPrice);
            holder.image=(ImageView) convertView.findViewById(R.id.subMenuImage);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }
        holder.name.setText(arrayList.get(position).getSubMenuName());
        holder.price.setText(arrayList.get(position).getSubMenuPrice());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        requestOptions.placeholder(R.drawable.appimg);
        requestOptions.error(R.drawable.appimg);

        Glide.with(context).load(arrayList.get(position).getSubMenuImage().toString()).apply(requestOptions).into(holder.image);
        return convertView;
    }


    private class ItemHolder {
        TextView name;
        TextView price;
        ImageView image;
       
    }
}