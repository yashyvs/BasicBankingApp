package com.yash.basicbankingapp.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private String TABLE_NAME = "user_info";
    private String TABLE_NAME1 = "transfers_table";

    public Database(@Nullable Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY ,NAME TEXT,PHONE_NO VARCHAR, EMAIL VARCHAR,ACCOUNT_NO VARCHAR,IFSC_CODE VARCHAR,BALANCE VARCHAR)");
        db.execSQL("create table " + TABLE_NAME1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TRANSACTION_ID VARCHAR, DATE VARCHAR,SENDER_ID VARCHAR,RECEIVER_ID VARCHAR,AMOUNT VARCHAR,STATUS TEXT)");

        db.execSQL("insert into user_info values(1, 'Abhishek', 'abhishek001@gmail.com','9865748569', '456985647896', 'PUNB0123','4500.00')");
        db.execSQL("insert into user_info values(2, 'Aryan', 'aryan755@hotmail.com', '8745865895','458785697458', 'HDFC01234','23000.40')");
        db.execSQL("insert into user_info values(3, 'Praful', 'spraful123@yahoo.com', '6587985475','574898569745', 'IDFC0125','4200.00')");
        db.execSQL("insert into user_info values(4, 'Shiva','shiva6@gmai.com', '7458985645','791562541365', 'ICICI0256','6120.75')");
        db.execSQL("insert into user_info values(5, 'Brahm', 'brahm89@gmail.com', '7458965235','745896321589', 'BARB0004673','10000.00')");
        db.execSQL("insert into user_info values(6, 'Piyush', 'piyush@hotmail.com', '9775694521','654125893651', 'PUNB0653','54000.00')");
        db.execSQL("insert into user_info values(7, 'Yash', 'yash008@gmail.com', '9876543210','854965785469', 'SBIN02564','150000.00')");
        db.execSQL("insert into user_info values(8, 'Dhruv', 'dhruv666@rediffmail.com','6398567854', '658945875698', 'BARB0615','46950.85')");
        db.execSQL("insert into user_info values(9, 'Kartikey', 'kartikey255@gmail.com', '8569745896','985678549658', 'HDFC0256','9012.36')");
        db.execSQL("insert into user_info values(10, 'Priyesh', 'priyesh@hotmail.com', '9458658978','546458742563', 'SBIN0365','1200.00')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }

    public Cursor readalldata() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_info", null);
        return cursor;
    }

    public Cursor readparticulardata(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_info where id = " + id, null);
        return cursor;
    }

    public Cursor readselectuserdata(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_info except select * from user_info where id = " + id, null);
        return cursor;
    }

    public void updateAmount(String id, Double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update user_info set balance = " + amount + " where id = " + id);
    }

    public Cursor readtransferdata(String transactionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from transfers_table where TRANSACTION_ID = " + transactionId, null);
        return cursor;
    }


    public Cursor readperticulartransferdata(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from transfers_table where SENDER_ID = " + id + " OR RECEIVER_ID = " + id + " ORDER BY id DESC ", null);
        return cursor;
    }


    public boolean insertTransferData(String Transaction_id, String date, String SenderID, String ReceiverID, String amount, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TRANSACTION_ID", Transaction_id);
        contentValues.put("DATE", date);
        contentValues.put("SENDER_ID", SenderID);
        contentValues.put("RECEIVER_ID", ReceiverID);
        contentValues.put("AMOUNT", amount);
        contentValues.put("STATUS", status);
        Long result = db.insert(TABLE_NAME1, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
