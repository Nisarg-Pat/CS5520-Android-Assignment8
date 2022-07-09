package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StickerActivity extends AppCompatActivity {
    private final int[] STICKER_IDS = new int[] {R.drawable.sticker1,
            R.drawable.sticker2,R.drawable.sticker3,R.drawable.sticker4,
            R.drawable.sticker5,R.drawable.sticker6,R.drawable.sticker7,
            R.drawable.sticker8,R.drawable.sticker9,R.drawable.sticker10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticker_layout);
        List<Sticker> stickerList = new ArrayList<>();
        for (int Id:
             STICKER_IDS) {
            // This needs to be changed to reflect the stored count in the db
            stickerList.add(new Sticker("",Id,0));

        }
        StickerAdapter adapter = new StickerAdapter(this,stickerList);
        RecyclerView view = findViewById(R.id.sticker_recycler_view);
        view.setHasFixedSize(true);
        view.setLayoutManager(new GridLayoutManager(this,2));
        view.setAdapter(adapter);
    }
}
