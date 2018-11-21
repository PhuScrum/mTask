package com.example.admin.listview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private  BottomSheetListener mListener;

    Button btnCreateTask_MDS;
    Button btnCancel_MDS;
    EditText edtTaskName_Input;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_bottom_sheet, container, false);

        edtTaskName_Input =  v.findViewById(R.id.edtTaskName_Input);
        btnCreateTask_MDS = v.findViewById(R.id.btnCreateTask_MDS);
        btnCancel_MDS = v.findViewById(R.id.btnCancel_MDS);


        btnCancel_MDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btnCreateTask_MDS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked(edtTaskName_Input.getText().toString());
                dismiss();
            }
        });


        return v;
    }

    // what happens when the button on the fragment clicked, affect the Main Activity
    public interface BottomSheetListener{
        void onButtonClicked(String text);
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
