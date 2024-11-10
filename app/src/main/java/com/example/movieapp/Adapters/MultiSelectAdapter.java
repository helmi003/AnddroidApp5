package com.example.movieapp.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultiSelectAdapter<T> extends RecyclerView.Adapter<MultiSelectAdapter<T>.ViewHolder> {

    private final List<T> items;
    private final Set<T> selectedItems = new HashSet<>();
    private final ItemCallback<T> itemCallback;

    public MultiSelectAdapter(List<T> items, ItemCallback<T> itemCallback) {
        this.items = items;
        this.itemCallback = itemCallback;
        Log.d("MultiSelectAdapter", "Adapter initialized with items: " + items);
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multi_select, parent, false); // custom layout
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        T item = items.get(position);
        holder.textView.setText(itemCallback.getDisplayName(item));
        holder.checkBox.setChecked(selectedItems.contains(item));
        Log.d("MultiSelectAdapter", "Binding item: " + item + " isSelected: " + selectedItems.contains(item));

        holder.itemView.setOnClickListener(v -> {
            if (selectedItems.contains(item)) {
                selectedItems.remove(item);
            } else {
                selectedItems.add(item);
            }
            holder.checkBox.setChecked(selectedItems.contains(item)); // Update checkbox state
            Log.d("MultiSelectAdapter", "Selected Items: " + selectedItems);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Set<T> getSelectedItems() {
        return selectedItems; // Provide a method to get selected items
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1); // Ensure your custom layout has this ID for TextView
            checkBox = itemView.findViewById(R.id.checkbox); // Ensure your custom layout has this ID for CheckBox
        }
    }

    public interface ItemCallback<T> {
        String getDisplayName(T item);
    }
}
