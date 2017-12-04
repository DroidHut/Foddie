package com.foodproject.activity;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.foodproject.R;
import com.foodproject.fragment.FragmentMain;
import com.foodproject.pojo.ProductModel;
import com.foodproject.util.ParseJson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import developer.shivam.perfecto.OnNetworkRequest;
import developer.shivam.perfecto.Perfecto;


public class MenuActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private String id;
    private TabLayout tabLayout;
    private ArrayList<ProductModel> productModelslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        id = getIntent().getStringExtra("Id");
        Toast.makeText(this, id + "", Toast.LENGTH_SHORT).show();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolText = (TextView) findViewById(R.id.tool_text_menu);
        toolText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.otf"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.getSelectedTabPosition();
        getMenu(id);

    }

    public String getResturantId() {
        return id;
    }

    public ArrayList<ProductModel> getMenuId() {
        return getMenu(id);
    }

    private void setupViewPager(ViewPager viewPager, ArrayList<ProductModel> arrayList) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        try {
            int count = arrayList.size();
            for (int i = 0; i < count; i++) {
                FragmentMain fView = new FragmentMain();
                adapter.addFrag(fView, arrayList.get(i).getMenuName());
                Log.d("TAB TITLE", arrayList.get(i).getMenuName());
                viewPager.setAdapter(adapter);
            }
            
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "NULLLL", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<ProductModel> getMenu(String menuId) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tag", "getMenu");
            jsonObject.put("Restra_id", menuId);

            Perfecto.with(MenuActivity.this)
                    .fromUrl("https://www.foodondeal.in/api")
                    .ofTypePost(jsonObject)
                    .connect(new OnNetworkRequest() {
                        @Override
                        public void onStart() {
                            Log.d("MENU" + "START", "Request started");
                        }

                        @Override
                        public void onSuccess(String s) {
                            Log.d("MENU" + "SUCCESS", "Request successful");
                            productModelslist = ParseJson.getMenuList(s);
                            Log.d("MENU" + "LIST ", s);
                            setupViewPager(viewPager, productModelslist);
                        }

                        @Override
                        public void onFailure(int i, String s, String s1) {
                            Log.d("MENU" + "FAILED", "Request failed");
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }

        return productModelslist;
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentMain.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
   /* private void getSubMenuPopUp(String resId, String menuId) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tag", "getSubMenuPopUp");
            jsonObject.put("Restra_id", resId);
            jsonObject.put("Sub_Menu_id", menuId);


            Perfecto.with(ProductActivity.this)
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
                            productModelslist = ParseJson.getProductList(s);
                            Log.d("LIST", s);
                            listView.setAdapter(new CustomAdapter(ProductActivity.this, productModelslist));
                        }

                        @Override
                        public void onFailure(int i, String s, String s1) {
                            Log.d("FAILED", "Request failed");
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/

}
