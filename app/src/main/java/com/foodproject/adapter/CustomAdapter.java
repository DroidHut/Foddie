package com.foodproject.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.foodproject.R;
import com.foodproject.activity.MenuActivity;
import com.foodproject.activity.ProductActivity;
import com.foodproject.pojo.ProductModel;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<ProductModel> productModelArrayList;

    public CustomAdapter(Context context, ArrayList<ProductModel> productModelslist) {
        this.context = context;
        this.productModelArrayList = productModelslist;
    }

    @Override
    public int getCount() {
        return productModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return productModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ItemHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.menuitem_layout, null);

            holder = new ItemHolder();
            holder.name = (TextView) convertView.findViewById(R.id.item_name);
            holder.speciality = (TextView) convertView.findViewById(R.id.item_speciality);
            holder.address = (TextView) convertView.findViewById(R.id.item_address);
            holder.star = (RatingBar) convertView.findViewById(R.id.item_star);
            holder.rate = (TextView) convertView.findViewById(R.id.item_rate);
            holder.minOrder = (TextView) convertView.findViewById(R.id.item_min_order);
            holder.image = (ImageView) convertView.findViewById(R.id.productImage);
            holder.name.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Bold.otf"));
            holder.speciality.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf"));
            holder.address.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf"));
            holder.rate.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf"));
            holder.minOrder.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf"));

            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }
        String special = productModelArrayList.get(position).getSpeciality();
        if (!special.isEmpty()) {
            holder.speciality.setVisibility(View.VISIBLE);
            holder.speciality.setText(special);
        } else {
            holder.speciality.setVisibility(View.GONE);
        }

        holder.name.setText(productModelArrayList.get(position).getName());

        holder.address.setText(productModelArrayList.get(position).getAddress());
        holder.rate.setText("Rating : " + productModelArrayList.get(position).getRate());
        holder.star.setRating(productModelArrayList.get(position).getStar());
        holder.star.setEnabled(false);
        holder.minOrder.setText("Min. Order : " + productModelArrayList.get(position).getMinOrder() + " $");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        requestOptions.placeholder(R.drawable.appimg);
        requestOptions.error(R.drawable.appimg);

        Glide.with(context).load(productModelArrayList.get(position).getImageurl().toString()).apply(requestOptions).into(holder.image);
        // Picasso.with(context).load(productModelArrayList.get(position).getImageurl().toString()).placeholder(R.drawable.appimg).into(holder.image);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemId = productModelArrayList.get(position).getId();
               if(!itemId.isEmpty()){
                    Activity activity=(Activity) context;
                   //Toast.makeText(ProductActivity.this,itemId+"",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, MenuActivity.class);
                     intent.putExtra("Id", itemId);
                    activity.startActivity(intent);
                }else{
                   // Toast.makeText(ProductActivity.this,"NULL ITEM ID",Toast.LENGTH_SHORT).show();
               }


            }

        });
        
        return convertView;
    }

    private class ItemHolder {
        TextView name;
        ImageView image;
        TextView speciality;
        TextView address;
        TextView rate;
        RatingBar star;
        TextView minOrder;

    }


}
