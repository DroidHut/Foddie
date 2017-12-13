package com.foodproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.ui.LazyLoader;
import com.foodproject.R;
import com.foodproject.activity.MenuActivity;
import com.foodproject.activity.SubMenuActivity;
import com.foodproject.adapter.SubMenuAdapter;
import com.foodproject.pojo.ProductModel;
import com.foodproject.util.ParseJson;
import com.foodproject.util.RecyclerItemClickListener;

import org.json.JSONObject;

import java.util.ArrayList;

import developer.shivam.perfecto.OnNetworkRequest;
import developer.shivam.perfecto.Perfecto;

public class FragmentMain extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private LazyLoader lazyLoader;
    private ArrayList<String> subMenuList = new ArrayList<>();
    private ArrayList<ProductModel> productModelslist = new ArrayList<>();
    private ArrayList<ProductModel> subMenuPopUplist = new ArrayList<>();
    private String resId;
    private Context mContext;


    public FragmentMain() {
    }

    public static FragmentMain newInstance(int sectionNumber) {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment_main, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.listMenu);
        recyclerView.setNestedScrollingEnabled(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        lazyLoader = (LazyLoader) rootView.findViewById(R.id.lazyLoaderMenu);
        sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        MenuActivity activity = (MenuActivity) getActivity();
        resId = activity.getResturantId();
        subMenuList = activity.getMenuIdList();
        getListItems(resId, subMenuList);
     
       

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mContext = getContext();
                        Intent intent = new Intent(getActivity(), SubMenuActivity.class);
                            intent.putExtra("ResturantId", resId);
                            intent.putExtra("SubMenuId", productModelslist.get(position).getSubMenuId());
                            intent.putExtra("SubMenuPrice", productModelslist.get(position).getSubMenuPrice());
                        getActivity().startActivity(intent);
                    }
                })
        );
        return rootView;
    }


    private void getSubMenu(String resId, String menuId) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tag", "getSubMenu");
            jsonObject.put("Restra_id", resId);
            jsonObject.put("Menu_id", menuId);

            Perfecto.with(getActivity())
                    .fromUrl("https://www.foodondeal.in/api")
                    .ofTypePost(jsonObject)
                    .connect(new OnNetworkRequest() {
                        @Override
                        public void onStart() {
                            Log.d("START", "Request started");
                        }

                        @Override
                        public void onSuccess(String s) {
                            lazyLoader.setVisibility(View.GONE);
                            Log.d("SUCCESS", "Request successful");
                            productModelslist = ParseJson.getSubMenuList(s);
                            Log.d("SUBMENU" + "LIST", s);
                            recyclerView.removeAllViews();
                            recyclerView.setAdapter(new SubMenuAdapter(getActivity(), productModelslist));
                        }

                        @Override
                        public void onFailure(int i, String s, String s1) {
                            Log.d("FAILED", "Request failed");
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void getListItems(String resId, ArrayList<String> list) {
        sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

        switch (sectionNumber) {
            case 1:
                getSubMenu(resId, subMenuList.get(0));
                break;
            case 2:
                getSubMenu(resId, subMenuList.get(1));
                break;
            case 3:
                getSubMenu(resId, subMenuList.get(2));
                break;
            case 4:
                getSubMenu(resId, subMenuList.get(3));
                break;
            case 5:
                getSubMenu(resId, subMenuList.get(4));
                break;
            case 6:
                getSubMenu(resId, subMenuList.get(5));
                break;
            case 7:
                getSubMenu(resId, subMenuList.get(6));
                break;
            case 8:
                getSubMenu(resId, subMenuList.get(7));
                break;
            case 9:
                getSubMenu(resId, subMenuList.get(8));
                break;
        }
          

    }


}
   