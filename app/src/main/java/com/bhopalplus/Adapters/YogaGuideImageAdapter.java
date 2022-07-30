package com.bhopalplus.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhopalplus.Data.YogaGuideImageData;
import com.bhopalplus.databinding.RowYogaGuideImageLayoutBinding;
import com.bumptech.glide.Glide;
import java.util.List;

public class YogaGuideImageAdapter extends RecyclerView.Adapter<YogaGuideImageAdapter.MyViewHolder> {


    private Context mContext;
    private List<YogaGuideImageData> imgData;

    public YogaGuideImageAdapter(Context mContext, List<YogaGuideImageData> imgData) {
        this.mContext = mContext;
        this.imgData = imgData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowYogaGuideImageLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        YogaGuideImageData modelObject = imgData.get(position);

        if (modelObject != null) {


            Log.e("fgfgfg", "onBindViewHolder: " + modelObject.getPath());
            try {
                    Glide.with(mContext).load(modelObject.getPath()).into(holder.rowYogaGuideImageLayoutBinding.ivGuide);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public int getItemCount() {
        return imgData == null ? 0 : imgData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowYogaGuideImageLayoutBinding rowYogaGuideImageLayoutBinding;

        public MyViewHolder(RowYogaGuideImageLayoutBinding rowYogaGuideImageLayoutBinding) {
            super(rowYogaGuideImageLayoutBinding.getRoot());
            this.rowYogaGuideImageLayoutBinding = rowYogaGuideImageLayoutBinding;
        }

    }
}
