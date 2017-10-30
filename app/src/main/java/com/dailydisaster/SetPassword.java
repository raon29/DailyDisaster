package com.dailydisaster;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class SetPassword extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase sql;
    EditText password1, password2, password3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
    }
    public void Add(View view){
        password1 = (EditText)findViewById(R.id.Password1);
        password2 = (EditText)findViewById(R.id.Password2);
        password3 = (EditText)findViewById(R.id.Password3);
        String strpassword1 = password1.getText().toString();
        String strpassword2 = password2.getText().toString();
        String strpassword3 = password3.getText().toString();
        if(strpassword1.length()==0||strpassword2.length()==2||strpassword3.length()==0){
            Toast.makeText(this, "빈칸채워", Toast.LENGTH_SHORT).show();
            return;
        }
        Integer savingpassword1 = Integer.parseInt(strpassword1);
        Integer savingpassword2 = Integer.parseInt(strpassword2);
        Integer savingpassword3 = Integer.parseInt(strpassword3);

        dbHelper = new DBHelper(getApplicationContext(), "Password");
        dbHelper.query("UPDATE Password SET password1="+savingpassword1+",password2="+savingpassword2+",password3="+savingpassword3+" WHERE _id=666");
        Toast.makeText(this, "비밀번호 변경이 완료되었습니다..", Toast.LENGTH_SHORT).show();
        //dbHelper.query("INSERT INTO Password VALUES(null, " +savingpassword1+","+savingpassword2+","+savingpassword3+")");
        Intent intent=new Intent(SetPassword.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void Cancel(View view){
        Intent intent=new Intent(SetPassword.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
