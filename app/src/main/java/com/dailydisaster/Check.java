package com.dailydisaster;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Check extends FragmentActivity {

    ListView _list;
    DBHelper dbHelper;
    SQLiteDatabase sql;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        _list = (ListView)findViewById(R.id.dbList);
        dbHelper = new DBHelper(getApplicationContext(), "Schedule");
        sql = dbHelper.getWritableDatabase();
        cursor = sql.rawQuery("SELECT * FROM Schedule ORDER BY datetime ASC;", null);
        if(cursor.getCount() > 0){
            startManagingCursor(cursor);
            DBAdapter dbAdapter = new DBAdapter(this, cursor);
            _list.setAdapter(dbAdapter);
        }
        _list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                AlertDialog builder = new AlertDialog.Builder(Check.this)
                .setTitle(cursor.getString(cursor.getColumnIndex("name")))
                .setMessage(cursor.getString(cursor.getColumnIndex("content")))
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int _id = cursor.getInt(0);
                        dbHelper.query("DELETE FROM Schedule WHERE _id='" + _id + "';");
                        Intent intent=new Intent(Check.this, Check.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Check Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                        .show();
            }
        });
    }
    public void RemoveTable(View view){
        dbHelper.onUpgrade(sql,1,2);
        sql.close();
        Toast.makeText(this, "초기화 완료", Toast.LENGTH_SHORT).show();
    }
    public void Back(View view) {
        Intent intent=new Intent(Check.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
