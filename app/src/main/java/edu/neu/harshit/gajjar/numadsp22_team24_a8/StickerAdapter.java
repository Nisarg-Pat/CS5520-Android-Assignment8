package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.StickerViewHolder> {

    public static class StickerViewHolder extends RecyclerView.ViewHolder {
        private final GifImageView stickerIcon;
        private final TextView stickerCount;
        public StickerViewHolder(@NonNull View itemView) {
            super(itemView);
            stickerIcon = itemView.findViewById(R.id.sticker_image);
            stickerCount = itemView.findViewById(R.id.sticker_count_tv);
        }
    }

    private final StickerDialog dialog;
    private final Context context;
    private final List<Sticker> stickers;
    int selectedSticker;
    public StickerAdapter(Context context, List<Sticker> stickers, StickerDialog dialog) {
        this.context = context;
        this.stickers = stickers;
        this.selectedSticker = -1;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StickerViewHolder holder = new StickerViewHolder(LayoutInflater.from(context).inflate(R
                .layout.sticker_recycler_view_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        Sticker sticker = stickers.get(position);
        holder.stickerIcon.setImageDrawable(AppCompatResources.getDrawable(context, sticker.getId()));
        holder.stickerIcon.setOnClickListener(v -> {
            // Send the clicked on ID to the activity's fragment result listener
            holder.stickerCount.setText(String.valueOf(sticker.getCountSent()));
            Bundle bundle = new Bundle();
            bundle.putInt("id", sticker.getId());
            dialog.getParentFragmentManager().setFragmentResult("clicked_on_sticker",
                    bundle);
        });
//        holder.stickerIcon.setOnClickListener((v) -> {
//            if(selectedSticker == holder.getAdapterPosition()) {
//                sticker.setSelected(false);
//                selectedSticker = -1;
//            } else if (selectedSticker == -1) {
//                sticker.setSelected(true);
//            } else {
//                stickers.get(selectedSticker).setSelected(false);
//                sticker.setSelected(true);
//                notifyItemChanged(selectedSticker);
//            }
//            notifyItemChanged(holder.getAdapterPosition());
//            selectedSticker = holder.getAdapterPosition();
//        });
//        if(sticker.isSelected()) {
//            holder.stickerIcon.setBackground(AppCompatResources.getDrawable(context,
//                    R.drawable.blue_border));
//        } else {
//            holder.stickerIcon.setBackgroundColor(Color.TRANSPARENT);
//        }

    }

    @Override
    public int getItemCount() {
        return stickers.size();
    }

    public Sticker getSelectedSticker() {
        if(selectedSticker == -1) {
            return null;
        }
        return stickers.get(selectedSticker);
    }
}
