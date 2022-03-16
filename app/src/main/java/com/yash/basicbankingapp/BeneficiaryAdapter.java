package com.yash.basicbankingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yash.basicbankingapp.Models.model;

import java.util.List;

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.myViewholder>
{
    private Context context;
    private List<model> dataholder;
    String senderID;

    public BeneficiaryAdapter(Context context, List<model> dataholder) {
        this.context = context;
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beneficiary_list_item, parent, false);
        return new myViewholder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewholder holder, int position) {
        holder.Image.setText(String.valueOf(dataholder.get(position).getName().charAt(0)));
        holder.Name.setText(dataholder.get(position).getName());
        holder.phone.setText(dataholder.get(position).getPhone());

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }


    class myViewholder extends RecyclerView.ViewHolder{
        TextView Image, Name, phone;

        public myViewholder( View itemView, Context context) {
            super(itemView);
            Image = itemView.findViewById(R.id.user_image);
            Name = itemView.findViewById(R.id.user_name);
            phone = itemView.findViewById(R.id.user_phone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    model md = dataholder.get(position);
                    Intent intent = new Intent(context, TransferActivity.class);
                    intent.putExtra("SenderId",senderID);
                    intent.putExtra("ReceiverId",md.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}

