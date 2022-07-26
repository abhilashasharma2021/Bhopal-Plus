package com.bhopalplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bhopalplus.activity.SplashActivity;
import com.bhopalplus.fragment.AboutUsFrag;
import com.bhopalplus.fragment.ComplaintFragment;
import com.bhopalplus.fragment.FeedbackFrag;
import com.bhopalplus.fragment.HomeFrag;
import com.bhopalplus.fragment.UserProfileFrag;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.SharedHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import softpro.naseemali.ShapedNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    public static DrawerLayout drawerlayout;
    Button btLogout;
    ShapedNavigationView nav_view;
    BottomNavigationView bottomNav;
    ImageView ivCancel;
    RelativeLayout rlHome, rlProfile, rlComplaint, rlFeedback, rlAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        btLogout = findViewById(R.id.btLogout);
        rlHome = findViewById(R.id.rlHome);

        drawerlayout = findViewById(R.id.drawerlayout);
        nav_view = findViewById(R.id.nav_view);
        ivCancel = findViewById(R.id.ivCancel);
        rlProfile = findViewById(R.id.rlProfile);
        rlComplaint = findViewById(R.id.rlComplaint);
        rlFeedback = findViewById(R.id.rlFeedback);
        rlAbout = findViewById(R.id.rlAbout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFrag()).commit();
        }


        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.closeDrawer(GravityCompat.START);
            }
        });


        rlHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFrag()).commit();
                drawerlayout.closeDrawer(GravityCompat.START);
            }
        });
        rlProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserProfileFrag()).commit();
                drawerlayout.closeDrawer(GravityCompat.START);
            }
        });


        rlComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ComplaintFragment()).commit();
                drawerlayout.closeDrawer(GravityCompat.START);
            }
        });

        rlFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FeedbackFrag()).commit();
                drawerlayout.closeDrawer(GravityCompat.START);
            }
        });

        rlAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutUsFrag()).commit();
                drawerlayout.closeDrawer(GravityCompat.START);
            }
        });


        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });


        bottomNav.setOnNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFrag()).commit();
                drawerlayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_complaint:

                //  item.getIcon().setTint(ContextCompat.getColor());
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ComplaintFragment()).commit();
                drawerlayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_profile:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserProfileFrag()).commit();
                drawerlayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_feedback:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FeedbackFrag()).commit();
                drawerlayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_about:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutUsFrag()).commit();
                drawerlayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    public void logout() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogl_logout_layout);
        dialog.setCancelable(true);
        Button btn_yes = dialog.findViewById(R.id.btn_yes);
        Button btn_no = dialog.findViewById(R.id.btn_no);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_ID, "");
                SharedHelper.putKey(getApplicationContext(), AppConstats.USER_TOKEN, "");
                startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                finish();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }


}
