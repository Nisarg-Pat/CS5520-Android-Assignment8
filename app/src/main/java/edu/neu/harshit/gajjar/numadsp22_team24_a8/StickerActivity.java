package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.installations.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.harshit.gajjar.numadsp22_team24_a8.Model.User;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.FirebaseDB;

public class StickerActivity extends AppCompatActivity {
    private final int[] STICKER_IDS = new int[] {R.drawable.sticker1,
            R.drawable.sticker2,R.drawable.sticker3,R.drawable.sticker4,
            R.drawable.sticker5,R.drawable.sticker6,R.drawable.sticker7,
            R.drawable.sticker8,R.drawable.sticker9,R.drawable.sticker10};
    private StickerNotification notification;
    private FloatingActionButton fab;
    private HashMap<String, Sticker> imageCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticker_layout);
        this.notification = new StickerNotification(this);
        Map<String, Sticker> stickerList = new HashMap<>();
        this.fab = findViewById(R.id.fab1);
        fab.setVisibility(View.INVISIBLE);

        for (int i = 0;i<10;i++){
            // This needs to be changed to reflect the stored count in the db
            stickerList.put("sticker"+(i+1), new Sticker("sticker"+(i+1),STICKER_IDS[i],99));
        }

        RecyclerView recyclerView = findViewById(R.id.sticker_recycler_view);
        recyclerView.setHasFixedSize(true);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }


        imageCount = new HashMap<>();
        // to get the count of images
        FirebaseDB.getDataReference(getString(R.string.user_db)).child(FirebaseDB.getCurrentUser().getUid()).child("image_count").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()) {
                    stickerList.get(snap.getKey()).setCountSent(Integer.valueOf((String) snap.getValue()));
                }

                StickerAdapter stickerAdapter = new StickerAdapter(StickerActivity.this, new ArrayList<>(stickerList.values()), StickerActivity.this);
                recyclerView.setAdapter(stickerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
