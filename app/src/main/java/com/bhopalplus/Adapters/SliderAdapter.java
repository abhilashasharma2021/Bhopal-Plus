package com.bhopalplus.Adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bhopalplus.Data.SliderItemData;
import com.bhopalplus.R;
import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    List<SliderItemData> dataAdapters;

    public SliderAdapter(List<SliderItemData> getDataAdapter, Context context) {
        this.context = context;
        this.dataAdapters = getDataAdapter;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        final SliderItemData dataAdapterOBJ = dataAdapters.get(position);

        if (dataAdapterOBJ != null) {


            Log.e("TAG", "onBindViewHolder: "+dataAdapterOBJ.getSliderImage());
            try {
               Glide.with(context).load(dataAdapterOBJ.getSliderImage()).into(viewHolder.imageViewBackground);
            } catch (Exception e) {
                e.printStackTrace();
            }



        }
    }






        @Override
    public int getCount() {
        //slider view count could be dynamic size
        return dataAdapters.size();
    }

    class SliderAdapterVH extends SliderAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);

        }
    }
    }

