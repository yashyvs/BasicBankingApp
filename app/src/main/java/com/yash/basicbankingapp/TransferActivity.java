package com.yash.basicbankingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.yash.basicbankingapp.DataBase.Database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class TransferActivity extends AppCompatActivity {
    
    TextView receiverImage, receiverName, receiverAccount, receiverIfsc;
    TextInputLayout amount_input;
    Button transferBtn;
    String senderId, receiverId;
    Double senderBalance, receiverBalance;
    String amount;
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#0096FF"));
        
        receiverImage = findViewById(R.id.receiver_image);
        receiverName = findViewById(R.id.receiver_name);
        receiverAccount = findViewById(R.id.receiver_account);
        receiverIfsc = findViewById(R.id.receiver_ifsc);
        amount_input = findViewById(R.id.amount_input);
        transferBtn = findViewById(R.id.transfer_button);
        
        senderId = getIntent().getExtras().getString("SenderId");
        receiverId = getIntent().getExtras().getString("ReceiverId");
        
        displayBeneficiaryInfo(receiverId);
        getSenderInfo(senderId);
        
        transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferMoney();
            }
        });
        
    }

    private void displayBeneficiaryInfo(String receiverId) {
        Cursor cursor = new Database(this).readparticulardata(receiverId);
        while (cursor.moveToNext()) {


            receiverImage.setText(String.valueOf(cursor.getString(1).charAt(0)));
            receiverName.setText(cursor.getString(1));
            receiverAccount.setText(cursor.getString(4));
            receiverIfsc.setText(cursor.getString(5));
            receiverBalance = Double.parseDouble(cursor.getString(6));
        }
    }

    private void getSenderInfo(String senderId) {
        Cursor cursor = new Database(this).readparticulardata(senderId);
        while (cursor.moveToNext()) {

            senderBalance = Double.parseDouble(cursor.getString(6));
        }

    }

    private void transferMoney() {
        amount = String.valueOf(amount_input.getEditText().getText());

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        formattedDate = df.format(c.getTime());

        String Transaction_id = String.valueOf(new Random().nextInt((9999999 - 1000000) + 1) + 1000000);


        if (amount.isEmpty()){
            AlertDialog alertDialog = new AlertDialog.Builder(TransferActivity.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setIcon(R.drawable.ic_baseline_error_24 );
            alertDialog.setMessage("Please Enter a Amount");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        else if ( Double.parseDouble(amount) > senderBalance){
            AlertDialog alertDialog = new AlertDialog.Builder(TransferActivity.this).create();
            alertDialog.setTitle("Insufficient Balance");
            alertDialog.setIcon(R.drawable.ic_baseline_error_24 );
            alertDialog.setMessage("You Dont Have Enough Money.");
            alertDialog.setCancelable(true);
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            Intent intent = new Intent(TransferActivity.this, com.yash.basicbankingapp.PaymentReceiptActivity.class);
                            intent.putExtra("PaymentStatus", "Failed");
                            intent.putExtra("TransactionID", Transaction_id);
                            startActivity(intent);
                            finish();
                        }
                    });
            alertDialog.show();

            new Database(this).insertTransferData(Transaction_id,formattedDate, senderId,receiverId,amount,"Failed");
        }
        else{
            senderBalance = senderBalance - Double.parseDouble(amount);
            new Database(this).updateAmount(senderId , senderBalance);
            receiverBalance = receiverBalance + Double.parseDouble(amount);
            new Database(this).updateAmount(receiverId , receiverBalance);

            boolean result = new Database(this).insertTransferData(Transaction_id,formattedDate, senderId,receiverId,amount,"Successful");
            if (result == true){
                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TransferActivity.this, com.yash.basicbankingapp.PaymentReceiptActivity.class);
                intent.putExtra("TransactionID", Transaction_id);
                intent.putExtra("PaymentStatus", "Successful");
                startActivity(intent);
                finish();
            }
        }
    }
}