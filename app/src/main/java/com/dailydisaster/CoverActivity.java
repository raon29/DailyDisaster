package com.dailydisaster;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CoverActivity extends Activity {

    private DBHelper WdbHelper;
    SQLiteDatabase Wsql;
    Cursor Wcursor;
    DBHelper dbHelper;
    SQLiteDatabase sql;
    Cursor cursor;
    int c_time;
    double DateTime;
    long longTime = System.currentTimeMillis();
    Date curdate = new Date(longTime);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        TextView showWhen;
        showWhen = (TextView) findViewById(R.id.coverlist);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }

    public void deadClick(View view) {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
