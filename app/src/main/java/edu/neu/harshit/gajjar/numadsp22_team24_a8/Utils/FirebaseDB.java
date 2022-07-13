package edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.ChatHistory;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.LoginActivity;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Model.User;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.R;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.SignupActivity;

public class FirebaseDB {

    public static User currentUser;

    // Get Reference to a specific child in database
    public static DatabaseReference getDataReference(String path){
        return FirebaseDatabase.getInstance().getReference(path);
    }

    public static FirebaseAuth getInstanceFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    // To register a new User
    public static void registerUser(String email, String password, String username, Context ct){
        getInstanceFirebaseAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser firebaseUser = getCurrentUser();
                    String userid = firebaseUser.getUid();
                    DatabaseReference ref = getDataReference(ct.getString(R.string.user_db)).child(userid);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username);
                    hashMap.put("imageURL", "default");

                    HashMap<String, String> imageCount = new HashMap<>();
                    imageCount.put("sticker1", "0");
                    imageCount.put("sticker2", "0");
                    imageCount.put("sticker3", "0");
                    imageCount.put("sticker4", "0");
                    imageCount.put("sticker5", "0");
                    imageCount.put("sticker6", "0");
                    imageCount.put("sticker7", "0");
                    imageCount.put("sticker8", "0");
                    imageCount.put("sticker9", "0");
                    imageCount.put("sticker10", "0");

                    hashMap.put("image_count", imageCount);

                    ref.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("result", task.toString());
                            if(task.isSuccessful()){
                                Intent intent = new Intent(ct, ChatHistory.class);
                                ct.startActivity(intent);
                            } else {
                                Toast.makeText(ct, "Error registering user!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public static void logout(){
        getInstanceFirebaseAuth().signOut();
    }

    public static void getDetailsCurrentUser(Context ct){
        FirebaseUser user = getCurrentUser();
        DatabaseReference ref = getDataReference(ct.getString(R.string.user_db)).child(user.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()){
                    currentUser = (User) snap.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static DatabaseReference getReferencetoRootDB(){
        return FirebaseDatabase.getInstance().getReference();
    }
}
