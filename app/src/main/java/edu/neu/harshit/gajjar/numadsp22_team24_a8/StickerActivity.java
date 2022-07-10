package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StickerActivity extends AppCompatActivity {
    private final int[] STICKER_IDS = new int[] {R.drawable.sticker1,
            R.drawable.sticker2,R.drawable.sticker3,R.drawable.sticker4,
            R.drawable.sticker5,R.drawable.sticker6,R.drawable.sticker7,
            R.drawable.sticker8,R.drawable.sticker9,R.drawable.sticker10};
    private StickerNotification notification;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticker_layout);
        this.notification = new StickerNotification(this);
        List<Sticker> stickerList = new ArrayList<>();
        this.fab = findViewById(R.id.fab1);
        fab.setVisibility(View.INVISIBLE);
        for (int Id:
                STICKER_IDS) {
            // This needs to be changed to reflect the stored count in the db
            stickerList.add(new Sticker("",Id,0));
        }
        RecyclerView recyclerView = findViewById(R.id.sticker_recycler_view);
        recyclerView.setHasFixedSize(true);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }
        StickerAdapter adapter = new StickerAdapter(this, stickerList,this);
        recyclerView.setAdapter(adapter);
    }
}
