package com.yash.basicbankingapp;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yash.basicbankingapp.DataBase.Database;
import com.yash.basicbankingapp.Models.model;

import java.util.ArrayList;
import java.util.List;

public class StatementActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<model> dataHolder = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    StatementAdapter adapter;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#0096FF"));

        recyclerView = findViewById(R.id.statement_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        id = getIntent().getExtras().getString("UserID");

        displayStatement(id);
        adapter.ID = id;


    }

    private void displayStatement(String id) {
        dataHolder.clear();
        Cursor cursor = new Database(this).readperticulartransferdata(id);

        while(cursor.moveToNext()){
            model md = new model(cursor.getString(1),cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
            this.dataHolder.add(md);
        }

        adapter = new StatementAdapter(StatementActivity.this, dataHolder);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}