package com.bhopalplus.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.bhopalplus.MainActivity;
import com.bhopalplus.Model.MayorExpressModel;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.ActivityMayorExpressBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.SharedHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MayorExpressActivity extends AppCompatActivity {
    ActivityMayorExpressBinding binding;
    String getUserToken="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMayorExpressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getUserToken = SharedHelper.getKey(MayorExpressActivity.this, AppConstats.USER_TOKEN);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MayorExpressActivity.this, MainActivity.class));
                finish();

            }
        });
        show_Guideline();

    }


    private void show_Guideline(){

        Call<MayorExpressModel> call = APIClient.getAPIClient().showMayor("Bearer " + getUserToken);
        call.enqueue(new Callback< MayorExpressModel>() {
            @Override
            public void onResponse(@NonNull Call< MayorExpressModel> call, @NonNull Response< MayorExpressModel> response) {
                Log.e("grgtrgtg", response.toString());
                if (!response.isSuccessful()) {
                    Toast.makeText(MayorExpressActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                }
                else {
                    MayorExpressModel model = response.body();
                    binding.txGuideline.setText(Html.fromHtml(Html.fromHtml(model.getData().getDescription()).toString()));
                }




            }

            @Override
            public void onFailure(@NonNull Call< MayorExpressModel> call, @NonNull Throwable t) {
                Log.e("dgfdgfdg", t.getMessage(), t);
            }
        });

    }
}