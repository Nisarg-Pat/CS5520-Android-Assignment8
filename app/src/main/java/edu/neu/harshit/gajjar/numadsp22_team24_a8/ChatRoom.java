package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom extends AppCompatActivity {
    private RecyclerView chatRoomRecyclerView;
    private List<Message> messageList;
    private MessageAdapter messageAdpater;
    private String receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Intent intent = getIntent();
        receiver = intent.getStringExtra("currentUserName");
        getSupportActionBar().setTitle(receiver);

        chatRoomRecyclerView = findViewById(R.id.chat_room_recycler_view);

        messageList = new ArrayList<Message>();
        // Test Data for Front End --> To be replaced by populating the list from Firebase data
        messageList.add(new Message("2022 Jun 24", "Alan", "Sticker A"));
        messageList.add(new Message("2022 Jun 25", "Sean", "Sticker C"));
        messageList.add(new Message("2022 Jun 26", "Sean", "Sticker A"));
        messageList.add(new Message("2022 Jun 27", "Sean", "Sticker X"));
        messageList.add(new Message("2022 Jun 28", "Sean", "Sticker A"));

        messageAdpater = new MessageAdapter(this,messageList);
        chatRoomRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRoomRecyclerView.setAdapter(messageAdpater);
    }
}