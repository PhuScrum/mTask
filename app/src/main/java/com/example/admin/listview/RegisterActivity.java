package com.example.admin.listview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Button _btnreg; // Register button
    Button _btnlogin; // Login button
    EditText _txtfname, _txtlname, _txtpass, _txtemail, _txtphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        openHelper = new DatabaseHelper(this);
        _btnreg =(Button)findViewById(R.id.btnreg);
        _txtfname =(EditText)findViewById(R.id.txtfname);
        _txtlname =(EditText)findViewById(R.id.txtlname);
        _txtpass =(EditText)findViewById(R.id.txtpass);
        _txtemail =(EditText)findViewById(R.id.txtemail);
        _txtphone =(EditText)findViewById(R.id.txtphone);
        _btnlogin = (Button)findViewById(R.id.btnLogin);
        _btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openHelper.getWritableDatabase();
                String fname = _txtfname.getText().toString();
                String lname = _txtlname.getText().toString();
                String pass = _txtpass.getText().toString();
                String email = _txtemail.getText().toString();
                String phone = _txtphone.getText().toString();
                insertdata(fname,lname,pass,email,phone);
                //!?
                Toast.makeText(getApplicationContext(),"registered",Toast.LENGTH_LONG).show();
            }
        });
        _btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //INSERT DATA
    public void insertdata(String fname, String lname, String pass, String email, String phone){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.Colum2, fname);
        contentValues.put(DatabaseHelper.Colum3, lname);
        contentValues.put(DatabaseHelper.Colum4, pass);
        contentValues.put(DatabaseHelper.Colum5, email);
        contentValues.put(DatabaseHelper.Colum6, phone);
        long id = db.insert(DatabaseHelper.Table_Name, null, contentValues);
    }
}
