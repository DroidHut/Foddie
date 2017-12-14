package com.foodproject.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
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
import com.foodproject.util.MyLocation;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Iterator;


public class ChooseLocality extends AppCompatActivity implements View.OnClickListener {
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
    private int MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_locality);

        editSearch = (EditText) findViewById(R.id.editWorkLocation);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        TextView textView = (TextView) findViewById(R.id.companyLogo);
        TextView textLocation1 = (TextView) findViewById(R.id.textLocation1);
        TextInputLayout textLocation2 = (TextInputLayout) findViewById(R.id.input_location);
        button = (Button) findViewById(R.id.buttonGo);
        RadioButton pickup = (RadioButton) findViewById(R.id.pickup);
        RadioButton delivery = (RadioButton) findViewById(R.id.Delivery);

        pickup.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf"));
        textLocation1.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf"));
        textLocation2.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf"));
        delivery.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf"));
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Bold.otf"));
        editSearch.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf"));
        button.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf"));

        button.setOnClickListener(this);
        editSearch.setOnClickListener(this);
        textLocation1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonGo:
                int id = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(id);
                type = radioButton.getText().toString();
                Intent intent = new Intent(ChooseLocality.this, ProductActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
                break;
            case R.id.editWorkLocation:

                zzzG();

                break;
            case R.id.textLocation1:
                break;
        }
    }

    private void zzzG() {
        int var1 = -1;

        try {
            Intent var2 = (new PlaceAutocomplete.IntentBuilder(2)).setBoundsBias(this.zzaRk).setFilter(this.zzaRl).
                    zzeq(this.editSearch.getText().toString()).zzig(1).build(ChooseLocality.this);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for (int i = 0, len = permissions.length; i < len; i++) {
            String permission = permissions[i];
            if (ActivityCompat.checkSelfPermission(ChooseLocality.this, permission) == PackageManager.PERMISSION_GRANTED) {
                if (requestCode == MULTIPLE_PERMISSIONS) {
                    Toast.makeText(ChooseLocality.this, "Permissions granted", Toast.LENGTH_SHORT).show();
                } else if (ActivityCompat.checkSelfPermission(ChooseLocality.this, permission) == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        Toast.makeText(ChooseLocality.this, "Go to Settings and Grant the permission to use this feature.", Toast.LENGTH_SHORT).show();
                        // User selected the Never Ask Again Option
                        if (ChooseLocality.this == null) {
                            return;
                        }
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent i = new Intent();
                                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + getPackageName()));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(i);
                            }
                        }, 3000);


                    } else {
                        Toast.makeText(ChooseLocality.this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }

                }
            }}}
   
}
