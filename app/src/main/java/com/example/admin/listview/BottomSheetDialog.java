package com.example.admin.listview;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class BottomSheetDialog extends BottomSheetDialogFragment  {
    private  BottomSheetListener mListener;

    Button btnCreateTask_MDS;
    Button btnCancel_MDS;
    EditText edtTaskName_Input;

    EditText edtTaskDescription_Input;
    Button btnSetDate;
    Button btnSetTime;
    View v;

    String dateSelection_Input;
    String timeSelection_Input;

    public void BSMReference(){
        edtTaskName_Input =  v.findViewById(R.id.edtTaskName_Input);
        btnCreateTask_MDS = v.findViewById(R.id.btnCreateTask_MDS);
        btnCancel_MDS = v.findViewById(R.id.btnCancel_MDS);

        edtTaskDescription_Input = v.findViewById(R.id.edtTaskDescription_Input);
        btnSetDate = v.findViewById(R.id.btnSetDate_Input);
        btnSetTime = v.findViewById(R.id.btnSetTime_Input);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.modal_bottom_sheet, container, false);
        BSMReference();

        // set default date and time selection on the modal bottom sheet
        final Calendar calendar = Calendar.getInstance();
        btnSetDate.setText("Today");
        SimpleDateFormat defaultDate = new SimpleDateFormat("dd/MM/yyyy");
        dateSelection_Input = defaultDate.format(calendar.getTime());

        SimpleDateFormat defaultTime = new SimpleDateFormat("HH:mm");
        timeSelection_Input = defaultTime.format(calendar.getTime());
        btnSetTime.setText(timeSelection_Input);

        // make top header Today
        String dateToday = defaultDate.format(calendar.getTime());
//        if(dateSelection_Input.equals(dateToday)){
//        dateSelection_Input = "Today";}

        // cancel button
        btnCancel_MDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // create task button
        btnCreateTask_MDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(edtTaskName_Input.getText().toString(), edtTaskDescription_Input.getText().toString(), dateSelection_Input, timeSelection_Input);
                dismiss();
            }
        });

        // date selection button
        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonNgay();
            }
        });

        // time selection button
        btnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonGio();
            }
        });

        return v;
    }

    private void chonNgay(){
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(calendar.DATE);
        int month = calendar.get(calendar.MONTH);
        int year = calendar.get(calendar.YEAR);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateToday = simpleDateFormat.format(calendar.getTime());

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                dateSelection_Input = simpleDateFormat.format(calendar.getTime());
//                if(dateSelection_Input.equals(dateToday)){
//                dateSelection_Input = "Today";
//                }
                btnSetDate.setText(dateSelection_Input);
            }
        }, year,  month, date);
        datePickerDialog.show();
    }

    private void chonGio(){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0, 0, 0, hourOfDay, minute);
                timeSelection_Input = simpleDateFormat.format(calendar.getTime());
                btnSetTime.setText(timeSelection_Input);
            }
        },hour, minutes, true);
        timePickerDialog.show();
    }




    // what happens when the button on the fragment clicked, affect the Main Activity
    public interface BottomSheetListener{
        void onButtonClicked(String taskName, String taskDescription, String dateSelection_input, String timeSelection_input);
    }


    // To send data from the Modal Bottom Sheet to the Main Activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
        mListener = (BottomSheetListener) context;}
        catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

}


