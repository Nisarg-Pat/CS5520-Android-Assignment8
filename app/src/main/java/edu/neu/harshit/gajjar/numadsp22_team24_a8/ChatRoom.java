package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Model.MessageHistory;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.FirebaseDB;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatRoom extends AppCompatActivity {
    private RecyclerView chatRoomRecyclerView;
    private List<Message> messageList;
    private MessageAdapter messageAdpater;
    private String receiver;
    private StickerNotification notification;
    private FloatingActionButton fab;
    private Activity activity;
    ProgressBar messageHistoryBar;
    Handler visibilityHandler = new Handler();

    private ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(new
                            ActivityResultContracts.StartActivityForResult(),
                    result ->
                    {
                        if(result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();
                            String id = intent.getStringExtra("id");
                            String name = intent.getStringExtra("name");
                            int count = intent.getIntExtra("count", 0);
                            Log.i("name is", name);
                            sendMessageToFirebase(id, name, count);
                        }
                    });

    // Receiver Username
    String receiverName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_chatroom);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        this.activity = this;
        Intent intent = getIntent();
        receiver = intent.getStringExtra("currentUserName");
        receiverName = intent.getStringExtra("clickedUserName");
        getSupportActionBar().setTitle(receiver);

        chatRoomRecyclerView = findViewById(R.id.chat_room_recycler_view);
        messageHistoryBar = findViewById(R.id.msg_history_progress_bar);

        chatRoomRecyclerView.setVisibility(View.GONE);
        messageHistoryBar.setVisibility(View.VISIBLE);

        this.fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent stickerIntent = new Intent(activity, StickerActivity.class);
            resultLauncher.launch(stickerIntent);
        });
        messageList = new ArrayList<Message>();

//        fetchChatHistory();
        new Thread(new ChatRoom.GetAllChats()).start();

        // Stickers
        this.notification = new StickerNotification(this);

    }

    public void sendMessageToFirebase(String stickerId, String stickerName, int count){
        DatabaseReference reference = FirebaseDB.getReferencetoRootDB();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", FirebaseDB.currentUser.getUsername());
        hashMap.put("receiver", receiverName);
        hashMap.put("message", stickerName); // stickerId
        hashMap.put("senderID", FirebaseDB.currentUser.getId());
//        hashMap.put("receiverID", receiverUserId);
        hashMap.put("timestamp", Instant.now().toString()); //

        String chat_id = Util.generateChatID(FirebaseDB.currentUser.getUsername(), receiverName);
        reference.child(getString(R.string.chat)).child(chat_id).push().setValue(hashMap);
        FirebaseDB.getDataReference(getString(R.string.user_db)).child(FirebaseDB.getCurrentUser().getUid())
                .child("image_count")
                .child(stickerName)
                .setValue(String.valueOf(count + 1));
    }

    public void fetchChatHistory(){
        String chatid = Util.generateChatID(FirebaseDB.currentUser.getUsername(), receiverName);
        DatabaseReference chatRef = FirebaseDB.getDataReference(getString(R.string.chat)).child(chatid);

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for(DataSnapshot snap: snapshot.getChildren()){
                    MessageHistory history = (MessageHistory) snap.getValue(MessageHistory.class);

                    if(history != null){
                        messageList.add(new Message(history.getTimestamp(),
                                history.getSender(), history.getMessage()));
                        if (!Util.generateChatID(history.getSender(),
                                history.getReceiver()).equals(chatid)) {
                            notification.createNotification(history.getReceiver());
                        }
                    }
                }

                visibilityHandler.post(() -> {
                    messageAdpater = new MessageAdapter(ChatRoom.this,messageList, Util.getStickerIds(ChatRoom.this));
                    chatRoomRecyclerView.setLayoutManager(new LinearLayoutManager(ChatRoom.this));
                    chatRoomRecyclerView.setAdapter(messageAdpater);
                    chatRoomRecyclerView.scrollToPosition(messageList.size() - 1);

                    chatRoomRecyclerView.setVisibility(View.VISIBLE);
                    messageHistoryBar.setVisibility(View.GONE);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ChatHistory.class);
        startActivity(intent);

    }

    class GetAllChats implements Runnable{

        @Override
        public void run() {
            fetchChatHistory();
        }
    }
}