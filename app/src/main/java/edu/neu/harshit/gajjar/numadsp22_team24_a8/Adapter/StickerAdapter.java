package edu.neu.harshit.gajjar.numadsp22_team24_a8.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.neu.harshit.gajjar.numadsp22_team24_a8.R;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Model.Sticker;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.Util;
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
    private Handler hander = new Handler();
    int selectedSticker;
    public StickerAdapter(Context context, List<Sticker> stickers, Activity activity) {
        this.context = context;
        this.stickers = stickers;
        this.selectedSticker = -1;
        this.activity = activity;
        this.fab = activity.findViewById(R.id.fab1);
        fab.setOnClickListener(v -> {
            if (!Util.isNetworkConnected(context)) {
                Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            if (selectedSticker != -1) {
                intent.putExtra("name", stickers.
                        get(selectedSticker).getName());
                intent.putExtra("count", stickers.
                        get(selectedSticker).getCountSent());
                intent.putExtra("id", String.valueOf(stickers.
                        get(selectedSticker).getId()));
                activity.setResult(Activity.RESULT_OK, intent);
                activity.finish();
            }
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
        holder.stickerCount.setText(String.valueOf(sticker.getCountSent()));
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
            }
            notifyItemChanged(selectedSticker);
            notifyItemChanged(holder.getAdapterPosition());
            selectedSticker = holder.getAdapterPosition();
        });
        if(sticker.isSelected()) {
            fab.setVisibility(View.VISIBLE);
            holder.stickerIcon.setBackground(AppCompatResources.getDrawable(context,
                    R.drawable.send_message_shape));
            Glide.with(context).load(AppCompatResources.getDrawable(context,sticker.getId())).into(holder.stickerIcon);
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


