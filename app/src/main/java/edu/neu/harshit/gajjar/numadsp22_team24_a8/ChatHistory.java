package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import edu.neu.harshit.gajjar.numadsp22_team24_a8.Model.MessageHistory;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Model.User;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.FirebaseDB;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.Util;

public class ChatHistory extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private Map<String, Message> chatMap;
    private ChatAdapter chatAdapter;
    private String loginUserName;
    private FloatingActionButton newChatButton;
    private StickerNotification notification;
    private ProgressBar userListBar;
    private boolean newUser;
    Handler visibilityHandler = new Handler();

    // Firebase
    FirebaseUser currentUser;
    DatabaseReference userDbRef;

    // Lists
    public ArrayList<User> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_history);
        Toolbar toolbar = findViewById(R.id.toolbar_chathistory);
        setSupportActionBar(toolbar);
        Log.i("login successful", FirebaseDB.getCurrentUser().getUid());
        Util.isInChat = false;
        this.notification = new StickerNotification(this);
        addNotificationListener();

        // Initialization
        userListBar = findViewById(R.id.users_list_progress_bar);
        allUsers = new ArrayList<>();
        Intent intent = getIntent();

        loginUserName = intent.getStringExtra("currentUserName");
        if (Util.newUser){
            findViewById(R.id.no_chats_text_view).setVisibility(View.VISIBLE);
            findViewById(R.id.no_chats_gif).setVisibility(View.VISIBLE);
        }

        chatMap = new HashMap<>();

        chatRecyclerView = findViewById(R.id.chat_history_recycler_view);
//        getAllUsers();


        chatRecyclerView.setVisibility(View.GONE);
        userListBar.setVisibility(View.VISIBLE);
        new Thread(new ChatHistory.AllUsersListChats()).start();

        newChatButton = findViewById(R.id.new_chat);
        newChatButton.setOnClickListener(v -> {
            Intent newChatIntent = new Intent(ChatHistory.this, NewChatActivity.class);
            startActivity(newChatIntent);
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            // Logout
            FirebaseDB.logout();
            Intent intent = new Intent(ChatHistory.this, LoginActivity.class);
            finish();
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getAllUsers(){
        currentUser = FirebaseDB.getCurrentUser();
        userDbRef = FirebaseDB.getDataReference(getString(R.string.user_db));
        userDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allUsers.clear();
                for(DataSnapshot snap: snapshot.getChildren()){
                    User user = (User) snap.getValue(User.class);

                    // Fetch all users except the current User
                    if(user != null && !user.getId().equals(FirebaseDB.getCurrentUser().getUid())){
                         allUsers.add(user);
                    } else {
                        FirebaseDB.currentUser = user;
                    }
                }

                if(allUsers.size() >= 1){
                    populateChatList();
                } else {
                    userListBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void populateChatList(){
        chatMap.clear();
        for(User user: allUsers) {
            String chatId = Util.generateChatID(FirebaseDB.currentUser.getUsername(), user.getUsername());
            DatabaseReference ref = FirebaseDB.getDataReference(getString(R.string.chat)).child(chatId);
            ref.limitToLast(1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snap: snapshot.getChildren()){
                        MessageHistory msg = (MessageHistory) snap.getValue(MessageHistory.class);
                        chatMap.put(chatId, new Message(msg.getTimestamp(),
                                user.getUsername(),
                                msg.getMessage()));
                    }
                        visibilityHandler.post(() -> {

//                            findViewById(R.id.no_chats_gif).setVisibility(View.INVISIBLE);
//                            findViewById(R.id.no_chats_text_view).setVisibility(View.INVISIBLE);
                            chatAdapter = new ChatAdapter(ChatHistory.this,
                                    new ArrayList<>(chatMap.values()));
                            chatRecyclerView.setLayoutManager(new
                                    LinearLayoutManager(ChatHistory.this));
                            chatRecyclerView.setAdapter(chatAdapter);
                            userListBar.setVisibility(View.GONE);
                            chatRecyclerView.setVisibility(View.VISIBLE);
                        });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


    class AllUsersListChats implements Runnable{

        @Override
        public void run() {
            getAllUsers();
        }
    }
    public void addNotificationListener(){
        DatabaseReference fullchatRef = FirebaseDB.getDataReference(getString(R.string.chat));
        fullchatRef.addChildEventListener(new ChildEventListener() {
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
                Log.d("receiver",receiver);
                Log.d("sender",sender);
                Log.d("username",FirebaseDB.currentUser.getUsername());
                Log.d("util", String.valueOf(Util.isInChat));
                if (receiver.equals(FirebaseDB.currentUser.getUsername()) && !Util.isInChat) {
                    int id = getApplicationContext().getResources().getIdentifier(stickerID,
                            "drawable", getApplicationContext().getPackageName());
                    notification.createNotification(sender,id);
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
        });
    }

}