package com.foodproject.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.foodproject.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        TextView textView=(TextView)findViewById(R.id.textLogoSignIn);
        textView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf"));

        TextView forgotPass=(TextView)findViewById(R.id.forgotPass);
        forgotPass.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        
        EditText editEmail=(EditText)findViewById(R.id.edit_login_email);
        editEmail.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));

        EditText editPass=(EditText)findViewById(R.id.edit_login_pass);
        editPass.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));

        TextInputLayout inputEmail=(TextInputLayout)findViewById(R.id.input_login_email);
        inputEmail.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        
        TextInputLayout inputPass=(TextInputLayout)findViewById(R.id.input_login_pass);
        inputPass.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));

        Button signInBtn=(Button)findViewById(R.id.SignInBtn);
        signInBtn.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,ProductActivity.class);
                startActivity(intent);
            }
        });
    }
}
