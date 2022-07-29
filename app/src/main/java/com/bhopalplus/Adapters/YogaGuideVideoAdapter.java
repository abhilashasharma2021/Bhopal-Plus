package com.bhopalplus.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bhopalplus.Data.YogaGuideImageData;
import com.bhopalplus.databinding.RowYogaGuideImageLayoutBinding;
import com.bhopalplus.databinding.RowYogaGuideVideoLayoutBinding;
import com.bumptech.glide.Glide;

import java.util.List;

public class YogaGuideVideoAdapter extends RecyclerView.Adapter<YogaGuideVideoAdapter.MyViewHolder> {


    private Context mContext;
    private List<YogaGuideImageData> imgData;

    public YogaGuideVideoAdapter(Context mContext, List<YogaGuideImageData> imgData) {
        this.mContext = mContext;
        this.imgData = imgData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowYogaGuideVideoLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        YogaGuideImageData modelObject = imgData.get(position);

        if (modelObject != null) {


            Log.e("gfdgfdgfd", "onBindViewHolder: " + modelObject.getPath());

            Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(modelObject.getPath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
           holder.rowYogaGuideVideoLayoutBinding.ivVideo.setImageBitmap(thumbnail);

        }


    }

    @Override
    public int getItemCount() {
        return imgData == null ? 0 : imgData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowYogaGuideVideoLayoutBinding rowYogaGuideVideoLayoutBinding;

        public MyViewHolder(RowYogaGuideVideoLayoutBinding rowYogaGuideVideoLayoutBinding) {
            super(rowYogaGuideVideoLayoutBinding.getRoot());
            this.rowYogaGuideVideoLayoutBinding = rowYogaGuideVideoLayoutBinding;
        }

    }
}
