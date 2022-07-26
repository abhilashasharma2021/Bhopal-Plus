package com.bhopalplus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.bhopalplus.MainActivity;
import com.bhopalplus.R;
import com.bhopalplus.databinding.ActivityLoginBinding;
import com.bhopalplus.databinding.ActivityYogaGuideBinding;

public class YogaGuideActivity extends AppCompatActivity {
ActivityYogaGuideBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityYogaGuideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YogaGuideActivity.this, MainActivity.class));
                finish();

            }
        });

        binding.txLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cdVideo.setVisibility(View.VISIBLE);
                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_slide_right);
                binding.cdVideo.setLayoutAnimation(animationController);
             //   overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

            }
        });
                     /*(Activity) context).overridePendingTransition(
                        R.anim.left_to_right, R.anim.right_to_left);*/


        binding.ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cdVideo.setVisibility(View.GONE);

            }
        });
        binding.btLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cdVideo.setVisibility(View.VISIBLE);
                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_slide_right);
                binding.cdVideo.setLayoutAnimation(animationController);

            }
        });



    }
}