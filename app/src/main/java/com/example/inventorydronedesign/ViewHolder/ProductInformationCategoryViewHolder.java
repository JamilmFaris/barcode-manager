package com.example.inventorydronedesign.ViewHolder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorydronedesign.Activities.AddItemActivity;
import com.example.inventorydronedesign.R;

import java.util.ArrayList;


public class ProductInformationCategoryViewHolder extends RecyclerView.ViewHolder {
    Spinner spinner;
    EditText editText;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> emptyAdapter;
    public ProductInformationCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        editText = itemView.findViewById(R.id.category_edittext);
        spinner = itemView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AddItemActivity.category = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // check if the user input category just remove all data from the categories spinner
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                spinner.setAdapter(emptyAdapter);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // if after he input a new category the edittext
                // is still empty return the spinner's items
                if(editText.getText().toString().isEmpty()){
                    spinner.setAdapter(adapter);
                }
                else{

                    AddItemActivity.category = editText.getText().toString();
                }
            }
        });
    }
    public void bind(ArrayAdapter<String> adapter){
        spinner.setAdapter(adapter);
        this.adapter = adapter;

        this.emptyAdapter = new ArrayAdapter<>(adapter.getContext(), android.R.layout.simple_spinner_item,
                new ArrayList<String>());
    }
}
