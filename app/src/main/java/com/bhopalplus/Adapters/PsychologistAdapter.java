package com.bhopalplus.Adapters;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bhopalplus.Data.ContactPsychologistData;
import com.bhopalplus.databinding.RowContactPsychologistLayoutBinding;
import java.util.List;

public class PsychologistAdapter extends RecyclerView.Adapter<PsychologistAdapter.MyViewHolder> {


    private Context mContext;
    private List<ContactPsychologistData> psychologistData;

    public PsychologistAdapter(Context mContext, List<ContactPsychologistData> psychologistData) {
        this.mContext = mContext;
        this.psychologistData = psychologistData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(RowContactPsychologistLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ContactPsychologistData modelObject = psychologistData.get(position);

        if (modelObject != null) {
            holder.rowContactPsychologistLayoutBinding.txName.setText(modelObject.getName());
            holder.rowContactPsychologistLayoutBinding.txAddress.setText(modelObject.getAddress());
              holder.rowContactPsychologistLayoutBinding.txCode.setText(modelObject.getCode()+ "-");
            holder.rowContactPsychologistLayoutBinding.txNumber.setText(modelObject.getMobile());



            holder.rowContactPsychologistLayoutBinding.ivCall.setOnClickListener(new View.OnClickListener() {
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
        return psychologistData == null ? 0 : psychologistData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowContactPsychologistLayoutBinding rowContactPsychologistLayoutBinding;

        public MyViewHolder(RowContactPsychologistLayoutBinding rowContactPsychologistLayoutBinding) {
            super(rowContactPsychologistLayoutBinding.getRoot());
            this.rowContactPsychologistLayoutBinding = rowContactPsychologistLayoutBinding;
        }

    }
}
