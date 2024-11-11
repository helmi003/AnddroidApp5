package com.example.movieapp.adaptater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movieapp.R;
import com.example.movieapp.Seasons;
import com.example.movieapp.Support;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Message;
import com.example.movieapp.entities.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class ChatFeed extends RecyclerView.Adapter<ChatFeed.UserViewHolder> {

    private List<User> users;
    private Context context;

    public ChatFeed(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ChatFeed.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_feed, parent, false);
        return new ChatFeed.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatFeed.UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView userNameTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.username);
        }

        public void bind(User user) {
            userNameTextView.setText(user.username);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, Support.class);
                intent.putExtra("userID", user.getId());
                context.startActivity(intent);
            });
        }
    }
}
