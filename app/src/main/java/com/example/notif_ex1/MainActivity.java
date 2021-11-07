package com.example.notif_ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat mNotificationManagerCompat;

    public static final String NORMAL_CHANNEL = "NORMAL_CHANNEL";
    public static final String IMPORTANT_CHANNEL = "IMPORTANT_CHANNEL";

    private int numberOfMessages = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManagerCompat = NotificationManagerCompat.from(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String nameNI = getResources().getString(R.string.NOT_IMPORTANT_CHANNEL_NAME);
            NotificationChannel channelNI = new NotificationChannel(
                    NORMAL_CHANNEL,
                    nameNI,
                    NotificationManager.IMPORTANCE_LOW
            );
            channelNI.setDescription(
                    getResources().getString(R.string.NOT_IMPORTANT_CHANNEL_DESCRIPTION)
            );
            channelNI.enableVibration(false);
            mNotificationManagerCompat.createNotificationChannel(channelNI);


            String nameI = getResources().getString(R.string.IMPORTANT_CHANNEL_NAME);
            NotificationChannel channelI = new NotificationChannel(
                    IMPORTANT_CHANNEL,
                    nameI,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channelI.setDescription(
                    getResources().getString(R.string.IMPORTANT_CHANNEL_DESCRIPTION)
            );
            channelI.enableVibration(false);

            mNotificationManagerCompat.createNotificationChannel(channelI);
        }
    }

    public void button_Click(View view){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NORMAL_CHANNEL);

        builder.setContentTitle("Action 2");
        builder.setContentText("Перейти к Action 2");
        Intent intent2 = new Intent(this, MainActivity2.class);
        PendingIntent a2pending = PendingIntent.getActivity(
                this, R.id.PENDING_ID,
                intent2, PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.setContentIntent(a2pending);

        Intent intent3 = new Intent(this, MainActivity3.class);
        PendingIntent a3pending = PendingIntent.getActivity(
                this, R.id.PENDING_ID,
                intent3, PendingIntent.FLAG_UPDATE_CURRENT
        );
        builder.addAction(android.R.drawable.star_on, "Открыть Activity 3",
                a3pending);

        builder.setAutoCancel(true);
        for(int i = 0; i < 10; i++)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i%2==0)
                builder.setSmallIcon(android.R.drawable.alert_light_frame);
            else
                builder.setSmallIcon(android.R.drawable.ic_dialog_alert);

            mNotificationManagerCompat.notify(
                    R.id.NOTIFICATION_ID,
                    builder.build()
            );
        }
        mNotificationManagerCompat.cancel(R.id.NOTIFICATION_ID);
    }

}