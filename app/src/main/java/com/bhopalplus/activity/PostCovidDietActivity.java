package com.bhopalplus.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.bhopalplus.MainActivity;
import com.bhopalplus.Model.PostCovidDietModel;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.ActivityPostCovidDietBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.SharedHelper;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostCovidDietActivity extends AppCompatActivity {
    ActivityPostCovidDietBinding binding;
    String getUserToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostCovidDietBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getUserToken = SharedHelper.getKey(PostCovidDietActivity.this, AppConstats.USER_TOKEN);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostCovidDietActivity.this, MainActivity.class));
                finish();

            }
        });
        show_Diet();
    }


    private void show_Diet(){

        Call<PostCovidDietModel> call = APIClient.getAPIClient().showDiet("Bearer " + getUserToken);
        call.enqueue(new Callback< PostCovidDietModel>() {
            @Override
            public void onResponse(@NonNull Call< PostCovidDietModel> call, @NonNull Response< PostCovidDietModel> response) {
                Log.e("grgtrgtg", response.toString());
                if (!response.isSuccessful()) {
                    Toast.makeText(PostCovidDietActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                }
                else {
                    PostCovidDietModel model = response.body();

                    try {
                        Glide.with(PostCovidDietActivity.this).load(model.getData().getPath()).into(binding.myZoomageView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }




            }

            @Override
            public void onFailure(@NonNull Call< PostCovidDietModel> call, @NonNull Throwable t) {
                Log.e("dgfdgfdg", t.getMessage(), t);
            }
        });

    }

}