package com.foodproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.agrawalsuneet.dotsloader.ui.LazyLoader;
import com.foodproject.R;
import com.foodproject.activity.MenuActivity;
import com.foodproject.adapter.SubMenuAdapter;
import com.foodproject.pojo.ProductModel;
import com.foodproject.util.ParseJson;

import org.json.JSONObject;

import java.util.ArrayList;

import developer.shivam.perfecto.OnNetworkRequest;
import developer.shivam.perfecto.Perfecto;

public class FragmentMain extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    private ListView listView;
    private LazyLoader lazyLoader;
    private ArrayList<ProductModel> productModelslist = new ArrayList<>();

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment_main, container, false);

        listView = (ListView) rootView.findViewById(R.id.listMenu);
        lazyLoader = (LazyLoader) rootView.findViewById(R.id.lazyLoaderMenu);
        sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

        MenuActivity activity=(MenuActivity)getActivity();
        String resId=activity.getResturantId();
        ArrayList<ProductModel> subMenuList=activity.getMenuId();
      //  getSubMenu(resId,subMenuList.get(sectionNumber).getMenuId());
        Log.d("MYMENU",resId+"\t"+subMenuList);

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
                            listView.setAdapter(new SubMenuAdapter(getActivity(), productModelslist));
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
}
   