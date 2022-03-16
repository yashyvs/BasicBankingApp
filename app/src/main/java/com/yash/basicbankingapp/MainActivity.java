package com.yash.basicbankingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.yash.basicbankingapp.DataBase.Database;

public class MainActivity extends AppCompatActivity {

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        db = new Database(this);
        getWindow().setStatusBarColor(Color.parseColor("#0096FF"));

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent intent = new Intent(MainActivity.this,AllUsersActivity.class);
               startActivity(intent);
               finish();
           }
       },1000);

    }
}