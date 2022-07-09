package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Message> messageList;
    static int ITEM_RECEIVE = 1;
    static int ITEM_SENT = 2;

    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
//        return new ChatViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_row, parent,false));
        //
    }

    @Override
    public int getItemViewType(int position) {
        Message currentMessage = messageList.get(position);
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getClass() == SendViewHolder.class){
            SendViewHolder viewHolder = (SendViewHolder) holder;
            Message currentMessage = messageList.get(position);
            viewHolder.sent_datetime.setText(currentMessage.getDatetime());
            viewHolder.sent_username.setText(currentMessage.getUsername());
            viewHolder.sent_sticker.setText(currentMessage.getSticker());
        }
        else{
            ReceiveViewHolder viewHolder = (ReceiveViewHolder) holder;
            Message currentMessage = messageList.get(position);
            viewHolder.receive_datetime.setText(currentMessage.getDatetime());
            viewHolder.receive_username.setText(currentMessage.getUsername());
            viewHolder.receive_sticker.setText(currentMessage.getSticker());
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class SendViewHolder extends RecyclerView.ViewHolder {
        TextView sent_username, sent_datetime, sent_sticker;

        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            sent_username = itemView.findViewById(R.id.sent_username);
            sent_datetime = itemView.findViewById(R.id.sent_datetime);
            sent_sticker = itemView.findViewById(R.id.sent_sticker);
        }
    }
    class ReceiveViewHolder extends RecyclerView.ViewHolder {
        TextView receive_username, receive_datetime, receive_sticker;

        public ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            receive_username = itemView.findViewById(R.id.receive_username);
            receive_datetime = itemView.findViewById(R.id.receive_datetime);
            receive_sticker = itemView.findViewById(R.id.receive_sticker);
        }
    }
}