package com.bhopalplus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhopalplus.MainActivity;
import com.bhopalplus.R;
import com.bhopalplus.databinding.ActivitySignUpBinding;
import com.bhopalplus.databinding.ActivityTeleConsultationBinding;

public class TeleConsultation extends AppCompatActivity {
ActivityTeleConsultationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeleConsultationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeleConsultation.this, MainActivity.class));
                finish();

            }
        });
    }
}