package com.example.movieapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.DAO.Feedback;
import com.example.movieapp.R;
import com.example.movieapp.database.ApplicationDatabase;

import java.util.List;


public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
    private List<Feedback> feedbackList;
    private Context context;
    private OnFeedbackClickListener listener;

    public FeedbackAdapter(List<Feedback> feedbackList, Context context, OnFeedbackClickListener listener) {
        this.feedbackList = feedbackList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feedback, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);
        holder.commentTextView.setText(feedback.getComment());
        holder.ratingBar.setRating(feedback.getRating());

        // Set click listener for the "Modifier" button
        holder.editButton.setOnClickListener(v -> {
            // Pass the feedback ID to the EditFeedbackActivity
            Intent intent = new Intent(context, EditFeedbackActivity.class);
            intent.putExtra("FEEDBACK_ID", feedback.getId()); // Pass the feedback ID
            intent.putExtra("FEEDBACK_COMMENT", feedback.getComment()); // Pass the comment
            intent.putExtra("FEEDBACK_RATING", feedback.getRating()); // Pass the rating
            context.startActivity(intent);
        });

        // Set click listener for the "Supprimer" button
        holder.deleteButton.setOnClickListener(v -> {
            // Delete the feedback from the database
            ApplicationDatabase db = ApplicationDatabase.getAppDatabase(context);
            new Thread(() -> {
                db.feedbackDao().deleteFeedback(feedback);  // Delete feedback
                ((Activity) context).runOnUiThread(() -> {
                    feedbackList.remove(position); // Remove feedback from the list
                    notifyItemRemoved(position); // Notify the adapter
                });
            }).start();
        });
    }


    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public void updateFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
        notifyDataSetChanged();
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView commentTextView;
        RatingBar ratingBar;
        Button editButton, deleteButton;

        public FeedbackViewHolder(View itemView) {
            super(itemView);
            commentTextView = itemView.findViewById(R.id.comment);
            ratingBar = itemView.findViewById(R.id.rating);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public interface OnFeedbackClickListener {
        void onEditFeedback(int feedbackId);
    }
}
