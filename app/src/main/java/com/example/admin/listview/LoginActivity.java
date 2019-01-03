package com.example.admin.listview;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    Cursor cursor;
    Button _btnLogin;
    EditText _txtEmail, _txtPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
        _btnLogin = (Button)findViewById(R.id.btnLogin);
        _txtEmail = (EditText)findViewById(R.id.txtEmail);
        _txtPass = (EditText)findViewById(R.id.txtPass);
        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = _txtEmail.getText().toString();
                String pass = _txtPass.getText().toString();
                cursor = db.rawQuery("SELECT * FROM "+ DatabaseHelper.Table_Name +" WHERE " + DatabaseHelper.Colum5 + "=? AND " + DatabaseHelper.Colum4 + "=?", new String[]{email, pass});
                if (cursor != null){
                    if (cursor.getCount() > 0){
                        cursor.moveToNext();
                        Toast.makeText(getApplicationContext(), "login successful", Toast.LENGTH_LONG).show();
                        Intent sucessfullyLogin = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(sucessfullyLogin);

                    } else {
                        Toast.makeText(getApplicationContext(),"login fail",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
