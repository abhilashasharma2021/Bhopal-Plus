package com.bhopalplus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhopalplus.Data.TeleConsultationData;
import com.bhopalplus.databinding.RowTeleConsultationLayoutBinding;
import java.util.List;

public class TeleAdapter extends RecyclerView.Adapter<TeleAdapter.MyViewHolder> {


    private Context mContext;
    private List<TeleConsultationData> teleData;

    public TeleAdapter(Context mContext, List<TeleConsultationData> teleData) {
        this.mContext = mContext;
        this.teleData = teleData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowTeleConsultationLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TeleConsultationData modelObject = teleData.get(position);

        if (modelObject != null) {
            holder.rowTeleConsultationLayoutBinding.txName.setText(modelObject.getName());
            holder.rowTeleConsultationLayoutBinding.txAddress.setText(modelObject.getAddress());
              holder.rowTeleConsultationLayoutBinding.txCode.setText(modelObject.getCode()+ "-");
            holder.rowTeleConsultationLayoutBinding.txNumber.setText(modelObject.getMobile());



            holder.rowTeleConsultationLayoutBinding.ivCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+modelObject.getCode()+modelObject.getMobile()));
                    mContext.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return teleData == null ? 0 : teleData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowTeleConsultationLayoutBinding rowTeleConsultationLayoutBinding;

        public MyViewHolder(RowTeleConsultationLayoutBinding rowTeleConsultationLayoutBinding) {
            super(rowTeleConsultationLayoutBinding.getRoot());
            this.rowTeleConsultationLayoutBinding = rowTeleConsultationLayoutBinding;
        }

    }
}
