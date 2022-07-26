package com.bhopalplus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bhopalplus.MainActivity;
import com.bhopalplus.R;
import com.bhopalplus.databinding.ActivityContactPsychologistBinding;
import com.bhopalplus.databinding.ActivityTeleConsultationBinding;

public class ContactPsychologistActivity extends AppCompatActivity {
ActivityContactPsychologistBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactPsychologistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactPsychologistActivity.this, MainActivity.class));
                finish();

            }
        });
    }
}