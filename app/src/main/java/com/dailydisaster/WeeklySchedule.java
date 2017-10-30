package com.dailydisaster;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class WeeklySchedule extends FragmentActivity{

    GridView _grid;
    DBHelper dbHelper;
    SQLiteDatabase sql;
    Cursor cursor;

    protected void onCreate(Bundle savedInstanceState) {
	getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_schedule);

        _grid = (GridView) findViewById(R.id.gridView);

        dbHelper = new DBHelper(getApplicationContext(), "Week");
        sql = dbHelper.getWritableDatabase();
        cursor = sql.rawQuery("SELECT * FROM Week ORDER BY position ASC;", null);
        if(cursor.getCount() > 0) {
            startManagingCursor(cursor);
            GridAdapter gridAdapter = new GridAdapter(this, cursor);
            _grid.setAdapter(gridAdapter);
        }
        _grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position%8!=0) {
                    cursor.moveToPosition(position);
                    int nowPosition = cursor.getInt(cursor.getColumnIndex("position"));
                    String nowName = cursor.getString(cursor.getColumnIndex("name"));
                    Intent intent = new Intent(WeeklySchedule.this, AddWeek.class);
                    intent.putExtra("position", nowPosition);
                    intent.putExtra("name", nowName);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    public void Back(View view) {
        Intent intent=new Intent(WeeklySchedule.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
