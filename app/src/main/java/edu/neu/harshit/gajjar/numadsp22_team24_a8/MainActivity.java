package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference db;

    public List<Message> getChatMessagesByID(String chatID){
        List<Message> msgList = new ArrayList<>();
        Query keys = db.child("Messages").child(chatID).orderByValue();
        keys.get().addOnSuccessListener(dataSnapshot -> {
            dataSnapshot.getChildren().forEach(dataSnapshot1 -> msgList.add(dataSnapshot1.
                    getValue(Message.class)));
            Log.d("messages",msgList.toString());
            // Send the inflated messages to a recyclerview whenever a new fragment opened?
        });
        return msgList;
    }

    public String createNewMessage(String sender, String recipient,String chatID,
                                    String stickerID){
        String msgID = UUID.randomUUID().toString();
        db.child("Messages").child(chatID).child(msgID).
                setValue(new Message(recipient,sender,stickerID));
        db.child("Users").child(sender).child("stickerCount").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                // Reinflates the hashmap
                GenericTypeIndicator<HashMap<String,Integer>> t = new
                        GenericTypeIndicator<HashMap<String,Integer>>(){};
                Map<String,Integer> map = currentData.getValue(t);
                if (map != null) {
                    map.replace(stickerID,map.get(stickerID)+1);
                    currentData.setValue(map);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                Log.d("Status", error == null ? "Message created" : error.toString());
            }
        });
        return msgID;
    }

    public String createNewChat(String userOne, String userTwo){
        // Ordered by alphabetically smaller names to prevent duplicate chats
        String chatID = userOne.compareTo(userTwo) < 0 ?
                userOne+"_"+userTwo : userTwo+"_"+userOne;
        db.child("Messages").setValue(chatID);
        return chatID;
    }

    public void createNewUser(String username){
        class StickerCount{
            public final HashMap<String,Integer> stickerCount = new HashMap<>();
            public StickerCount(){
                for(int i=1;i<6;i++) {
                    stickerCount.put("Id"+i,0);
                }
            }
        }
        // Check if the
        db.child("Users").child(username).setValue(new StickerCount());
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase
        this.db = FirebaseDatabase.getInstance().getReference();
        String id = createNewChat("Chris","Harshit");
        String id2 = createNewChat("Nisarg","Zhenyu");
        createNewMessage("Harshit","Chris",id,"Id1");
        createNewMessage("Chris","Harshit",id,"Id2");
        createNewMessage("Nisarg","Zhenyu",id2,"Id3");
        createNewMessage("Zhenyu","Nisarg",id2,"Id2");
        getChatMessagesByID(id2);
    }
}