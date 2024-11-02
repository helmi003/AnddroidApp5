package com.example.movieapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MultiSelectAdapter<T> extends RecyclerView.Adapter<MultiSelectAdapter.ViewHolder> {

    private final List<T> items; // List of items of generic type T
    private final List<T> selectedItems; // List of selected items of generic type T
    private final ItemBinder<T> itemBinder; // Interface to bind items

    public MultiSelectAdapter(List<T> items, List<T> selectedItems, ItemBinder<T> itemBinder) {
        this.items = items;
        this.selectedItems = selectedItems;
        this.itemBinder = itemBinder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        T item = items.get(position);
        holder.checkedTextView.setText(itemBinder.getDisplayText(item));
        holder.checkedTextView.setChecked(selectedItems.contains(item));

        holder.checkedTextView.setOnClickListener(v -> {
            if (holder.checkedTextView.isChecked()) {
                selectedItems.remove(item);
                holder.checkedTextView.setChecked(false);
            } else {
                selectedItems.add(item);
                holder.checkedTextView.setChecked(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckedTextView checkedTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            checkedTextView = itemView.findViewById(android.R.id.text1);
        }
    }

    // Interface to define how to display the item
    public interface ItemBinder<T> {
        String getDisplayText(T item);
    }
}
