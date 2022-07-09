package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private Context context;
    private List<Message> messageList;

    public ChatAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_row, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message currentMessage = messageList.get(position);
        holder.chat_text_datetime.setText(currentMessage.getDatetime());
        holder.chat_text_username.setText(currentMessage.getUsername());
        holder.chat_text_last_sticker.setText(currentMessage.getSticker());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView chat_text_username, chat_text_datetime, chat_text_last_sticker;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            chat_text_username = itemView.findViewById(R.id.chat_text_username);
            chat_text_datetime = itemView.findViewById(R.id.chat_text_datetime);
            chat_text_last_sticker = itemView.findViewById(R.id.chat_text_sticker);
        }
    }
}

