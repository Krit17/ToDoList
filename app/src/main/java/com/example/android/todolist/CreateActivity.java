package com.example.android.todolist;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.android.todolist.Constant.KEY_TODO;

public class CreateActivity extends AppCompatActivity {
    EditText et1, et2, et3, et4, et5;
    Calendar cal;
    public static final String MY_ACTION = "my_action";
    public static final int REQ_SEND = 20;
    long millis;
    BroadcastReceiver broadcastReceiver;
    LocalBroadcastManager broadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        et1 = (EditText) findViewById(R.id.title);
        et2 = (EditText) findViewById(R.id.activity);
        et3 = (EditText) findViewById(R.id.category);
        et4 = (EditText) findViewById(R.id.date);
        et5 = (EditText) findViewById(R.id.time);
        broadcastManager = LocalBroadcastManager.getInstance(this);


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(CreateActivity.this,"Custom Broadcast",Toast.LENGTH_SHORT).show();
            }
        };
        cal = Calendar.getInstance();
        et4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateActivity.this, date, cal
                        .get(Calendar.YEAR), cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        et5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(CreateActivity.this, time, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show();
            }
        });

    }

    public void AddTodo(View view) throws ParseException {
        String title = et1.getEditableText().toString();
        String activity = et2.getEditableText().toString();
        String category = et3.getEditableText().toString();
        String mydate = et4.getEditableText().toString();
        String mytime = et5.getEditableText().toString();

        millis = cal.getTimeInMillis();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
         Bundle b=new Bundle();
        b.putString("title",title);
        b.putString("activity",activity);
        alarmIntent.putExtras(b);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, alarmIntent, 0);

        alarmManager.set(AlarmManager.RTC, millis, pendingIntent);

        ToDoOpenHelper openHelper = ToDoOpenHelper.getInstance(getApplicationContext());
        SQLiteDatabase db = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.TODO_TITLE, title);
        contentValues.put(Contract.TODO_ACTIVITY, activity);
        contentValues.put(Contract.TODO_CAT, category);
        contentValues.put(Contract.TODO_DATEANDTIME, millis);
        long id = db.insert(Contract.TODO_TABLE, null, contentValues);
        ToDo toDo=new ToDo(title,activity,id,category,millis);
        Intent intent = new Intent();
        intent.putExtra(Constant.KEY_ID, id);
        // intent.putExtra(KEY_TODO,todo);
        setResult(REQ_SEND, intent);
        finish();

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et4.setText(sdf.format(cal.getTime()));
    }

    TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int min) {
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, min);
            et5.setText(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
        }
    };


    //    public static String getDate(long milliSeconds, String dateFormat)
//    {
//        // Create a DateFormatter object for displaying date in specified format.
//        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
//
//        // Create a calendar object that will convert the date and time value in milliseconds to date.
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(milliSeconds);
//        return formatter.format(calendar.getTime());
//    }

}
