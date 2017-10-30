package com.dailydisaster;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by PSH on 2015-12-05.
 */
public class PushService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("service", "onStartCommand 실행");

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, i, 0);
        Notification.Builder builder = new Notification.Builder(PushService.this);

        builder.setAutoCancel(true);
        builder.setTicker("DailyDisaster 알림");
        builder.setContentTitle("비가 올 예정입니다.");
        builder.setContentText("우산을 챙기세요.");
        builder.setSmallIcon(R.drawable.um);
        builder.setContentIntent(pi);

        Notification notification = builder.getNotification();
        manager.notify(123, notification);

        play();

        return START_STICKY_COMPATIBILITY;
    }
    public void play() {
        MediaPlayer m = MediaPlayer.create(getApplicationContext(), R.raw.a);
        m.start();
    }
}
