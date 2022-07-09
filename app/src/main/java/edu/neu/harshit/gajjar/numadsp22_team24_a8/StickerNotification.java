package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import static android.provider.Settings.System.getString;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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

    public void createNotification(String username){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.notification_message)
                .setContentTitle(username)
                .setContentText("New Sticker!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(activity);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }

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
