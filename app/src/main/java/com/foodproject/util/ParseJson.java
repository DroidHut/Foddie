package com.foodproject.util;

import android.util.Log;


import com.foodproject.pojo.ProductModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;



public class ParseJson {

    public static ArrayList<ProductModel> getProductList(String s) {
        ArrayList<ProductModel>productlist=new ArrayList<>();
        JSONObject dataObject = null;
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.has("success")) {
                if (jsonObject.getBoolean("success")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ProductModel productModel=new ProductModel();
                        dataObject = jsonArray.getJSONObject(i);
                        if (jsonArray.getJSONObject(i).has("restra_id")) {
                            String id = dataObject.getString("restra_id");
                            Log.d("id", id);
                            productModel.setId(id);
                        }
                        if (jsonArray.getJSONObject(i).has("restra_name")) {
                            String name = dataObject.getString("restra_name");
                            Log.d("Restrant name", name);
                            productModel.setName(name);
                        }
                        if (jsonArray.getJSONObject(i).has("restra_image")) {
                            String imgurl = dataObject.getString("restra_image");
                            Log.d("imageUrl", imgurl);
                            productModel.setImageurl(imgurl);
                        }
                        if(jsonArray.getJSONObject(i).has("restra_address")){
                            String address = dataObject.getString("restra_address");
                            Log.d("resAddress", address);
                            productModel.setAddress(address);
                        }
                        if(jsonArray.getJSONObject(i).has("restra_speciality")){
                            String speciality = dataObject.getString("restra_speciality");
                            Log.d("resSpeciality", speciality);
                            productModel.setSpeciality(speciality);
                        }

                        JSONObject rating = jsonArray.getJSONObject(i).getJSONObject("restra_rating");

                        if (rating.has("Star")) {
                            int star = rating.getInt("Star");
                            Log.d("Star", star + "");
                            productModel.setStar(star);
                        }
                        if (rating.has("Rating")) {
                            String rate = rating.getString("Rating");
                            Log.d("rate", rate + "");
                            productModel.setRate(rate);
                        }
                        
                        try {

                            if (jsonArray.getJSONObject(i).has("restra_order_delivery")) {
                                JSONObject name=null;
                                String restra_order_delivery = dataObject.getString("restra_order_delivery");
                                JSONObject jsonObject1 = new JSONObject(restra_order_delivery);
                                if(jsonObject1.has("delivery"))
                                    name=jsonObject1.getJSONObject("delivery");
                                if(name.has("min_order"))
                                {
                                    String Minorder=name.getString("min_order");
                                    Log.d("imageUrl", Minorder);
                                    productModel.setMinOrder(Minorder);
                                }


                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                productlist.add(productModel);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return productlist;
    }
    public static ArrayList<ProductModel> getMenuList(String s) {
        ArrayList<ProductModel>menuList=new ArrayList<>();
        JSONObject dataObject2 = null;
        try {
            JSONObject jsonObject2 = new JSONObject(s);
            if (jsonObject2.has("success")) {
                if (jsonObject2.getBoolean("success")) {
                    JSONArray jsonArray2 = jsonObject2.getJSONArray("menu");
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        ProductModel productModel=new ProductModel();
                        dataObject2 = jsonArray2.getJSONObject(i);
                        if (jsonArray2.getJSONObject(i).has("id")) {
                            String id = dataObject2.getString("id");
                            Log.d("Menu id", id);
                            productModel.setMenuId(id);
                        }
                        if (jsonArray2.getJSONObject(i).has("menu_name")) {
                            String name = dataObject2.getString("menu_name");
                            Log.d("Menu name", name);
                            productModel.setMenuName(name);
                        }

                        menuList.add(productModel);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuList;
    }
    public static ArrayList<ProductModel> getSubMenuList(String s) {
        ArrayList<ProductModel>subMenuList=new ArrayList<>();
        JSONObject dataObject3 = null;
        try {
            JSONObject jsonObject3 = new JSONObject(s);
            if (jsonObject3.has("success")) {
                if (jsonObject3.getBoolean("success")) {
                    JSONArray jsonArray3 = jsonObject3.getJSONArray("sub_menu");
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        ProductModel productModel=new ProductModel();
                        dataObject3 = jsonArray3.getJSONObject(i);
                        if (jsonArray3.getJSONObject(i).has("id")) {
                            String id = dataObject3.getString("id");
                            Log.d("Sub Menu id", id);
                            productModel.setSubMenuId(id);
                        }
                        if (jsonArray3.getJSONObject(i).has("Name")) {
                            String name = dataObject3.getString("Name");
                            Log.d("Sub Menu name", name);
                            productModel.setSubMenuName(name);
                        }
                        if (jsonArray3.getJSONObject(i).has("Price")) {
                            int price = dataObject3.getInt("Price");
                            Log.d("Sub Menu price", price+"");
                            productModel.setSubMenuPrice(price);
                        }
                        JSONObject image = jsonArray3.getJSONObject(i).getJSONObject("Image");

                        if (image.has("small")) {
                            String img = image.getString("small");
                            Log.d("Sub Menu Image", img);
                            productModel.setSubMenuImage(img);
                        }

                        subMenuList.add(productModel);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return subMenuList;
    }
}