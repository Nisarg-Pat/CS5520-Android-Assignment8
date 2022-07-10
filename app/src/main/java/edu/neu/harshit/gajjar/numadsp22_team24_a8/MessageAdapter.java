package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.FirebaseDB;
import pl.droidsonroids.gif.GifImageView;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Message> messageList;
    static int ITEM_RECEIVE = 1;
    static int ITEM_SENT = 2;
    String loginUserName = "Sean";

    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1){
            View view = LayoutInflater.from(context).inflate(R.layout.receive, parent,false);
            return new ReceiveViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.sent, parent,false);
            return new SendViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message currentMessage = messageList.get(position);
        // ------------------Change currentUserName to the username logged into Firebase-----------------
        Log.d("currentMessageUserName",currentMessage.getUsername());

        if (currentMessage.getUsername().equals(FirebaseDB.currentUser.getUsername())){
//            Log.d("currentMessageUserName",currentMessage.getUsername());
            return ITEM_SENT;
        }
        else{
            return ITEM_RECEIVE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getClass() == SendViewHolder.class){
            SendViewHolder viewHolder = (SendViewHolder) holder;
            Message currentMessage = messageList.get(position);
            viewHolder.sent_datetime.setText(currentMessage.getDatetime());
            viewHolder.sent_username.setText(currentMessage.getUsername());
            viewHolder.sent_sticker.setImageDrawable(AppCompatResources.getDrawable(context,currentMessage.getSticker()));
        }
        else{
            ReceiveViewHolder viewHolder = (ReceiveViewHolder) holder;
            Message currentMessage = messageList.get(position);
            viewHolder.receive_datetime.setText(currentMessage.getDatetime());
            viewHolder.receive_username.setText(currentMessage.getUsername());
            viewHolder.receive_sticker.setImageDrawable(AppCompatResources.getDrawable(context,currentMessage.getSticker()));
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class SendViewHolder extends RecyclerView.ViewHolder {
        TextView sent_username, sent_datetime;
        GifImageView sent_sticker;

        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            sent_username = itemView.findViewById(R.id.sent_username);
            sent_datetime = itemView.findViewById(R.id.sent_datetime);
            sent_sticker = itemView.findViewById(R.id.sent_sticker);
        }
    }
    class ReceiveViewHolder extends RecyclerView.ViewHolder {
        TextView receive_username, receive_datetime;
        GifImageView receive_sticker;

        public ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            receive_username = itemView.findViewById(R.id.receive_username);
            receive_datetime = itemView.findViewById(R.id.receive_datetime);
            receive_sticker = itemView.findViewById(R.id.receive_sticker);
        }
    }
}