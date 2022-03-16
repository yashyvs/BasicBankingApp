package com.yash.basicbankingapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yash.basicbankingapp.DataBase.Database;

public class UserDetailsActivity extends AppCompatActivity {

    String id;
    TextView name, balance, email, phone, account_no, ifsc_code, user_image;
    Button statement_button, initiate_transfer_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#0096FF"));

        name = findViewById(R.id.user_name);
        balance = findViewById(R.id.user_balance);
        email = findViewById(R.id.user_email);
        phone = findViewById(R.id.user_phone);
        account_no = findViewById(R.id.user_account);
        ifsc_code = findViewById(R.id.user_ifsc);
        user_image = findViewById(R.id.user_image);
        statement_button = findViewById(R.id.statement_button);
        initiate_transfer_button = findViewById(R.id.initiate_transfer_button);

        id = getIntent().getExtras().getString("UserID");

        displayUserInfo(id);

        initiate_transfer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailsActivity.this, BeneficiaryActivity.class);
                intent.putExtra("UserID", id);
                startActivity(intent);
            }
        });

        statement_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailsActivity.this, StatementActivity.class);
                intent.putExtra("UserID", id);
                startActivity(intent);
            }
        });


    }

    private void displayUserInfo(String id) {
        Cursor cursor = new Database(this).readparticulardata(id);
        while (cursor.moveToNext()) {


            user_image.setText(String.valueOf(cursor.getString(1).charAt(0)));
            name.setText(cursor.getString(1));
            email.setText(cursor.getString(2));
            phone.setText(cursor.getString(3));
            account_no.setText(cursor.getString(4));
            ifsc_code.setText(cursor.getString(5));
            balance.setText(cursor.getString(6));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}