package com.foodproject.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.foodproject.R;
import com.foodproject.accessibility.TypeFaceUtil;


public class StartupActivity extends AppCompatActivity implements View.OnClickListener {
    Button loginActivity, registrationActivity;
    LinearLayout skipFooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // TypeFaceUtil.overrideFont(getApplicationContext(),"QUICKSAND", "fonts/Quicksand-Regular.otf");
        TextView textView = (TextView) findViewById(R.id.text_app);
        TextView footer = (TextView) findViewById(R.id.skipnw);
        loginActivity = (Button) findViewById(R.id.button_login);
        registrationActivity = (Button) findViewById(R.id.button_sign_up);
        skipFooter = (LinearLayout) findViewById(R.id.skipFooter);

        textView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.otf"));
        footer.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf"));
        loginActivity.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf"));
        registrationActivity.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf"));

        loginActivity.setOnClickListener(this);
        registrationActivity.setOnClickListener(this);
        skipFooter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_login:
                Intent loginActivity = new Intent(StartupActivity.this, LoginActivity.class);
                loginActivity.putExtra("which_activity", "StartupActivity");
                startActivity(loginActivity);
              //  overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                break;
            case R.id.button_sign_up:
                Intent registrationIntent = new Intent(StartupActivity.this,SignUpActivity.class);
                startActivity(registrationIntent);
              //  overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                break;
            case R.id.skipFooter:
                Intent intent=new Intent(StartupActivity.this,ChooseLocality.class);
                startActivity(intent);
                break;
        }
    }
}

