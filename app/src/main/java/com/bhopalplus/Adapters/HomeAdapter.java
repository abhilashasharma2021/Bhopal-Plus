package com.bhopalplus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhopalplus.Data.HomeItemData;
import com.bhopalplus.Model.HomeItemModel;
import com.bhopalplus.R;
import com.bhopalplus.activity.ContactPsychologistActivity;
import com.bhopalplus.activity.FeverClinicActivity;
import com.bhopalplus.activity.FoodActivity;
import com.bhopalplus.activity.ReportIncidentActivity;
import com.bhopalplus.activity.TeleConsultation;
import com.bhopalplus.activity.YogaGuideActivity;
import com.bhopalplus.utils.AppConstats;
import com.bhopalplus.utils.SharedHelper;
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
        HomeItemData homemodelobj = servicesList.get(position);
        Log.e("yukmk", "check: " + homemodelobj);

        if (homemodelobj != null) {

            Log.e("HomeAdapter", "onBindViewHolder: " + homemodelobj.getServiceImage());
            try {
                Glide.with(context).load(homemodelobj.getServiceImage()).into(holder.ivService);
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.txService.setText(homemodelobj.getServiceName());


            if (servicesList.get(position).getServiceName().equals("Report Incident")) {

                holder.llView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, ReportIncidentActivity.class));
                    }
                });
            } else if (servicesList.get(position).getServiceName().equals("Yoga Guide")) {

                holder.llView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, YogaGuideActivity.class));
                    }
                });
            } else if (servicesList.get(position).getServiceName().equals("Tele Consultation")) {

                holder.llView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, TeleConsultation.class));
                    }
                });
            } else if (servicesList.get(position).getServiceName().equals("Contact Psychologist")) {

                holder.llView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, ContactPsychologistActivity.class));
                    }
                });
            }
            else if (servicesList.get(position).getServiceName().equals("Fever Clinic")) {

                holder.llView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, FeverClinicActivity.class));
                    }
                });
            }

            else if (servicesList.get(position).getServiceName().equals("Food")) {

                holder.llView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context, FoodActivity.class));
                    }
                });
            }
            else {

            }

        }
    }


    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivService;
        TextView txService;
        LinearLayout llView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivService = itemView.findViewById(R.id.ivService);
            txService = itemView.findViewById(R.id.txService);
            llView = itemView.findViewById(R.id.llView);


        }
    }


}

