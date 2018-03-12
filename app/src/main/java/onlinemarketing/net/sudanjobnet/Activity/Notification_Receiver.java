package onlinemarketing.net.sudanjobnet.Activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

import onlinemarketing.net.sudanjobnet.Fragment.Fragment_Job_List;
import onlinemarketing.net.sudanjobnet.R;

/**
 * Created by muawia.ibrahim on 3/8/2018.
 */

public class Notification_Receiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context,
                0 /* Request code */,
                intent1,
                PendingIntent.FLAG_ONE_SHOT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel("ID", "Name", importance);
            notificationManager.createNotificationChannel(notificationChannel);

            builder = new NotificationCompat.Builder(context, notificationChannel.getId());
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.activity_notification);
        contentView.setTextViewText(R.id.title05, "Last day for the following Positions:" + intent.getExtras().getString("title") + "\n\n");
        builder = builder
                .setSmallIcon(R.mipmap.icon_sudanjob1)
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setContentTitle(context.getString(R.string.app_name))
                .setTicker(context.getString(R.string.hary))
                //.setContentText("Last day for the following Positions:" + intent.getExtras().getString("title") + "\n\n")
                .setDefaults(Notification.DEFAULT_ALL)
                .setContent(contentView)
                .setSound(Uri.parse("android.resource://onlinemarketing.net.sudanjobnet/"+ R.raw.sudajobmp3))
                .setContentIntent(pi)

                .setAutoCancel(true);

        notificationManager.notify(0, builder.build());

    }
}