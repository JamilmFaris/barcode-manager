package com.example.inventorydronedesign.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.inventorydronedesign.Model.Item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DroneSheetDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "sheets";

    // below int is our database version
    public static final int DB_VERSION = 5;

    public static final String TABLE_NAME = "sheets";


    public static final String BARCODE_COL = "barcode";
    public static final String NAME_COL = "name";

    public static final String DESCRIPTION_COL = "description";
    public static final String COUNT_COL = "count";
    public static final String CATEGORY_COL = "category";
    public static final String NOTES_COL = "notes";

    public static final String LOCATION_COL = "location";
    public static final String SHEET_COL = "db";






    // creating a constructor for our database handler.
    public DroneSheetDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + BARCODE_COL + " TEXT,"
                + NAME_COL + " TEXT,"
                + DESCRIPTION_COL + " TEXT,"
                + COUNT_COL + " INTEGER, "
                + CATEGORY_COL + " TEXT,"
                + NOTES_COL + " TEXT,"
                + LOCATION_COL + " TEXT, "
                + SHEET_COL + " TEXT)";
        db.execSQL(query);
    }

    public void addNewItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(BARCODE_COL, item.barcode);
        values.put(NAME_COL, item.name);
        values.put(DESCRIPTION_COL, item.description);
        values.put(COUNT_COL, item.quantity);
        values.put(CATEGORY_COL, item.category);
        values.put(NOTES_COL, item.notes);
        values.put(LOCATION_COL, item.location);
        values.put(SHEET_COL, item.sheet);


        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // get items in a specific sheet
    public ArrayList<Item> readItems(String sheetName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor itemCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where " + SHEET_COL + " = '"+sheetName+"' "
                , null);
        ArrayList<Item> itemArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (itemCursor.moveToFirst()) {
            do {
                itemArrayList.add(new Item(itemCursor.getString(0),
                        itemCursor.getString(1),
                        itemCursor.getString(2),
                        itemCursor.getInt(3),
                        itemCursor.getString(4),
                        itemCursor.getString(5),
                        itemCursor.getString(6),
                        itemCursor.getString(7)
                ));
            } while (itemCursor.moveToNext());
            // moving our cursor to next.
        }
        itemCursor.close();
        return itemArrayList;
    }

    // get categories in the database
    public Set<String> getCategories(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor itemCursor = db.rawQuery("SELECT " + CATEGORY_COL  + " FROM " + TABLE_NAME
                , null);
        Set<String> categoryArrayList = new HashSet<>();

        // moving our cursor to first position.
        if (itemCursor.moveToFirst()) {
            do {
                categoryArrayList.add(itemCursor.getString(0));
            } while (itemCursor.moveToNext());
            // moving our cursor to next.
        }
        itemCursor.close();
        return categoryArrayList;
    }

    // get categories in the database
    public Set<String> getSheets(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor itemCursor = db.rawQuery("SELECT " + SHEET_COL  + " FROM " + TABLE_NAME
                , null);
        Set<String> sheetArrayList = new HashSet<>();

        // moving our cursor to first position.
        if (itemCursor.moveToFirst()) {
            do {
                sheetArrayList.add(itemCursor.getString(0));
            } while (itemCursor.moveToNext());
            // moving our cursor to next.
        }
        itemCursor.close();
        return sheetArrayList;
    }


    // check if a barcode exists in the database
    public boolean barcodeExisted(String barcode){
        SQLiteDatabase db = this.getReadableDatabase();
        boolean existed = false;
        Cursor itemCursor = db.rawQuery("SELECT " + BARCODE_COL  + " FROM " + TABLE_NAME
                , null);

        if (itemCursor.moveToFirst()) {
            do {
                if(itemCursor.getString(0).equals(barcode)){
                    existed = true;
                    break;
                }
            } while (itemCursor.moveToNext());


        }
        itemCursor.close();
        return existed;
    }



    // delete a specific item
    public void deleteItem(String barcode) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, BARCODE_COL + "=?", new String[]{barcode});
        db.close();
    }

    public void clear() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void clearSheet(String sheetName) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, SHEET_COL + "=?" , new String[]{sheetName});
        db.close();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
}
