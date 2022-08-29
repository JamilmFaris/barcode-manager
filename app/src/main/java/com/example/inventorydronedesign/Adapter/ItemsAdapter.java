package com.example.inventorydronedesign.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorydronedesign.ClickListener.ClickListener;
import com.example.inventorydronedesign.Model.Item;
import com.example.inventorydronedesign.R;
import com.example.inventorydronedesign.ViewHolder.ItemsViewHolder;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsViewHolder> {
    ArrayList<Item> items = new ArrayList<>();
    // listens to each item when clicked
    ClickListener listener;
    public ItemsAdapter(ClickListener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // get the view "item_holder" to create view holder from
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holder, null);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        // method to bind contents of each item with its view
        holder.bind(items.get(position));

        // indicate the item index which is clicked
        int index = holder.getAdapterPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(ArrayList<Item> items){
        // add an array of items to the items in Items activity
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }
    public void addItem(Item item){
        // add an item to the items in Items activity
        this.items.add(item);
        notifyDataSetChanged();
    }
}
