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
import com.bhopalplus.databinding.ActivityTeleConsultationBinding;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.MyDialog.CustomDialog;
import com.bhopalplus.utils.MyDialog.DialogInterface;
import com.bhopalplus.utils.SharedHelper;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeleConsultation extends AppCompatActivity {
ActivityTeleConsultationBinding binding;
String getUserToken="";
List<TeleConsultationData>consultationList=new ArrayList<>();
TeleAdapter adapter;
RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeleConsultationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getUserToken = SharedHelper.getKey(TeleConsultation.this, AppConstats.USER_TOKEN);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeleConsultation.this, MainActivity.class));
                finish();

            }
        });
        binding.rvTele.setLayoutManager(new LinearLayoutManager(TeleConsultation.this));
        layoutManager = new LinearLayoutManager(TeleConsultation.this, LinearLayoutManager.VERTICAL, false);
        consultation();
    }


    private void consultation() {

        final DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, TeleConsultation.this);
        Call<TeleConsultationModel> call = APIClient.getAPIClient().teleConsultation("Bearer " + getUserToken);
        call.enqueue(new Callback<TeleConsultationModel>() {
            @Override
            public void onResponse(Call<TeleConsultationModel> call, Response<TeleConsultationModel> response) {
                if (!response.isSuccessful()) {
                    dialogInterface.hideDialog();


                } else {
                    TeleConsultationModel model = response.body();

                    if (model != null) {
                        if (model.getResult()) {
                            consultationList = new ArrayList<>();
                            List<TeleConsultationModel.Datum> dataList = model.getData();
                            for (TeleConsultationModel.Datum data : dataList) {
                                TeleConsultationData itemData = new TeleConsultationData();
                                for (int i = 0; i < dataList.size(); i++) {
                                    itemData.setName(data.getName());
                                    itemData.setAddress(data.getAddress());
                                    itemData.setCode(data.getPhoneCode());
                                    itemData.setMobile(data.getNumber());

                                }
                                consultationList.add(itemData);
                            }

                            dialogInterface.hideDialog();
                            if (TeleConsultation.this!= null) {
                                adapter = new TeleAdapter(TeleConsultation.this,consultationList);
                                binding.rvTele.setAdapter(adapter);
                            }
                        } else {
                            dialogInterface.hideDialog();
                            Toast.makeText(TeleConsultation.this, "SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
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