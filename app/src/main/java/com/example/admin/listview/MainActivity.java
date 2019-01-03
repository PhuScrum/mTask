package com.example.admin.listview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Timer;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import static com.example.admin.listview.SortTime.TIME_ORDER;

class SortTime {
    static final Comparator<Task> TIME_ORDER =
            new Comparator<Task>() {
                public int compare(Task o1, Task o2) {
                    return o1.getTaskTime().compareTo(o2.getTaskTime());
                }
            };
}

public class MainActivity extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener, CustomExpandableListView.imageViewListener {

    FloatingActionButton btnCreateTask;

    static ExpandableListView expandableTaskView;
    static CustomExpandableListView customExpandableListView;

    static List<String> dateHeader;
    static HashMap<String, List<Task>>taskGroup;
    List<Task> taskGroupChild;
    Task taskObject;


    Date taskDate;
    Date taskTime;

    static String dateToday;
    static String dateTomorrow;


    static int numberOfCompletedTasks =0;

    static String ReceiverClassName1 = "ReceiverClassName Here";

    private static MainActivity application;




    public static class ReceiverClassName extends BroadcastReceiver implements  CustomExpandableListView.imageViewListener {
        @Override
        public void onReceive(Context context, Intent intent) {

            // set default Today to adjust the date header
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpledateformat = new SimpleDateFormat("dd/MM/YYYY");
            dateToday = simpledateformat.format(calendar.getTime());

            // set default Tomorrow to adjust the date header
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.DATE, 1); // Adding 1 days
            dateTomorrow = sdf.format(c.getTime());

            // set the date after tomorrow to update the timeline.
            SimpleDateFormat sdfDateAfterTMR = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date()); // Now use today date.
            cal.add(Calendar.DATE, 1); // Adding 1 days
            String dateAfterTomorrow = sdfDateAfterTMR.format(cal.getTime());


