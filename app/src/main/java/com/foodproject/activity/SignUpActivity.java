package com.foodproject.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.foodproject.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        TextView textView=(TextView)findViewById(R.id.textLogoSignUp);
        textView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf"));

        Button signUp=(Button)findViewById(R.id.SignUpBtn);
        signUp.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        
        TextView haveAccount=(TextView)findViewById(R.id.have_account);
        haveAccount.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent =new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(intent);
            }
        });
        EditText editName=(EditText)findViewById(R.id.edit_signup_name);
        editName.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        TextInputLayout inputName=(TextInputLayout)findViewById(R.id.input_signup_name);
        inputName.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));

        EditText editEmail=(EditText)findViewById(R.id.edit_signup_email);
        editEmail.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        TextInputLayout inputEmail=(TextInputLayout)findViewById(R.id.input_signup_email);
        inputEmail.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        
        EditText editPhone=(EditText)findViewById(R.id.edit_signup_phone);
        editPhone.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        TextInputLayout inputPhone=(TextInputLayout)findViewById(R.id.input_signup_phone);
        inputPhone.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        
        EditText editPass=(EditText)findViewById(R.id.edit_signup_pass);
        editPass.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        TextInputLayout inputPass=(TextInputLayout)findViewById(R.id.input_signup_pass);
        inputPass.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        
        EditText editConfirm=(EditText)findViewById(R.id.edit_signup_confirm);
        editConfirm.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        TextInputLayout inputConfirm=(TextInputLayout)findViewById(R.id.input_signup_confirm);
        inputConfirm.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        
    }
    
    
}
