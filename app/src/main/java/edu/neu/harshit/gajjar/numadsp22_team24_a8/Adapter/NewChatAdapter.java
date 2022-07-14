package edu.neu.harshit.gajjar.numadsp22_team24_a8.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.harshit.gajjar.numadsp22_team24_a8.ChatRoom;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Model.User;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.R;

public class NewChatAdapter extends RecyclerView.Adapter<NewChatAdapter.UserViewHolder> {
    private Context context;
    private List<User> userList;

    public NewChatAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.user_row, parent,false);
            return new UserViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user = userList.get(position);
        holder.user_list_username.setText(user.getUsername());
        holder.itemView.setOnClickListener((View v)->{
            Intent intent = new Intent(context, ChatRoom.class);
            intent.putExtra("currentUserName", user.getUsername().toString());
            intent.putExtra("clickedUserName", user.getUsername().toString());
            context.startActivity(intent);
            ((Activity) context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView user_list_username;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            user_list_username = itemView.findViewById(R.id.user_list_username);
        }
    }
}