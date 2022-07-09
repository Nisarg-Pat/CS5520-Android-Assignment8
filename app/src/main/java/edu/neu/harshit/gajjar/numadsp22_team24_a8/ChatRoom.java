package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ChatRoom extends AppCompatActivity {
    private StickerNotification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        this.notification = new StickerNotification(this);
    }
}