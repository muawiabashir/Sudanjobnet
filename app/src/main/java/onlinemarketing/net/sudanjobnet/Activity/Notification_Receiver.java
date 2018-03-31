package onlinemarketing.net.sudanjobnet.Activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

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
                PendingIntent.FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel("ID", "Name", importance);
            notificationManager.createNotificationChannel(notificationChannel);

            builder = new NotificationCompat.Builder(context, notificationChannel.getId());
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.activity_notification);
        contentView.setTextViewText(R.id.Notifi_com, intent.getExtras().getString("company"));
        contentView.setTextViewText(R.id.notifi_title, (CharSequence) intent.getSerializableExtra("title"));
        builder = builder
                .setSmallIcon(R.mipmap.icon_sudanjob1)
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setContentTitle(context.getString(R.string.app_name))
                .setTicker(context.getString(R.string.hary))

                .setBadgeIconType(R.mipmap.icon_sudanjob1)
                //.setContentText("Last day for the following Positions:" + intent.getExtras().getString("title") + "\n\n")
                .setDefaults(Notification.DEFAULT_ALL)
                .setContent(contentView)
                .setSound(Uri.parse("android.resource://onlinemarketing.net.sudanjobnet/"+ R.raw.sudajobmp3))
                .setContentIntent(pi)

                .setAutoCancel(true);

        notificationManager.notify(0, builder.build());

    }
}