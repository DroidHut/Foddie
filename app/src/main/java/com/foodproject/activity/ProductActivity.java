package com.foodproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.ui.LazyLoader;
import com.foodproject.R;
import com.foodproject.adapter.CustomAdapter;
import com.foodproject.pojo.ProductModel;
import com.foodproject.util.ParseJson;
import com.google.android.gms.analytics.ecommerce.Product;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import developer.shivam.perfecto.OnNetworkRequest;
import developer.shivam.perfecto.Perfecto;


public class ProductActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String type = null;
    private ListView listView;
    private LazyLoader lazyLoader;
    private Toolbar toolbar;
    private ArrayList<ProductModel> productModelslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        listView = (ListView) findViewById(R.id.list);
        try{
        String data[] = ChooseLocality.value.split(":");
        Log.d("Data", data[1]);
        String value[] = data[1].split(",");
        String lat = value[0].replace("(", " ").trim();
        String lang = value[1].replace(")", " ");
        type = getIntent().getStringExtra("type");

        sendLatLang(lat, lang, type);

        Log.d("LAT", lat + " LANG:" + lang + type);
        }catch(NullPointerException e){e.printStackTrace();}
        lazyLoader = (LazyLoader) findViewById(R.id.lazyLoader);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolTitle = (TextView) findViewById(R.id.tool_text);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        toolTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.otf"));

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(),   R.drawable.menu, ProductActivity.this.getTheme());
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

      


    }

    private void sendLatLang(String lat, String lang, String type) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tag", "get_restra");
            jsonObject.put("lat", lat);
            jsonObject.put("long", lang);
            jsonObject.put("Method", type);

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


    }
    
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
