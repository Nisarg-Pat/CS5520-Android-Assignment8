package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ChatHistory extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private List<Message> messageList;
    private ChatAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_history);

        messageList = new ArrayList<Message>();
        chatAdapter = new ChatAdapter(this, messageList);
    }
}