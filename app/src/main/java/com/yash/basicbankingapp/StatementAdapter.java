package com.yash.basicbankingapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yash.basicbankingapp.DataBase.Database;
import com.yash.basicbankingapp.Models.model;

import java.util.List;

public class StatementAdapter extends RecyclerView.Adapter<StatementAdapter.myviewholder>
{
    private Context context;
    private List<model> dataholder;
    String ID ;

    public StatementAdapter(Context context, List<model> dataholder) {
        this.context = context;
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statement_list_item, parent, false);
        return new myviewholder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {

        String user1_id = dataholder.get(position).getSender_id();
        String user2_id = dataholder.get(position).getReceiver_id();
        String status =dataholder.get(position).getStatus();


        holder.amount.setText("Rs. " + dataholder.get(position).getAmount());
        holder.status.setText(dataholder.get(position).getStatus());
        holder.date.setText(dataholder.get(position).getDate());

        if (user1_id.equals(ID)) {
            Cursor cursor = new Database(context).readparticulardata(user1_id);
            while (cursor.moveToNext()) {
                holder.user1.setText(cursor.getString(1));
            }
            Cursor cursor1 = new Database(context).readparticulardata(user2_id);
            while (cursor1.moveToNext()) {
                holder.user2.setText(cursor1.getString(1));
            }

            if (status.equals( "Successful")){
                holder.status.setTextColor(Color.parseColor("#ff669900"));
            }
            else {
                holder.status.setTextColor(Color.parseColor("#ffcc0000"));
            }
            holder.amount.setTextColor(Color.RED);
        }
        else {
            Cursor cursor = new Database(context).readparticulardata(user1_id);
            while (cursor.moveToNext()) {
                holder.user2.setText(cursor.getString(1));
            }
            Cursor cursor1 = new Database(context).readparticulardata(user2_id);
            while (cursor1.moveToNext()) {
                holder.user1.setText(cursor1.getString(1));
            }

            if (status.equals( "Successful")){
                holder.status.setTextColor(Color.parseColor("#ff669900"));
            }
            else {
                holder.status.setTextColor(Color.parseColor("#ffcc0000"));
            }
            holder.amount.setTextColor(Color.GREEN);
        }


    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }


    class myviewholder extends RecyclerView.ViewHolder{
        TextView user1,user2, amount, status,date,cr_dr_status;
        ImageView iv_cr_dr;

        public myviewholder( View itemView, Context context) {
            super(itemView);
            user1 = itemView.findViewById(R.id.user1);
            user2 = itemView.findViewById(R.id.user2);
            amount = itemView.findViewById(R.id.amount);
            status = itemView.findViewById(R.id.status);
            date = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    model md = dataholder.get(position);
                    Intent intent = new Intent(context, com.yash.basicbankingapp.PaymentReceiptActivity.class);
                    intent.putExtra("TransactionID",md.getTrans_id());
                    intent.putExtra("PaymentStatus", md.getStatus());
                    context.startActivity(intent);

                }
            });
        }
    }


}
