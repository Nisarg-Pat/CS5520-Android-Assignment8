package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Model.MessageHistory;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.FirebaseDB;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.Util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
    private String chatId;
    private DatabaseReference fullChatRef;
    private ChildEventListener listener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            String sender = "", receiver = "", stickerID = "";
            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                MessageHistory msg = snapshot1.getValue(MessageHistory.class);
                if (msg != null) {
                    sender = msg.getSender();
                    receiver = msg.getReceiver();
                    stickerID = msg.getMessage();
                }
            }
            String externalChatID = Util.generateChatID(sender, receiver);
            Log.d("externalchatID", externalChatID);
            Log.d("chatID", chatId);

            boolean matches = receiver.equals(FirebaseDB.currentUser.getUsername());
            if ((!externalChatID.equals(chatId) && matches) || (!Util.isForeground && matches)) {
                int id = getApplicationContext().getResources().getIdentifier(stickerID, "drawable",
                        getApplicationContext().getPackageName());
                notification.createNotification(sender, id);
            }
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

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
        Util.isInChat = true;

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        this.activity = this;
        Intent intent = getIntent();
        receiver = intent.getStringExtra("currentUserName");
        receiverName = intent.getStringExtra("clickedUserName");
        this.chatId = Util.generateChatID(FirebaseDB.currentUser.getUsername(), receiverName);
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

    public void addNotificationListener(){
        this.fullChatRef = FirebaseDB.getDataReference(getString(R.string.chat));
        fullChatRef.addChildEventListener(listener);
    }

    public void fetchChatHistory(){

        DatabaseReference chatRef = FirebaseDB.getDataReference(getString(R.string.chat)).child(this.chatId);
        addNotificationListener();
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for(DataSnapshot snap: snapshot.getChildren()){
                    MessageHistory history = (MessageHistory) snap.getValue(MessageHistory.class);

                    if(history != null){
                        messageList.add(new Message(history.getTimestamp(),
                                history.getSender(), history.getMessage()));
                    }
                }

                messageAdpater = new MessageAdapter(ChatRoom.this,messageList,
                        Util.getStickerIds(ChatRoom.this));
                chatRoomRecyclerView.setLayoutManager(new LinearLayoutManager(ChatRoom.this));
                chatRoomRecyclerView.setAdapter(messageAdpater);
                chatRoomRecyclerView.scrollToPosition(messageList.size() - 1);
                if(messageList.size() == 0){
                    messageHistoryBar.setVisibility(View.GONE);
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
    protected void onStop() {
        Util.isForeground = false;
        super.onStop();
    }

    @Override
    protected void onStart() {
        Util.isForeground = true;
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fullChatRef.removeEventListener(listener);
    }

    class GetAllChats implements Runnable{

        @Override
        public void run() {
            fetchChatHistory();
        }
    }
}