package edu.nnu.mp.musicplayer;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

public class MusicService extends Service {

    private static final int NOTIFICATION_ID = 1 ;
    private static final String TAG = "MusicService" ;

    private MediaPlayer player ;

    private int startID ;

    public MusicService() {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate ()
    {
        super.onCreate();

        //set the mediaplayer
        player =  MediaPlayer.create(this, R.raw.we);

        if(player != null) {
            player.setLooping(false);

            //stop the service when the music is finished
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //stop the service .
                    stopSelf(startID);
                }
            });


            // Create notification area notification to get back to the activity
            final Intent notificationIntent = new Intent(getApplicationContext(),
                    MainActivity.class);
            final PendingIntent pendingIntent = PendingIntent.
                    getActivity(this, 0, notificationIntent, 0);

            final Notification notification = new Notification.Builder(
                    getApplicationContext())
                    .setSmallIcon(android.R.drawable.ic_media_play)
                    .setOngoing(true).setContentTitle("Music Playing")
                    .setContentText("Click to Access Music Player")
                    .setContentIntent(pendingIntent).build();

            // Start service in foreground
            startForeground(NOTIFICATION_ID, notification);

        }
    }


    /*
     * Called by the system every time a client explicitly starts the service by calling
     * {@link android.content.Context#startService}, providing the arguments it supplied and a
     * unique integer token representing the start request.  Do not call this method directly.
     */

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (player != null) {
            // ID for this start command
            this.startID = startId;

            if (player.isPlaying()) {
                // Rewind to beginning
                player.seekTo(0);

            } else {
                // Start playing song
                player.start();
            }
        }

        // Don't automatically restart this Service if it is killed
        return START_NOT_STICKY;
    }

    /**
     * Called by the system to notify a Service that it is no longer used and is being removed.  The
     * service should clean up any resources it holds (threads, registered
     * receivers, etc) at this point.  Upon return, there will be no more calls
     * in to this Service object and it is effectively dead.  Do not call this method directly.
     */
    public void onDestroy() {

        if(player != null)
        {
            player.stop();
            player.release();
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
