package com.bhopalplus.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhopalplus.Data.HomeItemData;
import com.bhopalplus.Model.HomeItemModel;
import com.bhopalplus.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {


   List<HomeItemData> servicesList;
    Context context;

    public HomeAdapter(List<HomeItemData> servicesList, Context context) {
        this.servicesList = servicesList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_services_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeItemData homemodelobj=servicesList.get(position);
        Log.e("yukmk", "check: " +homemodelobj);

        if (homemodelobj !=null){

            Log.e("HomeAdapter", "onBindViewHolder: " +homemodelobj.getServiceImage());
            try {
                Glide.with(context).load(homemodelobj.getServiceImage()).into(holder.ivService);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.e("fdbfb", "onBindViewHolder: " +homemodelobj.getServiceName());

            holder.txService.setText(homemodelobj.getServiceName());





            }




        }


    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivService;
        TextView txService;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivService=itemView.findViewById(R.id.ivService);
            txService=itemView.findViewById(R.id.txService);


        }
    }



}

