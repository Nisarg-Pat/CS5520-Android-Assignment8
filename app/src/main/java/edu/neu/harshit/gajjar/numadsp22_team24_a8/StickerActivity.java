package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class StickerActivity extends AppCompatActivity implements View.OnClickListener{
    private final int[] STICKER_IDS = new int[] {R.drawable.sticker1,
            R.drawable.sticker2,R.drawable.sticker3,R.drawable.sticker4,
            R.drawable.sticker5,R.drawable.sticker6,R.drawable.sticker7,
            R.drawable.sticker8,R.drawable.sticker9,R.drawable.sticker10};

    private StickerNotification notification;
    private StickerDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sticker_layout);
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
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab){
            dialog.show(getSupportFragmentManager(),
                    "sticker_fragment");
        }
    }
}
