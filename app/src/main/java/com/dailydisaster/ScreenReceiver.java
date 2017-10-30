package com.dailydisaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by song on 2016-01-12.
 */
public class ScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(intent.ACTION_SCREEN_OFF)){
            Intent i = new Intent(context, CoverActivity.class);
            i.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

    }
}
