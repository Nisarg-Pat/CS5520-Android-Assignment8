package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    private final Context context;
    private final List<Sticker> stickers;
    private final FloatingActionButton fab;
    private StickerViewHolder holder;
    private Activity activity;
    int selectedSticker;
    public StickerAdapter(Context context, List<Sticker> stickers, Activity activity) {
        this.context = context;
        this.stickers = stickers;
        this.selectedSticker = -1;
        this.activity = activity;
        this.fab = activity.findViewById(R.id.fab1);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("stickerID",stickers.
                    get(holder.getAdapterPosition()).getId());
            activity.setResult(1,intent);
            activity.finish();
        });
    }

    @NonNull
    @Override
    public StickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StickerViewHolder holder = new StickerViewHolder(LayoutInflater.from(context).inflate(R
                .layout.sticker_recycler_view_item, parent, false));
        this.holder = holder;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StickerViewHolder holder, int position) {
        Sticker sticker = stickers.get(position);
        holder.stickerIcon.setImageDrawable(AppCompatResources.getDrawable(context, sticker.getId()));
        holder.stickerIcon.setOnClickListener((v) -> {
            if(selectedSticker == holder.getAdapterPosition() && sticker.isSelected()) {
                sticker.setSelected(false);
                selectedSticker = -1;
                fab.setVisibility(View.INVISIBLE);
            } else if (selectedSticker == -1) {
                sticker.setSelected(true);
            } else {
                stickers.get(selectedSticker).setSelected(false);
                sticker.setSelected(true);
                notifyItemChanged(selectedSticker);
            }
            notifyItemChanged(holder.getAdapterPosition());
            selectedSticker = holder.getAdapterPosition();
        });
        if(sticker.isSelected()) {
            fab.setVisibility(View.VISIBLE);
            holder.stickerIcon.setBackground(AppCompatResources.getDrawable(context,
                    R.drawable.send_message_shape));
        } else {
            holder.stickerIcon.setBackgroundColor(Color.TRANSPARENT);
        }
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


