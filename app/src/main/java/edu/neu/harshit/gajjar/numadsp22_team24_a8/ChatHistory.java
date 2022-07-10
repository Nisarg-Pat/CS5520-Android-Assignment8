package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.neu.harshit.gajjar.numadsp22_team24_a8.Model.MessageHistory;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Model.User;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.FirebaseDB;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.Util;
import pl.droidsonroids.gif.GifImageView;
public class ChatHistory extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private List<Message> chatList;
    private ChatAdapter chatAdapter;
    private String loginUserName;

    // Firebase
    FirebaseUser currentUser;
    DatabaseReference userDbRef;

    // Lists
    public ArrayList<User> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("login successful", FirebaseDB.getCurrentUser().getUid());

        // Initialization
        allUsers = new ArrayList<>();

        Intent intent = getIntent();
        loginUserName = intent.getStringExtra("currentUserName");

        // Test Data for Front End --> To be replaced by populating the list from Firebase data
        chatList = new ArrayList<Message>();
//        chatList.add(new Message("2022 Jun 24", "Alan", R.drawable.sticker1));
//        chatList.add(new Message("2022 Jun 25", "Bob", R.drawable.sticker2));
//        chatList.add(new Message("2022 Jun 26", "Chase", R.drawable.sticker3));
//        chatList.add(new Message("2022 Jun 27", "Dylan", R.drawable.sticker4));
//        chatList.add(new Message("2022 Jun 28", "Frank", R.drawable.sticker5));

        setContentView(R.layout.chat_history);
//        chatAdapter = new ChatAdapter(this, chatList);
        chatRecyclerView = findViewById(R.id.chat_history_recycler_view);
//        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        chatRecyclerView.setAdapter(chatAdapter);

        // Implement addValueEventListener for Firebase data
        getAllUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout){
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
        chatList.clear();
        for(User user: allUsers){
//            chatList.add(new Message("2022 Jun 24", user.getUsername(), R.drawable.sticker1));

            String chatId = Util.generateChatID(FirebaseDB.currentUser.getUsername(), user.getUsername());
            DatabaseReference ref = FirebaseDB.getDataReference(getString(R.string.chat)).child(chatId);;
            ref.limitToLast(1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snap: snapshot.getChildren()){
                        MessageHistory msg = (MessageHistory) snap.getValue(MessageHistory.class);
                        chatList.add(new Message(msg.getTimestamp(), user.getUsername(), Integer.valueOf(msg.getMessage())));
                    }

                    chatAdapter = new ChatAdapter(ChatHistory.this, chatList);
                    chatRecyclerView.setLayoutManager(new LinearLayoutManager(ChatHistory.this));
                    chatRecyclerView.setAdapter(chatAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}