package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class StickerNotification {
    private static final String CHANNEL_1_ID = "CHANNEL1";
    private static final int NOTIFICATION_ID = 1;
    private Activity activity;


    public StickerNotification(Activity activity){
        this.activity = activity;
        registerNotificationChannel();
    }

    /**
     * Creates a new notification with the passed in username.
     * @param username The username to be displayed.
     */
    public void createNotification(String username){

        Intent notifyIntent = new Intent(activity,ChatRoom.class);
        notifyIntent.putExtra("currentUserName", username);
        notifyIntent.putExtra("clickedUserName", username);
// Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
// Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                activity, 0, notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(activity, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.notification_message)
                .setContentTitle(username)
                .setContentText("New Sticker!").setContentIntent(notifyPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(activity);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }

    /**
     * Registers a notification channel. Necessary so phones running newer APIs
     * receive the notifications.
     */
    private void registerNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_1_ID, "Channel 1", importance);
            channel.setDescription("Notification channel 1");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = activity.
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
