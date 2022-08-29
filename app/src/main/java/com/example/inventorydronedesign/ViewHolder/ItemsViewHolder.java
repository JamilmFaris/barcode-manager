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
        name.setText(R.string.name);
        name.setText(name.getText() + " : " + item.name);

        description.setText(R.string.description);
        description.setText(description.getText() + " : " + item.description);

        category.setText(R.string.category);
        category.setText(category.getText() + " : " + item.category);

        counter.setText(R.string.quantity);
        counter.setText(counter.getText() + " : " + String.valueOf(item.quantity));
    }
}
