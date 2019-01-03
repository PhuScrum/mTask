package com.example.admin.listview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DataBase_Name = "register.db";
    public static final String Table_Name = "register";
    public static final String Colum1 = "_id";
    public static final String Colum2 = "FirstName";
    public static final String Colum3 = "LastName";
    public static final String Colum4 = "Password";
    public static final String Colum5 = "Email";
    public static final String Colum6 = "Phone";

    public DatabaseHelper(Context context) {
        super(context, DataBase_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATING A TABLE
        db.execSQL("CREATE TABLE " + Table_Name+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT,FirstName TEXT, LastName TEXT, Password TEXT, Email TEXT, Phone TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DROP A TABLE
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }
}
