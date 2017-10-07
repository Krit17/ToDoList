package com.example.android.todolist;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Krit17 on 07-10-2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        Bundle b=intent.getExtras();
        String title=b.getString("title");
        String activity=b.getString("activity");
        builder.setContentTitle(title);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText(activity);


        Intent intent1 = new Intent(context,CreateActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,2,intent1,0);

        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1,notification);
    }
}
