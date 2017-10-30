package com.dailydisaster;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddWeek extends FragmentActivity {

    private TextView _checkWeek;
    private EditText _modify;
    DBHelper dbHelper;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_week);

        _checkWeek = (TextView)findViewById(R.id.WeekCheck);
        Intent recvIntent = getIntent();
        position = recvIntent.getIntExtra("position", 0);
        String name = recvIntent.getStringExtra("name");
        String week = null;
        switch(position%8){
            case 1:week = "월요일";break;
            case 2:week = "화요일";break;
            case 3:week = "수요일";break;
            case 4:week = "목요일";break;
            case 5:week = "금요일";break;
            case 6:week = "토요일";break;
            case 7:week = "일요일";break;
        }
        if(name.compareTo("-")==0)
            name = "등록된 일정이 없습니다.";
        _checkWeek.setText(week+"\n"+name);
    }
    public void Back(View view){
        Intent intent=new Intent(AddWeek.this, WeeklySchedule.class);
        startActivity(intent);
        finish();
    }
    public void WeekDelete(View view){
        dbHelper = new DBHelper(getApplicationContext(), "Week");
        dbHelper.query("UPDATE Week SET name='-' WHERE position="+position);
        Intent intent=new Intent(AddWeek.this, WeeklySchedule.class);
        startActivity(intent);
        finish();
    }
    public void InputWeekName(View view){
        _modify = (EditText)findViewById(R.id.Modyfie);
        String name = _modify.getText().toString();
        if(name.length()==0)
            name = "-";
        dbHelper = new DBHelper(getApplicationContext(), "Week");
        dbHelper.query("UPDATE Week SET name='"+name+"' WHERE position="+position);
        Intent intent=new Intent(AddWeek.this, WeeklySchedule.class);
        startActivity(intent);
        finish();
    }
}
