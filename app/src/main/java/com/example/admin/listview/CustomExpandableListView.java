package com.example.admin.listview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CustomExpandableListView extends BaseExpandableListAdapter {

    private  imageViewListener mListener;

    MainActivity.ReceiverClassName context2;
    Context context;
    private List<String> listHeader;
    private HashMap<String, List<Task>> listChild;
//    ImageView checkbox;

    // the fourth parameter is to activate the complete button listener.
    public CustomExpandableListView(Context context, List<String> listHeader, HashMap<String, List<Task>> listChild, imageViewListener mListener) {
        this.context = context;
        this.listHeader = listHeader;
        this.listChild = listChild;

        this.mListener = mListener;
    }

    public CustomExpandableListView(MainActivity.ReceiverClassName context, List<String> listHeader, HashMap<String, List<Task>> listChild, imageViewListener mListener){
        this.context2 = context;
        this.listHeader = listHeader;
        this.listChild = listChild;

        this.mListener = mListener;
    }



    @Override
    public int getGroupCount() {
        if (listHeader.size() >=6){
            return 6;
        }
        else{
            return listHeader.size();
        }
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return listChild.get(listHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listHeader.get(groupPosition);
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(listHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle  = (String) getGroup(groupPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.dategroup_view, null);
        TextView dateHeader = convertView.findViewById(R.id.dateHeader);
        dateHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Task taskObject  = (Task) getChild(groupPosition, childPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.taskchild_view, null);          // reference task objects
        TextView taskName_Child = convertView.findViewById(R.id.taskName_Child);
        TextView taskDescription_Child = convertView.findViewById(R.id.taskDecription);
        TextView taskTime_child = convertView.findViewById(R.id.taskTime);
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String taskTime = simpleDateFormat.format(taskObject.getTaskTime());
        taskTime_child.setText(taskTime);                               // to display task time
        taskName_Child.setText(taskObject.getTaskName());               // to display task name
        taskDescription_Child.setText(taskObject.getTaskDescription()); // to display task description

        RadioButton completeCB = convertView.findViewById(R.id.completeRB);
        completeCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    mListener.onCBClicked(taskObject);
                } catch (NullPointerException techmaster1){
                    System.out.println("Exception in CrunchifyNPE()" + techmaster1);
                }
            }
        });

        //??
//        checkbox = convertView.findViewById(R.id.imageView);            // to display checkbox
//        checkbox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    mListener.onCBClicked(taskObject);
//                } catch (NullPointerException techmaster1) {
//                    System.out.println("Exception in CrunchifyNPE1()" + techmaster1);
//                }
//
//            }
//        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public interface imageViewListener{
        void onCBClicked(Task taskObject);
    }
}

