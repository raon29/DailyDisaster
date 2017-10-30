package com.dailydisaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by song on 2016-01-12.
 */

public class ConfigActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setcover);
}

    public void cover_click(View view) {
        Intent intent = new Intent(this, ScreenService.class);
        switch (view.getId()){
            case R.id.coveron:
                startService(intent);
                break;
            case R.id.coveroff:
                stopService(intent);
                break;
        }
    }
}
