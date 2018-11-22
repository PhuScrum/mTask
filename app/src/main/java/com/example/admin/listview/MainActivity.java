package com.example.admin.listview;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener {

    FloatingActionButton btnCreateTask;

    ListView lvTaskList;
    ArrayList<String> taskList;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ListView");
        btnCreateTask = findViewById(R.id.fabCreateTask);

        lvTaskList = (ListView) findViewById(R.id.lvTaskList);
        taskList = new ArrayList<>();

        btnCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // khai báo một bottom sheet
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                // hiển thị Bottom Sheet
                bottomSheet.show(getSupportFragmentManager(), "bottomSheet");
            }
        });
    }

    // Effects of button clicked on the Bottom Sheet, that affects the Main Activity
    @Override
    public void onButtonClicked(String text) {
        taskList.add(text);
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_single_choice, taskList);
        lvTaskList.setAdapter(adapter);
    }
}

