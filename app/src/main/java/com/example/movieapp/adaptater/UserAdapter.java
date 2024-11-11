package com.example.movieapp.adaptater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;
    private Context context;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView userNameTextView;
        private TextView userEmailTextView;
        private AppCompatButton blockButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            userEmailTextView = itemView.findViewById(R.id.userEmailTextView);
            blockButton = itemView.findViewById(R.id.blockButton);
        }

        public void bind(User user) {
            userNameTextView.setText(user.username);
            userEmailTextView.setText(user.email);
            blockButton.setText(user.isBlocked ? "Unblock" : "Block");

            // Handle block/unblock click
            blockButton.setOnClickListener(v -> {
                boolean newBlockStatus = !user.isBlocked;
                ApplicationDatabase.getAppDatabase(context).userDAO().toggleBlockUser(user.id, newBlockStatus);
                user.isBlocked = newBlockStatus;
                blockButton.setText(newBlockStatus ? "Unblock" : "Block");
            });
        }
    }
}
