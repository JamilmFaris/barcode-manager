package com.example.inventorydronedesign.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorydronedesign.R;

public class ProductInformationTextViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    public ProductInformationTextViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.information_type);
    }
    public void bind(String s){
        textView.setText(s);
    }
}
