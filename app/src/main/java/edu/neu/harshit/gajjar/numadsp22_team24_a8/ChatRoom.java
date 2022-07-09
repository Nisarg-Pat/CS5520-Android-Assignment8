package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.FirebaseDB;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.Util;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatRoom extends AppCompatActivity {
    private RecyclerView chatRoomRecyclerView;
    private List<Message> messageList;
    private MessageAdapter messageAdpater;
    private String receiver;
    private final int[] STICKER_IDS = new int[] {R.drawable.sticker1,
            R.drawable.sticker2,R.drawable.sticker3,R.drawable.sticker4,
            R.drawable.sticker5,R.drawable.sticker6,R.drawable.sticker7,
            R.drawable.sticker8,R.drawable.sticker9,R.drawable.sticker10};
    private StickerNotification notification;
    private StickerDialog dialog;

    // Receiver Username
    String receiverName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Intent intent = getIntent();
        receiver = intent.getStringExtra("currentUserName");
        receiverName = intent.getStringExtra("clickedUserName");
        getSupportActionBar().setTitle(receiver);

        chatRoomRecyclerView = findViewById(R.id.chat_room_recycler_view);

        messageList = new ArrayList<Message>();
        // Test Data for Front End --> To be replaced by populating the list from Firebase data

//        messageList.add(new Message("2022 Jun 24", "Alan", R.drawable.sticker1));
//        messageList.add(new Message("2022 Jun 25", "Sean", R.drawable.sticker5));
//        messageList.add(new Message("2022 Jun 26", "Sean", R.drawable.sticker1));
//        messageList.add(new Message("2022 Jun 27", "Sean", R.drawable.sticker6));
//        messageList.add(new Message("2022 Jun 28", "Sean", R.drawable.sticker1));

        messageAdpater = new MessageAdapter(this,messageList);
        chatRoomRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRoomRecyclerView.setAdapter(messageAdpater);

        // Stickers
        this.notification = new StickerNotification(this);
        List<Sticker> stickerList = new ArrayList<>();
        for (int Id:
                STICKER_IDS) {
            // This needs to be changed to reflect the stored count in the db
            stickerList.add(new Sticker("",Id,0));

        }
        this.dialog = new StickerDialog(this,stickerList);
        // Returns the id of the sticker clicked on
        getSupportFragmentManager().setFragmentResultListener("clicked_on_sticker",
                this, (requestKey, result) -> {
                    int id = result.getInt("id");
                    Log.d("stickerID",String.valueOf(id));
                    // Implement Firebase new message logic

                    sendMessageToFirebase(String.valueOf(id));
                });

    }

    public void openStickers(View view) {
        dialog.show(getSupportFragmentManager(),
                "sticker_fragment");

    }


    public void sendMessageToFirebase(String stickerId){
        Log.i("current_username", FirebaseDB.currentUser.getUsername());
        Log.i("receiver_username", receiverName);

        DatabaseReference reference = FirebaseDB.getReferencetoRootDB();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", FirebaseDB.currentUser.getUsername());
        hashMap.put("receiver", receiverName);
        hashMap.put("message", stickerId);
        hashMap.put("senderID", FirebaseDB.currentUser.getId());
//        hashMap.put("receiverID", receiverUserId);
        hashMap.put("timestamp", Util.getGMTTimestamp());

        String chat_id = Util.generateChatID(FirebaseDB.currentUser.getUsername(), receiverName);
        reference.child(getString(R.string.chat)).child(chat_id).push().setValue(hashMap);

//        ref.child(receiverUserId).child("chatIDs").push().setValue(chat_id);
    }
}