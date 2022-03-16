package com.yash.basicbankingapp.Models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yash.basicbankingapp.R;

import java.util.List;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.myViewHolder>
{
    private Context context;
    private List<model> dataholder;

    public AllUserAdapter(Context context, List<model> dataholder) {
        this.context = context;
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item_list, parent, false);
        return new myViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.Image.setText(String.valueOf(dataholder.get(position).getName().charAt(0)));
        holder.Name.setText(dataholder.get(position).getName());
        holder.phone.setText(dataholder.get(position).getPhone());
        holder.Balance.setText("Rs. "+ dataholder.get(position).getBalance());
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView Image, Name, phone, Balance;

        public myViewHolder( View itemView, Context context) {
            super(itemView);
            Image = itemView.findViewById(R.id.user_image);
            Name = itemView.findViewById(R.id.user_name);
            phone = itemView.findViewById(R.id.user_phone);
            Balance = itemView.findViewById(R.id.user_balance);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    model md = dataholder.get(position);
                    Intent intent = new Intent(context, com.yash.basicbankingapp.UserDetailsActivity.class);
                    intent.putExtra("UserID",md.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
