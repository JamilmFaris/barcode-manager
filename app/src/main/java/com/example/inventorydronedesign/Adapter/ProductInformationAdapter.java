package com.example.inventorydronedesign.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorydronedesign.Activities.ItemsActivity;
import com.example.inventorydronedesign.Model.Bookmark;
import com.example.inventorydronedesign.R;
import com.example.inventorydronedesign.ViewHolder.ProductInformationBarcodeViewHolder;
import com.example.inventorydronedesign.ViewHolder.ProductInformationCategoryViewHolder;
import com.example.inventorydronedesign.ViewHolder.ProductInformationDescriptionViewHolder;
import com.example.inventorydronedesign.ViewHolder.ProductInformationLocationViewHolder;
import com.example.inventorydronedesign.ViewHolder.ProductInformationNameViewHolder;
import com.example.inventorydronedesign.ViewHolder.ProductInformationNotesViewHolder;
import com.example.inventorydronedesign.ViewHolder.ProductInformationQuantityViewHolder;
import com.example.inventorydronedesign.ViewHolder.ProductInformationTextViewHolder;

import java.util.ArrayList;
import java.util.Set;

public class ProductInformationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<Pair<Bookmark, String>> data = new ArrayList<>();

    private Integer informationText = 0;
    private Integer informationBarcode = 1;
    private Integer informationName = 2;
    private Integer informationDescription = 3;
    private Integer informationQuantity = 4;
    private Integer informationCategory = 5;
    private Integer informationNotes = 6;
    private Integer informationLocation = 7;
    private Integer informationSheet = 8;


    private ArrayAdapter<String> categoryAdapter ;
    private ArrayAdapter<String> sheetAdapter ;
    Intent printIntent;

    public ProductInformationAdapter(Context myContext, ArrayList< Pair< Bookmark, String> >  data1,
                                     Set<String> categories, Intent printIntent){
        this.context = myContext;
        this.data.addAll(data1);
        ArrayList<String> curCategories = new ArrayList<>();
        curCategories.addAll(categories);
        categoryAdapter = new ArrayAdapter<>(myContext
                , android.R.layout.simple_spinner_item, curCategories);

        ArrayList<String> sheets = new ArrayList<>(ItemsActivity.SHEETS);
        sheetAdapter = new ArrayAdapter<>(myContext
                , android.R.layout.simple_spinner_item, sheets);
        this.printIntent = printIntent;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        if(viewType == informationText){
            view = LayoutInflater.from(context).inflate(R.layout.text_holder, parent, false);
            return new ProductInformationTextViewHolder(view);
        }
        else if(viewType == informationBarcode){
            view = LayoutInflater.from(context).inflate(R.layout.barcode_holder, parent, false);
            return new ProductInformationBarcodeViewHolder(view);
        }
        else if(viewType == informationName){
            view = LayoutInflater.from(context).inflate(R.layout.name_holder, parent, false);
            return new ProductInformationNameViewHolder(view);
        }
        else if(viewType == informationDescription){
            view = LayoutInflater.from(context).inflate(R.layout.description_holder, parent, false);
            return new ProductInformationDescriptionViewHolder(view);
        }
        else if(viewType == informationQuantity){
            view = LayoutInflater.from(context).inflate(R.layout.quantity_holder, parent, false);
            return new ProductInformationQuantityViewHolder(view);
        }
        else if(viewType == informationCategory){
            view = LayoutInflater.from(context).inflate(R.layout.category_holder, parent, false);
            return new ProductInformationCategoryViewHolder(view);
        }
        else if(viewType == informationNotes){
            view = LayoutInflater.from(context).inflate(R.layout.notes_holder, parent, false);
            return new ProductInformationNotesViewHolder(view);
        }
        else if(viewType == informationLocation){
            view = LayoutInflater.from(context).inflate(R.layout.location_holder, parent, false);
            return new ProductInformationLocationViewHolder(view);
        }
        else {// nothing
            view = LayoutInflater.from(context).inflate(R.layout.name_holder, parent, false);
            return new ProductInformationNameViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bookmark d = data.get(position).first;
        if(d.getType().equals(informationText)){
            ((ProductInformationTextViewHolder) holder).bind(data.get(position).second);
        }
        else if(d.getType().equals(informationBarcode)){
            ((ProductInformationBarcodeViewHolder) holder).bind(context, printIntent);

        }
        else if(d.getType().equals(informationName)){
            ((ProductInformationNameViewHolder) holder).bind();
        }
        else if(d.getType().equals(informationDescription)){
            ((ProductInformationDescriptionViewHolder) holder).bind();
        }
        else if(d.getType().equals(informationQuantity)){
            ((ProductInformationQuantityViewHolder) holder).bind();
        }
        else if(d.getType().equals(informationCategory)){
            ((ProductInformationCategoryViewHolder) holder).bind(categoryAdapter);
        }
        else if(d.getType().equals(informationNotes)){
            ((ProductInformationNotesViewHolder) holder).bind();
        }
        else if(d.getType().equals(informationLocation)){
            ((ProductInformationLocationViewHolder) holder).bind();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public int getItemViewType(int position) {
        // based on your list you will return the ViewType
        return data.get(position).first.getType();
    }
}
