package com.yash.basicbankingapp;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yash.basicbankingapp.DataBase.Database;
import com.yash.basicbankingapp.Models.AllUserAdapter;
import com.yash.basicbankingapp.Models.model;

import java.util.ArrayList;
import java.util.List;

public class AllUsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<model> dataHolder = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    AllUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        getSupportActionBar().setTitle("Users");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0096FF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(Color.parseColor("#0096FF"));

        recyclerView = findViewById(R.id.users_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        displayUsers();


    }

    private void displayUsers() {
        dataHolder.clear();
        Cursor cursor = new Database(this).readalldata();

        while (cursor.moveToNext()) {
            model md = new model(cursor.getString(0), cursor.getString(1), cursor.getString(3), cursor.getString(6));
            this.dataHolder.add(md);
        }

        adapter = new AllUserAdapter(AllUsersActivity.this, dataHolder);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}