package com.bhopalplus.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.bhopalplus.Adapters.TeleAdapter;
import com.bhopalplus.Data.TeleConsultationData;
import com.bhopalplus.MainActivity;
import com.bhopalplus.Model.TeleConsultationModel;
import com.bhopalplus.R;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.ActivityFeverClinicBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.MyDialog.CustomDialog;
import com.bhopalplus.utils.MyDialog.DialogInterface;
import com.bhopalplus.utils.SharedHelper;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeverClinicActivity extends AppCompatActivity {
    ActivityFeverClinicBinding binding;
    String getUserToken = "";
    List<TeleConsultationData> clinicList=new ArrayList<>();
    TeleAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeverClinicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserToken = SharedHelper.getKey(FeverClinicActivity.this, AppConstats.USER_TOKEN);
        show_Clinc();
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FeverClinicActivity.this, MainActivity.class));
                finish();

            }
        });


        binding.rvClinic.setLayoutManager(new LinearLayoutManager(FeverClinicActivity.this));
        layoutManager = new LinearLayoutManager(FeverClinicActivity.this, LinearLayoutManager.VERTICAL, false);
    }

    private void show_Clinc() {

        final DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, FeverClinicActivity.this);
        Call<TeleConsultationModel> call = APIClient.getAPIClient().showFeverClinic("Bearer " + getUserToken);
        call.enqueue(new Callback<TeleConsultationModel>() {
            @Override
            public void onResponse(Call<TeleConsultationModel> call, Response<TeleConsultationModel> response) {
                if (!response.isSuccessful()) {
                    dialogInterface.hideDialog();


                } else {
                    TeleConsultationModel model = response.body();

                    if (model != null) {
                        if (model.getResult()) {
                            clinicList = new ArrayList<>();
                            List<TeleConsultationModel.Datum> dataList = model.getData();
                            for (TeleConsultationModel.Datum data : dataList) {
                                TeleConsultationData itemData = new TeleConsultationData();
                                for (int i = 0; i < dataList.size(); i++) {
                                    itemData.setName(data.getName());
                                    itemData.setAddress(data.getAddress());
                                    itemData.setCode(data.getPhoneCode());
                                    itemData.setMobile(data.getNumber());

                                }
                                clinicList.add(itemData);
                            }

                            dialogInterface.hideDialog();
                            if (FeverClinicActivity.this!= null) {
                                adapter = new TeleAdapter(FeverClinicActivity.this,clinicList);
                                binding.rvClinic.setAdapter(adapter);
                            }
                        } else {
                            dialogInterface.hideDialog();
                            Toast.makeText(FeverClinicActivity.this, "SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<TeleConsultationModel> call, Throwable t) {
                Log.e("regrgr", t.getMessage() + "msg");
                dialogInterface.hideDialog();
            }
        });

    }
}