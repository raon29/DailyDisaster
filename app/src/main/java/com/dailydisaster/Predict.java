package com.dailydisaster;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Predict extends FragmentActivity {
    Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);
        result = new Result(Predict.this);
        result.setTitle("오늘의 운세");
    }
    public void Select(View view){
        result.show();
    }
    public void Back(View view) {
        Intent intent=new Intent(Predict.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
