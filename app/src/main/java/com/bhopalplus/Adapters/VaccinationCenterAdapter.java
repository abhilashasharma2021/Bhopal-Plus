package com.bhopalplus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhopalplus.Data.VaccinationCenterData;
import com.bhopalplus.databinding.RowVaccineCenterLayoutBinding;
import java.util.List;

public class VaccinationCenterAdapter extends RecyclerView.Adapter<VaccinationCenterAdapter.MyViewHolder> {


    private Context mContext;
    private List<VaccinationCenterData> vaccinationList;

    public VaccinationCenterAdapter(Context mContext, List<VaccinationCenterData> vaccinationList) {
        this.mContext = mContext;
        this.vaccinationList = vaccinationList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowVaccineCenterLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VaccinationCenterData modelObject = vaccinationList.get(position);

        if (modelObject != null) {
            holder.rowVaccineCenterLayoutBinding.txName.setText(modelObject.getName());
            holder.rowVaccineCenterLayoutBinding.txAddress.setText(modelObject.getAddress());




        }


    }

    @Override
    public int getItemCount() {
        return vaccinationList == null ? 0 : vaccinationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowVaccineCenterLayoutBinding rowVaccineCenterLayoutBinding;

        public MyViewHolder(RowVaccineCenterLayoutBinding rowVaccineCenterLayoutBinding) {
            super(rowVaccineCenterLayoutBinding.getRoot());
            this.rowVaccineCenterLayoutBinding = rowVaccineCenterLayoutBinding;
        }

    }
}
