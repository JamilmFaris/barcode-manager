package com.example.inventorydronedesign.ViewHolder;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorydronedesign.Activities.AddItemActivity;
import com.example.inventorydronedesign.R;

public class ProductInformationQuantityViewHolder extends RecyclerView.ViewHolder {
    EditText number;
    ImageButton reduce, add;
    public ProductInformationQuantityViewHolder(@NonNull View itemView) {
        super(itemView);
        number = itemView.findViewById(R.id.quantity_number);
        add = itemView.findViewById(R.id.add);
        reduce = itemView.findViewById(R.id.reduce);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberPlusOne = Integer.parseInt(number.getText().toString()) + 1;

                number.setText(String.valueOf(numberPlusOne));
                AddItemActivity.quantity = numberPlusOne;
            }
        });
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numberMinusOne = Integer.parseInt(number.getText().toString()) - 1;
                if(numberMinusOne < 0){
                    numberMinusOne = 0;
                }
                number.setText(String.valueOf(numberMinusOne));
                AddItemActivity.quantity = numberMinusOne;
            }
        });
        AddItemActivity.quantity = Integer.parseInt(number.getText().toString());
    }
    public void bind(){
    }
}
