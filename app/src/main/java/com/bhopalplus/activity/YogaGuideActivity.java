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

        binding.rlVisible.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                binding.cdVideo.setVisibility(View.VISIBLE);
                binding.rlHide.setVisibility(View.VISIBLE);
                binding.rlVisible.setVisibility(View.GONE);
                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_slide_right);
                binding.cdVideo.setLayoutAnimation(animationController);

            }
        });


        binding.rlHide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                binding.rlVisible.setVisibility(View.VISIBLE);
                binding.rlHide.setVisibility(View.GONE);
                binding.cdVideo.setVisibility(View.GONE);
                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_anim_slide_left);
                binding.cdVideo.setLayoutAnimation(animationController);

            }
        });

       /* (Activity) context).overridePendingTransition(
        R.anim.left_to_right, R.anim.right_to_left);*/
        binding.btLearnVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cdVideo.setVisibility(View.VISIBLE);
                binding.btLearnHide.setVisibility(View.VISIBLE);
                binding.btLearnVisible.setVisibility(View.GONE);
                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_slide_right);
                binding.cdVideo.setLayoutAnimation(animationController);

            }
        });

        binding.btLearnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cdVideo.setVisibility(View.GONE);
                binding.btLearnVisible.setVisibility(View.VISIBLE);
                binding.btLearnHide.setVisibility(View.GONE);
                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_animation_slide_right);
                binding.cdVideo.setLayoutAnimation(animationController);

            }
        });



        binding.ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cdVideo.setVisibility(View.GONE);
                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layout_anim_slide_left);
                binding.cdVideo.setLayoutAnimation(animationController);
            }
        });

    }
}