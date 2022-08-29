package com.example.inventorydronedesign.Model;

import java.io.Serializable;

public class Item implements Serializable {
    public String barcode, name, description, category, notes, location, sheet;


    public int quantity;
    public Item(String name, String sheet){
        this.barcode = "094129";
        this.name = name;
        this.description = "description";
        this.category = "category";
        this.quantity = 0;
        this.sheet = sheet;
        this.location = "up roof";
    }

    public Item(String barcode, String name, String description
            ,int quantity, String category,  String notes, String location, String sheet) {
        this.barcode = barcode;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.category = category;
        this.notes = notes;
        this.location = location;
        this.sheet = sheet;

    }

}
