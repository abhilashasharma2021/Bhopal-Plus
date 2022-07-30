package com.bhopalplus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;
import com.bhopalplus.Adapters.YogaGuideImageAdapter;
import com.bhopalplus.Adapters.YogaGuideVideoAdapter;
import com.bhopalplus.Data.YogaGuideImageData;
import com.bhopalplus.MainActivity;
import com.bhopalplus.Model.YogaGuideModel;
import com.bhopalplus.R;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.ActivityYogaGuideBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.MyDialog.CustomDialog;
import com.bhopalplus.utils.MyDialog.DialogInterface;
import com.bhopalplus.utils.SharedHelper;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YogaGuideActivity extends AppCompatActivity {
    ActivityYogaGuideBinding binding;
    String getUserToken = "";
    YogaGuideImageAdapter adapterImg;
    YogaGuideVideoAdapter adapterVideo;
    List<YogaGuideImageData> guideImgList;
    List<YogaGuideImageData> guideVideoList;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.LayoutManager layoutManagerVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityYogaGuideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getUserToken = SharedHelper.getKey(YogaGuideActivity.this, AppConstats.USER_TOKEN);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(YogaGuideActivity.this, MainActivity.class));
                finish();

            }
        });


      //  show_Guide_Image();
      //  show_Guide_Video();
        binding.rvYoga.setLayoutManager(new LinearLayoutManager(YogaGuideActivity.this));
        layoutManager = new LinearLayoutManager(YogaGuideActivity.this, LinearLayoutManager.VERTICAL, false);

        binding.rvYogaVideo.setLayoutManager(new LinearLayoutManager(YogaGuideActivity.this));
        layoutManagerVideo = new LinearLayoutManager(YogaGuideActivity.this, LinearLayoutManager.VERTICAL, false);

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


    private void show_Guide_Image() {
        final DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, YogaGuideActivity.this);

        Call<YogaGuideModel> call = APIClient.getAPIClient().showYogaGuide("Bearer " + getUserToken);
        call.enqueue(new Callback<YogaGuideModel>() {
            @Override
            public void onResponse(@NonNull Call<YogaGuideModel> call, @NonNull Response<YogaGuideModel> response) {
                Log.e("fgfdgfd", response.toString());
                if (!response.isSuccessful()) {
                    dialogInterface.hideDialog();


                } else {
                    YogaGuideModel model = response.body();

                    if (model != null) {
                        if (model.getResult()) {
                            guideImgList = new ArrayList<>();
                            binding.txTittle.setText(model.getData().getTitle());
                            binding.txDate.setText(model.getData().getDate());
                            binding.txTime.setText(model.getData().getTime());
                            binding.txCompanyName.setText(model.getData().getContactAddress());
                            binding.txNumber.setText(model.getData().getContactNumber());
                            binding.txAgenda.setText(model.getData().getDescriptionAgenda());
                            binding.ivCall.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                    intent.setData(Uri.parse("tel:" + model.getData().getContactNumber()));
                                    startActivity(intent);
                                }
                            });

                            List<YogaGuideModel.Data.MultipleImage> dataList = model.getData().getMultipleImage();
                            for (YogaGuideModel.Data.MultipleImage data : dataList) {
                                YogaGuideImageData itemData = new YogaGuideImageData();
                                for (int i = 0; i < dataList.size(); i++) {
                                    itemData.setPath(data.getPath());


                                }
                                guideImgList.add(itemData);
                            }

                            dialogInterface.hideDialog();
                            if (YogaGuideActivity.this != null) {
                                adapterImg = new YogaGuideImageAdapter(YogaGuideActivity.this, guideImgList);
                                binding.rvYoga.setAdapter(adapterImg);
                            }
                        } else {
                            dialogInterface.hideDialog();
                            Toast.makeText(YogaGuideActivity.this, "SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<YogaGuideModel> call, @NonNull Throwable t) {
                Log.e("gtrggt", t.getMessage(), t);
                dialogInterface.hideDialog();
            }
        });
    }
    private void show_Guide_Video() {
        final DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, YogaGuideActivity.this);

        Call<YogaGuideModel> call = APIClient.getAPIClient().showYogaGuide("Bearer " + getUserToken);
        call.enqueue(new Callback<YogaGuideModel>() {
            @Override
            public void onResponse(@NonNull Call<YogaGuideModel> call, @NonNull Response<YogaGuideModel> response) {
                Log.e("dfdsfdfd", response.toString());
                if (!response.isSuccessful()) {
                    dialogInterface.hideDialog();


                } else {
                    YogaGuideModel model = response.body();

                    if (model != null) {
                        if (model.getResult()) {
                            guideVideoList = new ArrayList<>();
                            List<YogaGuideModel.Data.MultipleYoutubeLink> dataList = model.getData().getMultipleYoutubeLink();
                            for (YogaGuideModel.Data.MultipleYoutubeLink data : dataList) {
                                YogaGuideImageData itemData = new YogaGuideImageData();
                                for (int i = 0; i < dataList.size(); i++) {
                                    itemData.setPath(data.getUrl());


                                }
                                guideVideoList.add(itemData);
                            }

                            dialogInterface.hideDialog();
                            if (YogaGuideActivity.this != null) {
                                adapterVideo = new YogaGuideVideoAdapter(YogaGuideActivity.this, guideVideoList);
                                binding.rvYogaVideo.setAdapter(adapterVideo);
                            }
                        } else {
                            dialogInterface.hideDialog();
                            Toast.makeText(YogaGuideActivity.this, "SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<YogaGuideModel> call, @NonNull Throwable t) {
                Log.e("gtrggt", t.getMessage(), t);
                dialogInterface.hideDialog();
            }
        });
    }

}



