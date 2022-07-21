package com.bhopalplus.fragment;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bhopalplus.Adapters.HomeAdapter;
import com.bhopalplus.Data.HomeItemData;
import com.bhopalplus.Model.HomeItemModel;
import com.bhopalplus.MainActivity;

import com.bhopalplus.R;
import com.bhopalplus.Retrofit.APIClient;
import com.bhopalplus.databinding.FragmentHomeBinding;

import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.MyDialog.CustomDialog;
import com.bhopalplus.utils.MyDialog.DialogInterface;
import com.bhopalplus.utils.ReturnErrorToast;
import com.bhopalplus.utils.SharedHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFrag extends Fragment {
    FragmentHomeBinding binding;
    View view;
    Context context;
    List<HomeItemData> serviceList;
    HomeAdapter adapter;
    String getUserToken="";
    RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        view = binding.getRoot();
        context = getActivity();
        getUserToken = SharedHelper.getKey(getActivity(), AppConstats.USER_TOKEN);
        binding.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0755 247 7770"));
                startActivity(intent);
            }
        });


        binding.rvService.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawerlayout.openDrawer(GravityCompat.START);
            }
        });


        show_Services();
        return view;
    }



    private void show_Services() {

        final DialogInterface dialogInterface = new CustomDialog();
        dialogInterface.showDialog(R.layout.pr_dialog, getActivity());
        Call<HomeItemModel> call = APIClient.getAPIClient().showServicesItem("Bearer " +getUserToken);
        call.enqueue(new Callback<HomeItemModel>() {
            @Override
            public void onResponse(Call<HomeItemModel> call, Response<HomeItemModel> response) {
                if (!response.isSuccessful()) {
                    dialogInterface.hideDialog();
                    showToast();

                } else {
                    HomeItemModel model = response.body();

                    if (model != null) {
                        if (model.getResult()) {
                            serviceList = new ArrayList<>();
                            List<HomeItemModel.Datum> dataList = model.getData();
                            for (HomeItemModel.Datum data : dataList) {
                                HomeItemData itemData = new HomeItemData();
                                for (int i = 0; i <dataList.size() ; i++) {
                                    itemData.setServiceId(String.valueOf(data.getId()));
                                    itemData.setServiceName(data.getTitle());
                                    itemData.setServiceImage(data.getPath());

                                }
                                serviceList.add(itemData);
                            }

                            dialogInterface.hideDialog();
                            if (getActivity() != null) {
                                adapter = new HomeAdapter(serviceList,getActivity() );
                                binding.rvService.setAdapter(adapter);
                            }
                        }else {
                            dialogInterface.hideDialog();
                            Toast.makeText(getActivity(),"SomeThing Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<HomeItemModel> call, Throwable t) {
                Log.e("eryruiyd", t.getMessage() + "msg");
                dialogInterface.hideDialog();
            }
        });


    }
    public void showToast() {
        LayoutInflater infl = getLayoutInflater();
        View layout = infl.inflate(R.layout.toast_layout, getActivity().findViewById(R.id.toast_layout_root));
        TextView text = layout.findViewById(R.id.text);
        text.setText(R.string.somethingwentwrong);
        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


}