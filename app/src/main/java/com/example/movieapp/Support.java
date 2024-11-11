package com.example.movieapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movieapp.adaptater.ChatAdapter;
import com.example.movieapp.database.ApplicationDatabase;
import com.example.movieapp.entities.Message;
import com.example.movieapp.entities.Role;
import com.example.movieapp.entities.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Support extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private ChatAdapter chatAdapter;
    private RecyclerView recyclerViewChat;
    private ImageView backArrow;
    private ImageButton send;
    private EditText editTextMessage;
    private ApplicationDatabase database;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        userID = getIntent().getStringExtra("userID");
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        database = ApplicationDatabase.getAppDatabase(this);

        // Initialize UI elements
        backArrow = findViewById(R.id.backArrow);
        send = findViewById(R.id.send);
        editTextMessage = findViewById(R.id.editTextMessage);
        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));

        // Initialize ChatAdapter with an empty list
        chatAdapter = new ChatAdapter(new ArrayList<>());
        recyclerViewChat.setAdapter(chatAdapter);

        // Set click listeners
        backArrow.setOnClickListener(v -> finish());
        send.setOnClickListener(view -> {
            String message = editTextMessage.getText().toString();
            if (message.isEmpty()) {
                Toast.makeText(Support.this, "Write something first", Toast.LENGTH_SHORT).show();
            } else {
                sendMessage(message.trim());
                editTextMessage.setText(""); // Clear the input field
            }
        });

        // Load messages for the current user's chat
        loadMessages();
    }

    private void loadMessages() {
        String userId = (userID != null && !userID.isEmpty()) ? userID : currentUser.getUid();
        db.collection("chats").document(userId).collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.w("Chat", "Listen failed.", e);
                        return;
                    }
                    List<Message> messages = new ArrayList<>();
                    for (DocumentSnapshot doc : snapshots) {
                        Message message = doc.toObject(Message.class);
                        if (message != null && message.getMessage() != null) {
                            messages.add(message);
                            Log.d("messages", message.getMessage());
                        } else {
                            Log.w("Chat", "Null message or missing text field in Firestore document.");
                        }
                    }
                    findViewById(R.id.emptyStateView).setVisibility(messages.isEmpty() ? View.VISIBLE : View.GONE);
                    chatAdapter.updateMessages(messages);
                    Log.d("messages", messages.toString());
                });
    }

    private void sendMessage(String text) {
        String userId = (userID != null && !userID.isEmpty()) ? userID : currentUser.getUid();
        User user = database.userDAO().getUserById(currentUser.getUid());
        String role = user.role == Role.ADMIN ? "admin" : "user";
        Map<String, Object> message = new HashMap<>();
        message.put("senderId", currentUser.getUid());
        message.put("message", text);
        message.put("timestamp", FieldValue.serverTimestamp());
        message.put("role", role);
        db.collection("chats").document(userId).collection("messages")
                .add(message)
                .addOnSuccessListener(documentReference -> Log.d("Chat", "Message sent"))
                .addOnFailureListener(e -> Log.w("Chat", "Error sending message", e));
    }
}
