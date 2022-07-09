package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class StickerDialog extends BottomSheetDialogFragment {
    private List<Sticker> stickerList;
    private Activity activity;
    public StickerDialog(Activity activity, List<Sticker> stickerList) {
        this.activity = activity;
        this.stickerList = stickerList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sticker_bottom_modal_dialog, container,
                false);
        // get the views and attach the listener
        RecyclerView recyclerView = view.findViewById(R.id.sticker_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,
                LinearLayoutManager.HORIZONTAL,false));
        StickerAdapter adapter = new StickerAdapter(activity,stickerList,this);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