            Toast.makeText(context, ReceiverClassName1, Toast.LENGTH_SHORT).show();
//          // to be continue later after fixing disappearing imageview at list view
//            if(taskGroup.containsKey("Today") && taskGroup.containsKey("Tomorrow") && taskGroup.containsKey(dateAfterTomorrow)){
//                if(taskGroup.get("Today").get(0).getTaskDate().equals(dateToday)){
//                    dateHeader.add("Need action!");
//                    List<Task> needAction = new ArrayList<>();
//                    taskGroup.put("Need action!", needAction);
//                    taskGroup.get("Need action!").addAll(taskGroup.get("Today"));
//                    if(taskGroup.containsKey("Tomorrow")){
//                    taskGroup.put("Today", taskGroup.get("Tomorrow"));}
//                    else{taskGroup.get("Today").clear(); dateHeader.remove("Today");}
//            }}else if(taskGroup.containsKey("Today") && taskGroup.containsKey("Tomorrow")){
//
//            }else
//
            customExpandableListView = new CustomExpandableListView(context, dateHeader, taskGroup, this);
            expandableTaskView.setAdapter(customExpandableListView);
        }

        Context test;
        @Override
        public void onCBClicked(Task taskObject) {

            numberOfCompletedTasks +=1;
            Toast.makeText(MainActivity.getContext(), "Congratulation! you have completed " + numberOfCompletedTasks + " tasks.", Toast.LENGTH_SHORT).show();
            if (taskObject.getTaskDate().equals(dateToday)){
                // remove task from task group child Today
                taskGroup.get("Today").remove(taskObject);
                //clear dates when there's no task in Today
                if (taskGroup.get("Today").isEmpty()){
                    dateHeader.remove("Today");
                    taskGroup.remove("Today");
                }

            } else if (taskObject.getTaskDate().equals(dateTomorrow)){
                // remove task from task group child Tomorrow
                taskGroup.get("Tomorrow").remove(taskObject);
                //clear dates when there's no items in Tomorrow
                if(taskGroup.get("Tomorrow").isEmpty()){
                    dateHeader.remove("Tomorrow");
                    taskGroup.remove("Tomorrow");
                }
            } else{
                // remove task from task group child other dates
                taskGroup.get(taskObject.getTaskDate()).remove(taskObject);
                //clear dates when there's no items in other dates
                if(taskGroup.get(taskObject.getTaskDate()).isEmpty()){
                    dateHeader.remove(taskObject.getTaskDate());
                    taskGroup.remove(taskObject.getTaskDate());
                }
            }

//            //taskGroupChild.remove(taskObject);
            customExpandableListView = new CustomExpandableListView(MainActivity.getContext(), dateHeader, taskGroup, this);
            expandableTaskView.setAdapter(customExpandableListView);


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = this;
        setContentView(R.layout.activity_main);
        setTitle("ListView");
        btnCreateTask = findViewById(R.id.fabCreateTask);

        expandableTaskView = (ExpandableListView) findViewById(R.id.expandableTaskView);
        dateHeader = new ArrayList<String>();
//        dateHeader.add("Need Action!");

        taskGroup = new HashMap<String, List<Task>>();


        btnCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // khai báo một bottom sheet
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                // hiển thị Bottom Sheet
                bottomSheet.show(getSupportFragmentManager(), "bottomSheet");
            }
        });


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, 1);
        setAlarm(calendar.getTimeInMillis());


    }


    private void setAlarm(long timeInMillis) {
        /*
            this function set alarm to schedule a task to add date header
            ""
         */

        IntentFilter filter = new IntentFilter();
        this.registerReceiver(new ReceiverClassName(), filter);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReceiverClassName.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC, timeInMillis, 1000*10, pendingIntent);


    }

    public static Context getContext(){
        return application.getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.registerReceiver(new ReceiverClassName(), new IntentFilter("some_unique_name"));

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(new ReceiverClassName());

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(MainActivity.this, "Inner Alarm", Toast.LENGTH_SHORT).show();
            // Extract data included in the Intent
            // String message = intent.getStringExtra("message");
            //update the TextView
        }
    };







    // Effects of button clicked on the Bottom Sheet, that affects the Main Activity
    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onButtonClicked(String taskName_input, String taskDescription_input, String dateSelection_input, String timeSelection_input) {

        // Time and date conversion to correct data type in the object task modeling.
        try{
            taskDate=new SimpleDateFormat("dd/MM/yyyy").parse(dateSelection_input);
        }catch (ParseException e){
            e.printStackTrace();
        }

        try{
            taskTime = new SimpleDateFormat("HH:mm").parse(timeSelection_input);
        } catch (ParseException e){
            e.printStackTrace();
        }

        // pass parameters from user input in modal bottom sheet to main activity, to create new task object.
        taskObject = new Task(taskName_input, taskDescription_input, dateSelection_input, taskTime);

        // set default Today to adjust the date header
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd/MM/YYYY");
        dateToday = simpledateformat.format(calendar.getTime());

        // set default Tomorrow to adjust the date header
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, 1); // Adding 1 days
        dateTomorrow = sdf.format(c.getTime());


        /* Depends on the date selection of task from users, this part will decides which task are allocated accordingly to date selection.
            -- Today
            -- Tomorrow
            -- *Other dates*
           and sort tasks in each dates accordingly to the time selection created by users.

         */
        if(dateSelection_input.equals(dateToday)){      // how tasks on Today are allocated
            if(taskGroup.containsKey("Today")){
                taskGroup.get("Today").add(taskObject);
                taskGroup.get("Today").sort((o1, o2) -> o1.getTaskTime().compareTo(o2.getTaskTime()));
            }
            else{
                dateHeader.add("Today");
                taskGroupChild = new ArrayList<Task>();
                taskGroupChild.add(taskObject);
                taskGroup.put("Today", taskGroupChild);
            }
        } else if (dateSelection_input.equals(dateTomorrow)){       // how tasks on Tomorrow are allocated
            if(taskGroup.containsKey("Tomorrow")){
                taskGroup.get("Tomorrow").add(taskObject);
                taskGroup.get("Tomorrow").sort((o1, o2) -> o1.getTaskTime().compareTo(o2.getTaskTime()));

            }
            else{
                dateHeader.add("Tomorrow");
                taskGroupChild = new ArrayList<Task>();
                taskGroupChild.add(taskObject);
                taskGroup.put("Tomorrow", taskGroupChild);
            }
        }
        else {

            if(taskGroup.containsKey(dateSelection_input)){         // how tasks  at Other dates are allocated
                taskGroup.get(dateSelection_input).add(taskObject);
                taskGroup.get(dateSelection_input).sort((o1, o2) -> o1.getTaskTime().compareTo(o2.getTaskTime()));
            }
            else{
                dateHeader.add(dateSelection_input);                // add new dateSelection into dateHeader                                 them dateSelection vao` dateHeader
                taskGroupChild = new ArrayList<Task>();             // create new taskGroupChild                                             tao. them taskGroupChild moi'
                taskGroupChild.add(taskObject);                     // add new tasks into the taskGroupChild accordingly                     them task_name vao`  taskGroupChild cua ngay day
                taskGroup.put(dateSelection_input, taskGroupChild); // add new dateSelection and taskGroupChild into new HashMap,            them dateSelection, taskGroupChild vao taskGroup
                                                                    // ready to be displayed as items in the ExpandableListView
                /*
                    main focus problem.
                    tao. new TaskGroup tuong u'ng voi moi~ ngay`
                    them task vao` taskGroup day'
                    them taskGroup moi~ ngay` vao taskGroupParent */


                Collections.sort(taskGroupChild, new Comparator<Task>() {   // to sort
                    @Override
                    public int compare(Task o1, Task o2) {
                        int result = o2.getTaskTime().compareTo(o1.getTaskTime());
                        return result;
                    }
                });

                // to sort the date headers
                List<String>  result = new ArrayList<>();
                result = SortDateHeader(dateHeader);
                dateHeader = result;



        }
        }


        //  Use adapter to display Expandable list view
        customExpandableListView = new CustomExpandableListView(MainActivity.this, dateHeader, taskGroup, this);
        expandableTaskView.setAdapter(customExpandableListView);
    }


    private static void order(List<Task> list) {

        Collections.sort(list, new Comparator<Task>() {

            public int compare(Task o2, Task o1) {
                return o1.getTaskTime().compareTo(o2.getTaskTime());

            }
        });
    }

    private static List<String> SortDateHeader(List<String> myDateArray){
        /* a function to sort the date header timeline
           where Today, Tomorrow and other dates are aligned, in order
         */
        List<String> literalPart = new ArrayList<>();       // Initializing two parts to contains Today + Tomorrow (1) and other dates (2)
        List<String> non_literalPart = new ArrayList<>();
        // result array when combined the two above.
        List<String> result = new ArrayList<>();

        // to sort the literal-part first
        if (myDateArray.contains("Today") && myDateArray.contains("Tomorrow")) {
            literalPart.add("Today");
            literalPart.add("Tomorrow");
        } else if(myDateArray.contains("Today")){
            literalPart.add("Today");
        } else if(myDateArray.contains("Tomorrow")){
            literalPart.add("Tomorrow");
        } else{
            Collections.sort(myDateArray);
            result.addAll(myDateArray);
        }

        // to sort the non-literal part second.
        for(String date: myDateArray){
            if (date.equals("Today") || date.equals("Tomorrow")){
                assert true;
            } else{
                non_literalPart.add(date);
            }
        }
        Collections.sort(non_literalPart);

        // combine two parts into one as a result of final date header
        result.addAll(literalPart);
        result.addAll(non_literalPart);
        return result;
    }

    @Override
    public void onCBClicked(Task taskObject) {
        /* a function to activate events when the completed buttons are tapped, to complete a task.

         displaying congratulation message toast whenever users complete a task by directly tapping on the circle button.*/
        numberOfCompletedTasks +=1;
        Toast congratulationToast = Toast.makeText(this, "Congratulation! You have completed " + numberOfCompletedTasks + " tasks.", Toast.LENGTH_SHORT);
        congratulationToast.show();

        if (taskObject.getTaskDate().equals(dateToday)){
            // remove task from task group child Today
            taskGroup.get("Today").remove(taskObject);
            //clear dates when there's no task in Today
            if (taskGroup.get("Today").isEmpty()){
                dateHeader.remove("Today");
                taskGroup.remove("Today");
            }

        } else if (taskObject.getTaskDate().equals(dateTomorrow)){
            // remove task from task group child Tomorrow
            taskGroup.get("Tomorrow").remove(taskObject);
            //clear dates when there's no items in Tomorrow
            if(taskGroup.get("Tomorrow").isEmpty()){
                dateHeader.remove("Tomorrow");
                taskGroup.remove("Tomorrow");
            }
        } else{
            // remove task from task group child other dates
            taskGroup.get(taskObject.getTaskDate()).remove(taskObject);
            //clear dates when there's no items in other dates
            if(taskGroup.get(taskObject.getTaskDate()).isEmpty()){
                dateHeader.remove(taskObject.getTaskDate());
                taskGroup.remove(taskObject.getTaskDate());
            }
        }

        //taskGroupChild.remove(taskObject);
        customExpandableListView = new CustomExpandableListView(MainActivity.this, dateHeader, taskGroup, this);
        expandableTaskView.setAdapter(customExpandableListView);
    }
}

