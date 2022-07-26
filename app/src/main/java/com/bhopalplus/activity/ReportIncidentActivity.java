package com.bhopalplus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhopalplus.MainActivity;
import com.bhopalplus.R;
import com.bhopalplus.databinding.ActivityReportIncidentBinding;
import com.bhopalplus.databinding.ActivityTeleConsultationBinding;

public class ReportIncidentActivity extends AppCompatActivity {
ActivityReportIncidentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportIncidentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReportIncidentActivity.this, MainActivity.class));
                finish();

            }
        });
    }
}