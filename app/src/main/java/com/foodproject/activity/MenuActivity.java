package com.foodproject.activity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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


public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
        //Toast.makeText(this, id + "", Toast.LENGTH_SHORT).show();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolText = (TextView) findViewById(R.id.tool_text_menu);
        toolText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.otf"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.getSelectedTabPosition();
        viewPager.setOffscreenPageLimit(7);
        
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_menu);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_menu);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(),   R.drawable.menu,MenuActivity.this.getTheme());
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        getSupportActionBar().setTitle("");
       /* for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv=(TextView) LayoutInflater.from(MenuActivity.this).inflate(R.layout.custom_tab,null);
            tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf"));
            tabLayout.getTabAt(i).setCustomView(tv);
        }*/
        new MyAsyncTask().execute();

    }

    public String getResturantId() {
        return id;
    }
    
    private ArrayList<String> array=new ArrayList<>();
    public ArrayList<String> getMenuIdList() {
    if(productModelslist.size()>0) {
        for (int i = 0; i < productModelslist.size(); i++) {
            array.add(i, productModelslist.get(i).getMenuId());
            Log.d("GETMENU", array.get(i));
        }
    }
        return array;
     
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MyAsyncTask extends AsyncTask<Void,Void,Void>{

    @Override
    protected Void doInBackground(Void... params) {
        getMenu(id);
        return null;
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
  

}
