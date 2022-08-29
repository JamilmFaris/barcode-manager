package com.example.inventorydronedesign.ViewHolder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorydronedesign.Activities.AddItemActivity;
import com.example.inventorydronedesign.R;

public class ProductInformationLocationViewHolder extends RecyclerView.ViewHolder {
    EditText text;
    public ProductInformationLocationViewHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.information_location);

    }
    public void bind(){
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                AddItemActivity.location = text.getText().toString();
            }
        });
    }

}
