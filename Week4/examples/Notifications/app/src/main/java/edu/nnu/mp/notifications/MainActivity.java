package edu.nnu.mp.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createSimpleNotification();
        createExpandableNotification();
        createABigViewNotification();
    }




    private  void createSimpleNotification ()
    {

        Notification.Builder myNote = new Notification.
                Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("my notifiication 2")
                .setContentText("this is the body of the notification")
                .setAutoCancel(false)
                ;


        Intent myIntent = new Intent(this, MainActivity.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            android.app.TaskStackBuilder stackBuilder =
                    android.app.TaskStackBuilder.create(this);

            stackBuilder.addNextIntentWithParentStack(myIntent);

            PendingIntent pendingIntent =
                    stackBuilder.getPendingIntent(0,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            myNote.setContentIntent(pendingIntent);

            NotificationManager myManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            myManager.notify(1 , myNote.build());
        }

    }
    private void createExpandableNotification (){
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Wooooow this is my first notification !")
                .setAutoCancel(false);

        Intent intent = new Intent(this,ResultActivity.class);
//        Intent intent = new Intent(this,MainActivity.class);

        TaskStackBuilder stackBuilder =  TaskStackBuilder.create(this);

        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Event tracker details:");

        // Moves events into the expanded layout
        String[] events = {"This is an expanded notification" , "you can find more ", "the following links " ,"http://codeversed.com/expandable-notifications-android/" , "https://developer.android.com/guide/topics/ui/notifiers/notifications.html"};

        for (int i=0; i < events.length; i++) {

            inboxStyle.addLine(events[i]);
        }
        // Moves the expanded layout object into the notification object.
        builder.setStyle(inboxStyle);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(3, builder.build());
    }

    private void createABigViewNotification ()
    {

        // Sets up the Snooze and Dismiss action buttons that will appear in the
// big view of the notification.
        Intent dismissIntent = new Intent(this, MainActivity.class);
        dismissIntent.setAction("DISMISS");
        PendingIntent piDismiss = PendingIntent.getService(this, 0, dismissIntent, 0);

        Intent snoozeIntent = new Intent(this, ResultActivity.class);
        snoozeIntent.setAction("SNOOZE");
        PendingIntent piSnooze = PendingIntent.getService(this, 0, snoozeIntent, 0);


        String msg = " * Sets the big view \"big text\" style and supplies the \n"
                +"* text (the user's reminder message) that will be displayed \n"
                +"* in the detail area of the expanded notification.\n"
                +"* These calls are ignored by the support library for \n"
                +"* pre-4.1 devices.";


        // Constructs the Builder object.
        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText("This is my bigview notification")
                        .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission

                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .addAction (R.mipmap.ic_launcher,
                                "dismiss", piDismiss)
                        .addAction (R.mipmap.ic_launcher,
                                "snooze", piSnooze);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      // mId allows you to update the notification later on.
        mNotificationManager.notify(2, builder.build());

    }


}



