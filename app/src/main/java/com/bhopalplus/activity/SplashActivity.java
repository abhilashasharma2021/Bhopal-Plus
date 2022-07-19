package com.bhopalplus.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;


import android.text.Html;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.bhopalplus.MainActivity;
import com.bhopalplus.R;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.Constants;
import com.bhopalplus.utils.GpsUtils;
import com.bhopalplus.utils.SharedHelper;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;


public class SplashActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    Handler mHandler;
  String userId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        findViewById(R.id.splash_logo).startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_from_top));
        findViewById(R.id.tv_splash).startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_from_bottom));




        userId = SharedHelper.getKey(getApplicationContext(), AppConstats.USER_ID);

        Dexter.withContext(SplashActivity.this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CONTACTS
                ).withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {


                                if (userId.equals("")){

                                    startActivity(new Intent(SplashActivity.this,LoginActivity .class));
                                    finish();
                                }else {

                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    finish();
                                }

                            }
                        },1000);


                    }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        token.continuePermissionRequest();


                    }
                }). withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


     /*   mHandler = new Handler(Looper.myLooper());

        findViewById(R.id.splash_logo).startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_from_top));
        findViewById(R.id.tv_splash).startAnimation(AnimationUtils.loadAnimation(this, R.anim.translate_from_bottom));
     //   findViewById(R.id.splash_progress_bar).startAnimation(AnimationUtils.loadAnimation(this, R.anim.fragment_fade_enter));
        ((TextView) findViewById(R.id.tv_splash)).setText(
                Html.fromHtml(
                        "<b>Bhopal Smart City Development Corporation Limited</b>"
                )
        );
       checkLocationPermissions();*/
    }

 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult","running");
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            openMainActivity();
        }
        if (requestCode == 1000 && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, getText(R.string.location_permission), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //*********************Location things********************************


    private void checkLocationPermissions() {
        //Log.e("checkPermissions","running");
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                checkForGPS();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(SplashActivity.this, getText(R.string.permission_denied_location), Toast.LENGTH_SHORT).show();
            }
        };

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getText(R.string.app_name)+" collects location data of user or employee for providing location tracking to the admin, even when app is closed. ");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                TedPermission.create()
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage(getString(R.string.permission_denied_location))
                        .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                        .check();
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            dialog.show();
        }
        else {
            TedPermission.create()
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage(getString(R.string.permission_denied_location))
                    .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                    .check();
        }

    }

    public void checkForGPS() {
        new GpsUtils(SplashActivity.this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                if (isGPSEnable){
                    openMainActivity();
                }
            }
        });
    }

    void openMainActivity(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                if (sharedPref.getBoolean(Constants.IS_LOGGED_IN_SP,false))
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                else
                  //  startActivity(new Intent(SplashActivity.this, ContactDetailsActivity.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);
                //mHandler.postDelayed(this, 500);
            }
        }, 2000);
    }*/




