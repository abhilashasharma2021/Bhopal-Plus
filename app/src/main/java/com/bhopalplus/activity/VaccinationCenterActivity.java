package com.bhopalplus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.bhopalplus.Adapters.VaccinationCenterAdapter;
import com.bhopalplus.Data.VaccinationCenterData;
import com.bhopalplus.MainActivity;
import com.bhopalplus.Model.VaccinationCenterModel;
import com.bhopalplus.R;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.ActivityVaccinationCenterBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.MyDialog.CustomDialog;
import com.bhopalplus.utils.MyDialog.DialogInterface;
import com.bhopalplus.utils.SharedHelper;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VaccinationCenterActivity extends AppCompatActivity {
    ActivityVaccinationCenterBinding binding;
    String getUserToken = "";
    List<VaccinationCenterData>vaccineList=new ArrayList<>();
    VaccinationCenterAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVaccinationCenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getUserToken = SharedHelper.getKey(VaccinationCenterActivity.this, AppConstats.USER_TOKEN);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VaccinationCenterActivity.this, MainActivity.class));
                finish();

            }
        });
        binding.rvCenter.setLayoutManager(new LinearLayoutManager(VaccinationCenterActivity.this));
        layoutManager = new LinearLayoutManager(VaccinationCenterActivity.this, LinearLayoutManager.VERTICAL, false);
        show_VaccinationCenter();
    }


    private void show_VaccinationCenter() {

        final DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, VaccinationCenterActivity.this);
        Call<VaccinationCenterModel> call = APIClient.getAPIClient().showVaccinationCenter("Bearer " + getUserToken);

        call.enqueue(new Callback<VaccinationCenterModel>() {
            @Override
            public void onResponse(Call<VaccinationCenterModel> call, Response<VaccinationCenterModel> response) {
                if (!response.isSuccessful()) {
                    dialogInterface.hideDialog();


                } else {
                    VaccinationCenterModel model = response.body();

                    if (model != null) {
                        if (model.getResult()) {
                            vaccineList = new ArrayList<>();
                            binding.btSignIn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    startActivity(new Intent(VaccinationCenterActivity.this,WebviewSignInVaccinationCenterActivity.class));
                                    SharedHelper.putKey(getApplicationContext(), AppConstats.SIGNIN_URL,model.getUrl());
                                }
                            });

                            List<VaccinationCenterModel.Datum> dataList = model.getData();
                            for (VaccinationCenterModel.Datum data : dataList) {
                                VaccinationCenterData itemData = new VaccinationCenterData();
                                for (int i = 0; i < dataList.size(); i++) {
                                    itemData.setName(data.getName());
                                    itemData.setAddress(data.getAddress());



                                }
                                vaccineList.add(itemData);
                            }

                            dialogInterface.hideDialog();
                            if (VaccinationCenterActivity.this!= null) {
                                adapter = new VaccinationCenterAdapter(VaccinationCenterActivity.this,vaccineList);
                                binding.rvCenter.setAdapter(adapter);
                            }
                        } else {
                            dialogInterface.hideDialog();
                            Toast.makeText(VaccinationCenterActivity.this, "SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }







            }

            @Override
            public void onFailure(Call<VaccinationCenterModel> call, Throwable t) {
                Log.e("jhgjyj", t.getMessage() + "msg");
                dialogInterface.hideDialog();
            }
        });

    }
}