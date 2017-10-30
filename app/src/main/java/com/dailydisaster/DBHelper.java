package com.dailydisaster;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import static android.widget.Toast.*;

public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context, String DBName){
        super(context, DBName, null, 1);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE Password(_id integer, password1 integer, password2 integer, password3 integer)");
        db.execSQL("INSERT INTO Password VALUES(666, 0, 0, 0)");
        db.execSQL("CREATE TABLE Schedule(_id integer primary key autoincrement, datetime double, name text, content text, place text)");
        db.execSQL("CREATE TABLE Week(_id integer primary key autoincrement, position integer, name text)");
        for(int i=0; i<8*24; i++) {
            if (i % 8 != 0)
                db.execSQL("INSERT INTO Week VALUES(null, " + i + ", '-')");
            else
                db.execSQL("INSERT INTO Week VALUES(null, " + i + ", '" + i / 8 + "')");
        }
    }
    public void onUpgrade(SQLiteDatabase db, int oloVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Password");
        db.execSQL("DROP TABLE IF EXISTS Schedule");
        db.execSQL("DROP TABLE IF EXISTS Week");
        onCreate(db);
    }
    public void query(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
    }
}