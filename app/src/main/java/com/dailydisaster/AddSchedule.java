package com.dailydisaster;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddSchedule extends FragmentActivity implements AdapterView.OnItemSelectedListener{

    ArrayList<String> arraylist;
    private TextView _outputDate, _outputTime;
    Spinner planPlace;
    private int dpYear, dpMonth, dpDay;
    private int tpHour, tpMinute;
    static final int DATE_DIALOG_ID=0;
    static final int TIME_DIALOG_ID=1;
    Date date = new Date(System.currentTimeMillis());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
	getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        _outputDate = (TextView)findViewById(R.id.OutputDate);
        _outputTime = (TextView)findViewById(R.id.OutputTime);

        arraylist = new ArrayList<String>();
        arraylist.add("대전 광역시");
        arraylist.add("서울 특별시");
        arraylist.add("부산 광역시");
        arraylist.add("대구 광역시");
        arraylist.add("인천 광역시");
        arraylist.add("광주 광역시");
        arraylist.add("경기도");
        arraylist.add("강원도");
        arraylist.add("충청 북도");
        arraylist.add("충청 남도");
        arraylist.add("전라 북도");
        arraylist.add("전라 남도");
        arraylist.add("경상 북도");
        arraylist.add("경상 남도");
        arraylist.add("제주 특별 자치도");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraylist);
        planPlace = (Spinner)findViewById(R.id.PlanPlace);
        planPlace.setPrompt("지역 선택");
        planPlace.setAdapter(adapter);
        planPlace.setOnItemSelectedListener(this);
    }

    public void InputDate(View view){
        showDialog(DATE_DIALOG_ID);
    }
    public void InputTime(View view){
        showDialog(TIME_DIALOG_ID);
    }
    private DatePickerDialog.OnDateSetListener dpDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    dpYear = year%100;
                    dpMonth = monthOfYear+1;
                    dpDay =  dayOfMonth;
                    _outputDate.setText(year + "/" + dpMonth + "/" + dpDay);
                }
            };
    private TimePickerDialog.OnTimeSetListener tpTimeSetListenet =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    tpHour = hourOfDay;
                    tpMinute = minute;
                    _outputTime.setText(tpHour+" : "+tpMinute);
                }
            };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case DATE_DIALOG_ID:
                //date 초기값
                return new DatePickerDialog(this, dpDateSetListener, date.getYear()+1900, date.getMonth(), date.getDay());
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, tpTimeSetListenet, tpHour, tpMinute, true);
            default:
                return null;
        }
    }
    public void Add(View view){
        EditText planName = (EditText)findViewById(R.id.PlanName);
        EditText planContent = (EditText)findViewById(R.id.PlanContent);

        double datetime = dpYear*100000000+dpMonth*1000000+dpDay*10000+tpHour*100+tpMinute;
        String name = planName.getText().toString();
        String content = planContent.getText().toString();
        String place = (String)planPlace.getSelectedItem();

        Calendar cal = Calendar.getInstance(Locale.KOREA);
        Calendar alarmCal = Calendar.getInstance(Locale.KOREA);
        alarmCal.set(dpYear+2000, dpMonth-1, dpDay, tpHour, tpMinute, 0);

        long min30 = 1800000;
        long curTime = cal.getTimeInMillis();
        long notificationTime = alarmCal.getTimeInMillis();
        long alarmTime = notificationTime - min30 - curTime;

        Log.d("curTime", "" + curTime);
        Log.d("notiTime", "" + notificationTime);
        Log.d("alarmTime", "" + alarmTime);
        setAlarm(this, alarmTime);
        Log.d("alarmTime", "" + alarmTime);

        DBHelper dbHelper = new DBHelper(getApplicationContext(), "Schedule");
        dbHelper.query("INSERT INTO Schedule VALUES(null, " + datetime + ",'" + name + "','" + content + "', '" + place + "')");
        Intent intent=new Intent(AddSchedule.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void Cancel(View view) {
        Intent intent=new Intent(AddSchedule.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    private void setAlarm(Context context, long second) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, PushService.class);
        PendingIntent pIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + second, pIntent);
    }
}