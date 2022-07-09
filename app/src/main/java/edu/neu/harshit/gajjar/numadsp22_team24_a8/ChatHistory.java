package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ChatHistory extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private List<Message> chatList;
    private ChatAdapter chatAdapter;
    private String loginUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        loginUserName = intent.getStringExtra("currentUserName");

        // Test Data for Front End --> To be replaced by populating the list from Firebase data
        chatList = new ArrayList<Message>();
        chatList.add(new Message("2022 Jun 24", "Alan", "Sticker A"));
        chatList.add(new Message("2022 Jun 25", "Bob", "Sticker C"));
        chatList.add(new Message("2022 Jun 26", "Chase", "Sticker A"));
        chatList.add(new Message("2022 Jun 27", "Dylan", "Sticker X"));
        chatList.add(new Message("2022 Jun 28", "Frank", "Sticker A"));

        setContentView(R.layout.chat_history);
        chatAdapter = new ChatAdapter(this, chatList);
        chatRecyclerView = findViewById(R.id.chat_history_recycler_view);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);

        // Implement addValueEventListener for Firebase data
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout){
            // Implement Firebase logout
            Intent intent = new Intent(ChatHistory.this, LoginActivity.class);
            finish();
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}