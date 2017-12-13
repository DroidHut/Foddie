package com.foodproject.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.ui.LazyLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.foodproject.R;
import com.foodproject.pojo.ProductModel;
import com.foodproject.util.ParseJson;

import org.json.JSONObject;
import java.util.ArrayList;
import developer.shivam.perfecto.OnNetworkRequest;
import developer.shivam.perfecto.Perfecto;

public class SubMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private LazyLoader lazyLoader;
    private ArrayList<ProductModel> subMenuPopUplist = new ArrayList<>();
    private String resId;
    private String subMenuId;
    private float subMenuPrice;
    private ImageView subMenuItemImage;
    private TextView subMenuItemName, subMenuTitle, subMenuItemPrice, counterText;
    private ImageButton plusBtn, minusBtn;
    private Button addButton;
    private RelativeLayout relativeLayout;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);

        resId = getIntent().getStringExtra("ResturantId");
        subMenuId = getIntent().getStringExtra("SubMenuId");
        subMenuPrice = getIntent().getFloatExtra("SubMenuPrice", 0);
        lazyLoader = (LazyLoader) findViewById(R.id.lazyLoader_submenu);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout_subMenu);
        relativeLayout.setVisibility(View.GONE);
        Toast.makeText(SubMenuActivity.this, resId + "\n" + subMenuId + "\n" + subMenuPrice, Toast.LENGTH_SHORT).show();

        subMenuItemImage = (ImageView) findViewById(R.id.sub_menu_image);
        subMenuItemName = (TextView) findViewById(R.id.sub_menu_name);
        subMenuTitle = (TextView) findViewById(R.id.sub_menu_title);
        subMenuItemPrice = (TextView) findViewById(R.id.total_price);

        counterText = (TextView) findViewById(R.id.text_counter);
        plusBtn = (ImageButton) findViewById(R.id.btn_add);
        minusBtn = (ImageButton) findViewById(R.id.btn_minus);
        addButton = (Button) findViewById(R.id.addButton);
        counterText.setText("1");

        subMenuItemPrice.setText("Total Price : " + subMenuPrice + " $");
        plusBtn.setOnClickListener(this);
        minusBtn.setOnClickListener(this);
        addButton.setOnClickListener(this);
        counterText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.otf"));
        subMenuItemName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.otf"));
        subMenuTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.otf"));
        subMenuItemPrice.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.otf"));
        getSubMenuPopUp(resId, subMenuId);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                if (addButton.getVisibility() == View.VISIBLE) {
                    addButton.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_add:
                CharSequence countAdd = counterText.getText();
                int a = Integer.parseInt(countAdd.toString());
                if (a >= 0 & a < 100) {
                    a = a + 1;
                    counterText.setText(a + "");

                    if (subMenuPrice != 0) {
                        subMenuItemPrice.setText("Total Price : " + (subMenuPrice * a) + " $");
                    }

                } else {
                    counterText.setText(1 + "");
                }
                break;
            case R.id.btn_minus:
                CharSequence countMinus = counterText.getText();
                int b = Integer.parseInt(countMinus.toString());
                if (b > 0 & b < 100) {
                    b = b - 1;
                    counterText.setText(b + "");
                    if (subMenuPrice != 0) {
                        subMenuItemPrice.setText("Total Price : " + (subMenuPrice * b) + " $");

                    }
                } else {
                    counterText.setText(1 + "");
                }
                break;
        }
    }

    private void getSubMenuPopUp(String resId, String subMenuId) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tag", "getSubMenuPopUp");
            jsonObject.put("Restra_id", resId);
            jsonObject.put("Sub_Menu_id", subMenuId);
            
            Perfecto.with(SubMenuActivity.this)
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
                            relativeLayout.setVisibility(View.VISIBLE);
                            Log.d("SUCCESS", "Request successful");
                            subMenuPopUplist = ParseJson.getSubMenuPopUpList(s);
                            Log.d("SUB MENU POP LIST", s);
                            RequestOptions requestOptions = new RequestOptions();
                            requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                            requestOptions.placeholder(R.drawable.appimg);
                            requestOptions.error(R.drawable.appimg);

                            subMenuItemName.setText(subMenuPopUplist.get(0).getSubMenuPopName());
                            Glide.with(SubMenuActivity.this).load(subMenuPopUplist.get(0).getSubMenuPopImage()).apply(requestOptions).into(subMenuItemImage);
                            Log.d("KETO", subMenuPopUplist + "");
                            Toast.makeText(SubMenuActivity.this, "LIST SIZE " + subMenuPopUplist.size() + "", Toast.LENGTH_SHORT).show();

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
