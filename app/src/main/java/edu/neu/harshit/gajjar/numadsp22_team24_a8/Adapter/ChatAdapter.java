package edu.neu.harshit.gajjar.numadsp22_team24_a8.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;

import edu.neu.harshit.gajjar.numadsp22_team24_a8.ChatRoom;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Model.Message;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.R;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.Util;
import pl.droidsonroids.gif.GifImageView;
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private Context context;
    private List<Message> messageList;
    private HashMap<String, Integer> stringIds;

    public ChatAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
        stringIds = new HashMap<>();
        stringIds = Util.getStickerIds(context);
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_row, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message currentMessage = messageList.get(position);
        holder.chat_text_datetime.setText(Util.convertTocurrentDateTime(currentMessage.getDatetime()));
        holder.chat_text_username.setText(currentMessage.getUsername());
        int stickerId = stringIds.getOrDefault(currentMessage.getSticker(), R.drawable.question);
        holder.chat_sticker.setImageDrawable(AppCompatResources.getDrawable(context,stickerId));
        Glide.with(context).load(AppCompatResources.getDrawable(context,stickerId)).into(holder.chat_sticker);
        holder.itemView.setOnClickListener((View v)->{
            Intent intent = new Intent(context, ChatRoom.class);
            intent.putExtra("currentUserName", currentMessage.getUsername().toString());
            intent.putExtra("clickedUserName", currentMessage.getUsername().toString());
            context.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView chat_text_username, chat_text_datetime;
        GifImageView chat_sticker;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            chat_text_username = itemView.findViewById(R.id.chat_text_username);
            chat_text_datetime = itemView.findViewById(R.id.chat_text_datetime);
            chat_sticker = itemView.findViewById(R.id.chat_sticker);

        }
    }
}

