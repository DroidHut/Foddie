package com.foodproject.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.foodproject.R;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


public class ChooseLocality extends AppCompatActivity implements View.OnClickListener{
    EditText editSearch;
    Button button;
    String type;
    RadioButton radioButton;
    RadioGroup radioGroup;
    public static String value;
    private View zzaRh;
    private View zzaRi;
    private EditText zzaRj;
    @Nullable
    private LatLngBounds zzaRk;
    @Nullable
    private AutocompleteFilter zzaRl;
    @Nullable
    private PlaceSelectionListener zzaRm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_locality);
        
        editSearch = (EditText) findViewById(R.id.editWorkLocation);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
        TextView textView=(TextView) findViewById(R.id.companyLogo);
        button = (Button) findViewById(R.id.buttonGo);
        RadioButton pickup=(RadioButton)findViewById(R.id.pickup);
        RadioButton delivery=(RadioButton)findViewById(R.id.Delivery);
                
        pickup.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf"));
        delivery.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf"));
        textView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf"));
        editSearch.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
        button.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Regular.otf"));
                
        button.setOnClickListener(this); 
        editSearch.setOnClickListener(this); 
              
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonGo:
                int id=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(id);
                type=radioButton.getText().toString();
                Intent intent = new Intent(ChooseLocality.this, ProductActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
                break;
            case R.id.editWorkLocation:
                
                zzzG();
                
                break;
        }

    }
    
    private void zzzG() {
        int var1 = -1;

        try {
            Intent var2 = (new PlaceAutocomplete.IntentBuilder(2)).setBoundsBias(this.zzaRk).setFilter(this.zzaRl).zzeq(this.editSearch.getText().toString()).zzig(1).build(ChooseLocality.this);
            this.startActivityForResult(var2, 1);
        } catch (GooglePlayServicesRepairableException var3) {
            var1 = var3.getConnectionStatusCode();
            Log.e("Places", "Could not open autocomplete activity", var3);
        } catch (GooglePlayServicesNotAvailableException var4) {
            var1 = var4.errorCode;
            Log.e("Places", "Could not open autocomplete activity", var4);
        }
        if (var1 != -1) {
            GoogleApiAvailability var5 = GoogleApiAvailability.getInstance();
            var5.showErrorDialogFragment(ChooseLocality.this, var1, 2);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == -1) {
                Place var4 = PlaceAutocomplete.getPlace(ChooseLocality.this, data);
                LatLng latLng = var4.getLatLng();
                String Statename = var4.getName().toString();
                value = latLng + "";
                this.setText(var4.getName().toString());
            } else if (resultCode == 2) {
                Status var5 = PlaceAutocomplete.getStatus(ChooseLocality.this, data);
                if (this.zzaRm != null) {
                    this.zzaRm.onError(var5);
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setText(CharSequence text) {
        this.editSearch.setText(text);
        //this.zzzF();
    }

   
}
