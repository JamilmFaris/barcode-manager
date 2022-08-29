package com.example.inventorydronedesign.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorydronedesign.ClickListener.ClickListener;
import com.example.inventorydronedesign.Model.Item;
import com.example.inventorydronedesign.R;

public class ItemsViewHolder extends RecyclerView.ViewHolder {
    TextView barcode, name, description, category, counter;

    public ItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        barcode = itemView.findViewById(R.id.item_barcode);
        name = itemView.findViewById(R.id.item_name);
        description = itemView.findViewById(R.id.item_description);
        category = itemView.findViewById(R.id.item_category);
        counter = itemView.findViewById(R.id.item_count);
    }
    public void bind(Item item){
        barcode.setText(  item.barcode);
        name.setText("name : " + item.name);
        description.setText("description : " + item.description);
        category.setText("category : " + item.category);
        counter.setText("quantity : " + String.valueOf(item.quantity));
    }
}
