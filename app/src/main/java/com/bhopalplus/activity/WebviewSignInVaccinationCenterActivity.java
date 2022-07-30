package com.bhopalplus.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.bhopalplus.databinding.ActivityWebviewSignInVaccinationCenterBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.SharedHelper;

public class WebviewSignInVaccinationCenterActivity extends AppCompatActivity {
ActivityWebviewSignInVaccinationCenterBinding binding;
String getUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebviewSignInVaccinationCenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getUrl = SharedHelper.getKey(WebviewSignInVaccinationCenterActivity.this, AppConstats.SIGNIN_URL);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WebviewSignInVaccinationCenterActivity.this, VaccinationCenterActivity.class));
                finish();

            }
        });
        binding.wb.getSettings().setJavaScriptEnabled(true);
        binding.wb.loadUrl(getUrl);
    }
}