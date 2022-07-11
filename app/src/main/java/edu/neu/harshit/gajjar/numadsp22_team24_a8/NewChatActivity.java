package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import edu.neu.harshit.gajjar.numadsp22_team24_a8.Model.User;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.FirebaseDB;


public class NewChatActivity extends AppCompatActivity {

    private RecyclerView userListRecyclerView;
    private ArrayList<User> allUsers;
    private NewChatAdapter newChatAdapter;

    // Firebase
    FirebaseUser currentUser;
    DatabaseReference userDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_new_chat);
        toolbar.setTitle("Contacts");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        userListRecyclerView = findViewById(R.id.user_list_recycler_view);

        allUsers = new ArrayList<>();
        getAllUsers();
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
                Log.i("number of users new chat activity", String.valueOf(allUsers.size()));
                if(allUsers.size() > 0){
                    newChatAdapter = new NewChatAdapter(NewChatActivity.this, allUsers);
                    userListRecyclerView.setLayoutManager(new LinearLayoutManager(NewChatActivity.this));
                    userListRecyclerView.setAdapter(newChatAdapter);
                }

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

}
