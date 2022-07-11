package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
    // Firebase
    FirebaseUser currentUser;
    DatabaseReference userDbRef;

    // Lists
    public ArrayList<User> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_chathistory);
        setSupportActionBar(toolbar);
        Log.i("login successful", FirebaseDB.getCurrentUser().getUid());

        // Initialization
        allUsers = new ArrayList<>();

        Intent intent = getIntent();
        loginUserName = intent.getStringExtra("currentUserName");

        // Test Data for Front End --> To be replaced by populating the list from Firebase data
        chatMap = new HashMap<>();

        chatRecyclerView = findViewById(R.id.chat_history_recycler_view);
        getAllUsers();
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
                Log.i("number of users", String.valueOf(allUsers.size()));

                if(allUsers.size() > 0){
                    populateChatList();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void populateChatList(){
        chatMap.clear();
        for(User user: allUsers){
            String chatId = Util.generateChatID(FirebaseDB.currentUser.getUsername(), user.getUsername());
            DatabaseReference ref = FirebaseDB.getDataReference(getString(R.string.chat)).child(chatId);
            ref.limitToLast(1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snap: snapshot.getChildren()){
                        MessageHistory msg = (MessageHistory) snap.getValue(MessageHistory.class);
                        chatMap.put(chatId, new Message(msg.getTimestamp(),
                                user.getUsername(),
                                Integer.valueOf(msg.getMessage())));
                    }
                    chatAdapter = new ChatAdapter(ChatHistory.this, new ArrayList<>(chatMap.values()));
                    chatRecyclerView.setLayoutManager(new LinearLayoutManager(ChatHistory.this));
                    chatRecyclerView.setAdapter(chatAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    public void newChat(View view){

    }

}