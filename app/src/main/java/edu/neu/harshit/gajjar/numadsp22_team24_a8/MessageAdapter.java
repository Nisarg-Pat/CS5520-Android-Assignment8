package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.FirebaseDB;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.Util;
import pl.droidsonroids.gif.GifImageView;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Message> messageList;
    private HashMap<String, Integer> stringIds;
    static int ITEM_RECEIVE = 1;
    static int ITEM_SENT = 2;
    String loginUserName = "Sean";

    public MessageAdapter(Context context, List<Message> messageList, HashMap<String, Integer> stringIds) {
        this.context = context;
        this.messageList = messageList;

        this.stringIds = new HashMap<>();
//        stringIds = Util.getStickerIds(context);
        this.stringIds = stringIds;
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
        if (currentMessage.getUsername().equals(FirebaseDB.currentUser.getUsername())){
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
            viewHolder.sent_datetime.setText(Util.convertTocurrentDateTime(currentMessage.getDatetime()));
            viewHolder.sent_username.setText(currentMessage.getUsername());
//            Log.i("stickerid ", stringIds.get(currentMessage.getSticker()).toString());
            int stickerId = stringIds.getOrDefault(currentMessage.getSticker(), R.drawable.question);
            viewHolder.sent_sticker.setImageDrawable(AppCompatResources.getDrawable(context,stickerId));
            Glide.with(context).load(AppCompatResources.getDrawable(context, stickerId)).into(viewHolder.sent_sticker);

        }
        else{
            ReceiveViewHolder viewHolder = (ReceiveViewHolder) holder;
            Message currentMessage = messageList.get(position);
            viewHolder.receive_datetime.setText(Util.convertTocurrentDateTime(currentMessage.getDatetime()));
            viewHolder.receive_username.setText(currentMessage.getUsername());
            int stickerId = stringIds.getOrDefault(currentMessage.getSticker(), R.drawable.question);
            viewHolder.receive_sticker.setImageDrawable(AppCompatResources.getDrawable(context,stickerId));
//            Glide.with(context).load(AppCompatResources.getDrawable(context,currentMessage.getSticker())).into(viewHolder.receive_sticker);

            Glide.with(context).load(AppCompatResources.getDrawable(context, stickerId)).into(viewHolder.receive_sticker);
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