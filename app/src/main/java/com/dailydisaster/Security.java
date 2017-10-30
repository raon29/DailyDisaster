package com.dailydisaster;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Security extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase sql;
    Cursor cursor;

    TextView equation;
    EditText result;

    int solve=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        equation = (TextView)findViewById(R.id.Equation);
        String _equation = "";
        dbHelper = new DBHelper(getApplicationContext(), "Password");
        sql = dbHelper.getWritableDatabase();
        cursor = sql.rawQuery("SELECT * FROM Password;", null);
        cursor.moveToFirst();
        int[] pwd = new int[3];
        pwd[0]=cursor.getInt(cursor.getColumnIndex("password1"));
        pwd[1]=cursor.getInt(cursor.getColumnIndex("password2"));
        pwd[2]=cursor.getInt(cursor.getColumnIndex("password3"));
        Toast.makeText(this, pwd[0]+", "+pwd[1]+", "+pwd[2], Toast.LENGTH_SHORT).show();
        char[] calc = {'+', '-', '*'};
        char[] randCalc = new char[2];
        int i;
        for(i=0; i<2; i++)
            randCalc[i] = calc[((int)(Math.random() * 10))%3];
        _equation = "Pass1"+randCalc[0]+"Pass2"+randCalc[1]+"Pass3";
        equation.setText(_equation);
        if(randCalc[1]=='*'){
            solve = pwd[1]*pwd[2];
            if(randCalc[0] == '+')
                solve+=pwd[0];
            else if(randCalc[0] == '-')
                solve=pwd[0]-solve;
            else
                solve*=pwd[0];
        }
        else{
            solve = pwd[0];
            for(i=1; i<3; i++){
                if(randCalc[i-1] == '+')
                    solve+=pwd[i];
                else if(randCalc[i-1] == '-')
                    solve-=pwd[i];
                else
                    solve*=pwd[i];
            }
        }
    }
    public void Submit(View view){
        result = (EditText) findViewById(R.id.Result);
        String strresult = result.getText().toString();
        if(strresult.length()==0){
            Toast.makeText(this, "빈칸채워", Toast.LENGTH_SHORT).show();
            return;
        }
        int numresult = Integer.parseInt(strresult);
        if(numresult==solve){
            Intent intent=new Intent(Security.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, "정답이 아닙니다.", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
