package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom extends AppCompatActivity {
    private RecyclerView chatRoomRecyclerView;
    private List<Message> messageList;
    private MessageAdapter messageAdpater;
    private String receiver;
    private StickerNotification notification;
    private FloatingActionButton fab;
    private Activity activity;
    private ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(new
                            ActivityResultContracts.StartActivityForResult(),
                    result ->
                            // Result of the sticker activity, use this to pull stickerID
                            Log.d("here", result.getData().getExtras().
                                    get("stickerID").toString()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        this.activity = this;
        Intent intent = getIntent();
        receiver = intent.getStringExtra("currentUserName");
        getSupportActionBar().setTitle(receiver);

        chatRoomRecyclerView = findViewById(R.id.chat_room_recycler_view);
        this.fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent stickerIntent = new Intent(activity, StickerActivity.class);
            resultLauncher.launch(stickerIntent);
        });

        messageList = new ArrayList<Message>();
        // Test Data for Front End --> To be replaced by populating the list from Firebase data
        messageList.add(new Message("2022 Jun 24", "Alan", R.drawable.sticker1));
        messageList.add(new Message("2022 Jun 25", "Sean", R.drawable.sticker5));
        messageList.add(new Message("2022 Jun 26", "Sean", R.drawable.sticker1));
        messageList.add(new Message("2022 Jun 27", "Sean", R.drawable.sticker6));
        messageList.add(new Message("2022 Jun 28", "Sean", R.drawable.sticker1));

        messageAdpater = new MessageAdapter(this, messageList);
        chatRoomRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRoomRecyclerView.setAdapter(messageAdpater);

        // Stickers
        this.notification = new StickerNotification(this);

    }
}

