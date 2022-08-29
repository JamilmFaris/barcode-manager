package com.example.inventorydronedesign.Activities;
/**
 *
 * shows the items in the database
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorydronedesign.Adapter.ItemsAdapter;
import com.example.inventorydronedesign.ClickListener.ClickListener;
import com.example.inventorydronedesign.Database.DroneSheetDB;
import com.example.inventorydronedesign.Database.SQLiteToExcel;
import com.example.inventorydronedesign.MainActivity;
import com.example.inventorydronedesign.Model.Item;
import com.example.inventorydronedesign.R;

import java.io.File;
import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {
    Spinner sheetsSelector;
    public static ArrayList<String> SHEETS = new ArrayList<>();
    public static String sheetSelected = "";
    ArrayList<Item> items = new ArrayList<>();
    RecyclerView itemsView;
    ItemsAdapter itemsAdapter;
    public static DroneSheetDB droneDatabase;
    ArrayAdapter<String> arrayAdapter;
    Button addSheetButton, clearButton, exportButton, scanButton;
    Button addItemButton;
    ClickListener listener;
    ImageButton optionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        /// initializing
        //spinner to select from sheets
        sheetsSelector = findViewById(R.id.sheets_select);
        // tool bar to hide the text from it and view just the sheetsSelector in it
        Toolbar toolbar = findViewById(R.id.toolbar);
        //recyclerView to view items in the screen
        itemsView = findViewById(R.id.items);
        addSheetButton = findViewById(R.id.add_sheet);
        clearButton = findViewById(R.id.clear_sheet_button);
        exportButton = findViewById(R.id.export_button);
        //the database controller which controls the database that holds the items data
        droneDatabase = new DroneSheetDB(this) ;
        addItemButton = findViewById(R.id.add_item);
        //holds the logout button
        optionsButton = findViewById(R.id.options);
        //to scan for a new barcode
        scanButton = findViewById(R.id.scan);

        // popup menu to logout
        PopupMenu popupMenu = new PopupMenu(ItemsActivity.this, optionsButton);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.logout) {
                    logout();
                }
                return true;
            }
        });

        //shows the logout option
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });


        // when item clicked
        listener = new ClickListener() {
            @Override
            public void click(int index) {
                // go to that specific item to export it
                Intent intent = new Intent(ItemsActivity.this, ItemToExportActivity.class);
                // pass the item to that activity
                intent.putExtra("item", items.get(index));
                startActivity(intent);
            }
        };


        itemsAdapter = new ItemsAdapter(listener);
        ///

        // itemsView which holds the items information
        itemsView.setHasFixedSize(true);
        itemsView.setLayoutManager(new GridLayoutManager(this,
                1, RecyclerView.VERTICAL, false));
        itemsView.setAdapter(itemsAdapter);


        //sheetsSelector implementation to select from sheets in the database
        arrayAdapter = new ArrayAdapter<>(ItemsActivity.this,
                android.R.layout.simple_spinner_item, SHEETS);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loadSheets();
        sheetsSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                // when a specific sheet is selected clear the items and add
                // new ones contained in that specific sheet
                sheetSelected = adapterView.getSelectedItem().toString();


                refreshDataBase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sheetsSelector.setAdapter(arrayAdapter);



        //toolbar
        // remove the text in the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //add sheet button
        addSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewSheet(view);
            }
        });

        //clear button
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSheet(view);
            }
        });

        //fill with dummy data

        //droneDatabase.clear();
        /*droneDatabase.addNewItem(new Item("warehouse", WAREHOUSE_SHEET));
        droneDatabase.addNewItem(new Item("warehouse", WAREHOUSE_SHEET));
        droneDatabase.addNewItem(new Item("drone", DRONE_SHEET));

         */


        if(sheetSelected.isEmpty()){
            Toast.makeText(this,
                    R.string.no_sheet_is_selected, Toast.LENGTH_SHORT).show();
        }
        else{
            // if there is a sheet selected get items from the database
            refreshDataBase();
        }

        // exportButton exports database to an excel file
        exportButton.setOnClickListener(new View.OnClickListener() {
            String fileName = sheetSelected + " sheet.xls";
            @Override
            public void onClick(View view) {
                String path = Environment.getExternalStorageDirectory()
                        + "/Android/data/com.example.inventorydronedesign/files/"
                        + fileName;
                new SQLiteToExcel
                        .Builder(ItemsActivity.this)
                        .setDataBase(DroneSheetDB.DB_NAME)
                .setTables(DroneSheetDB.TABLE_NAME)
                //.setOutputPath(path)
                .setOutputFileName(fileName)

                        .setSQL("select * from "
                                + DroneSheetDB.DB_NAME
                                + " WHERE " + DroneSheetDB.SHEET_COL + " LIKE '" + sheetSelected + "'" )
                        //set a password to the excel file
//                        .setEncryptKey("1234567")
//                        .setProtectKey("9876543")
                        .start(new SQLiteToExcel.ExportListener() {
                            @Override
                            public void onStart() {
                                Toast.makeText(ItemsActivity.this
                                        , R.string.started_converting_to_excel, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCompleted(String filePath) {
                                Toast.makeText(ItemsActivity.this
                                        , R.string.completed, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(ItemsActivity.this
                                        , R.string.error+ e.toString(), Toast.LENGTH_LONG).show();

                            }
                        });
                File file = new File(path);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),"application/vnd.ms-excel");
                startActivity(intent);
            }
        });

        //addItemButton
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // goes to the item activity to add specify item's information
                Intent intent = new Intent(ItemsActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });


        //scanButton scans for a new barcode
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ItemsActivity.this, ScannerActivity.class));
            }
        });

    }



    ///////////////////////////functions //////////////////////////////


    public void loadSheets(){
        SHEETS.clear();
        SHEETS.addAll(droneDatabase.getSheets());
        arrayAdapter.notifyDataSetChanged();
    }

    public void addNewSheet(View view){
        //pops up a window to let the user input new sheet's information
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.creating_new_sheet);
        View approveLayout = getLayoutInflater().inflate(R.layout.dialog_approve_new_sheet, null);
        builder.setView(approveLayout);
        builder.setPositiveButton("create sheet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText sheetNameEditText = approveLayout.findViewById(R.id.sheet_name_edittext);
                String sheetName = sheetNameEditText.getText().toString();
                // indicates whether the sheet exists in the database
                boolean redundant = false;
                for(int j = 0;j < SHEETS.size();j++){
                    if(sheetName.equals(SHEETS.get(j))){
                        redundant = true;
                        break;
                    }
                }
                if(redundant){
                    Toast.makeText(ItemsActivity.this,
                            R.string.sheet_is_redundant, Toast.LENGTH_SHORT).show();
                }
                else if(sheetName.isEmpty()){
                    Toast.makeText(ItemsActivity.this,
                            R.string.sheet_is_empty, Toast.LENGTH_SHORT).show();
                }
                else{
                    SHEETS.add(sheetName);
                    arrayAdapter.notifyDataSetChanged();
                    Toast.makeText(ItemsActivity.this,
                            R.string.sheet_is_created, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void clearSheet(View view){
        //pops up a window to let the user assert the deletion of a sheet
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View approveLayout = getLayoutInflater().inflate(R.layout.dialog_clear, null);
        builder.setView(approveLayout);

        builder.setTitle(R.string.clearing_sheet);
        builder.setMessage(R.string.what_do_you_want_to_do);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.clear, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                RadioGroup radioGroup =  approveLayout.findViewById(R.id.set_clear_type);
                int idx = radioGroup.getCheckedRadioButtonId();
                if(!sheetSelected.isEmpty()){
                    switch (idx){
                        case R.id.clear_this_sheet_only:
                            String sheetName = sheetSelected;
                            // check if the sheet is existed
                            boolean existed = false;

                            for(int i = 0;i < SHEETS.size();i++){
                                if(SHEETS.get(i).equals(sheetName)){
                                    existed = true;
                                    SHEETS.remove(i);
                                    arrayAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                            if(existed){
                                droneDatabase.clearSheet(sheetName);
                                refreshDataBase();
                                Toast.makeText(ItemsActivity.this,
                                        R.string.sheet_is_cleared, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(ItemsActivity.this,
                                        R.string.sheet_is_not_existed, Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case R.id.clear_sheets:
                            clearDatabase();
                            SHEETS.clear();
                            arrayAdapter.notifyDataSetChanged();
                            refreshDataBase();
                            Toast.makeText(ItemsActivity.this,
                                    R.string.sheets_are_cleared, Toast.LENGTH_SHORT).show();
                            break;
                    }


                    //add items for the new sheet and initialize sheetSelected

                        // check if the sheetsSelector is empty

                    items.clear();
                    if(arrayAdapter.getCount() == 0){
                        Toast.makeText(ItemsActivity.this,
                                R.string.there_are_no_more_sheets, Toast.LENGTH_SHORT).show();
                        sheetSelected = "";

                    }
                    else if(arrayAdapter.getCount() == 1){
                        try {
                            sheetSelected = arrayAdapter.getItem(0).toString();
                        }catch (IndexOutOfBoundsException e){
                            Toast.makeText(ItemsActivity.this,
                                    R.string.there_is_something_wrong_select_sheet_again, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ItemsActivity.class));
                        }
                        refreshDataBase();
                    }
                    else{
                        sheetSelected = sheetsSelector.getSelectedItem().toString();
                        refreshDataBase();
                    }

                }
                else{
                    Toast.makeText(ItemsActivity.this,
                            R.string.there_is_no_sheet_to_clear, Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void clearDatabase(){
        droneDatabase.clear();
        ItemsActivity.sheetSelected="";

    }
    public void logout(){
        startActivity(new Intent(this, MainActivity.class));
    }
    // get items from database and view it
    public void refreshDataBase(){
        items.clear();
        items.addAll(droneDatabase.readItems(sheetSelected));
        itemsAdapter.addItems(items);
        itemsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ItemsActivity.this, MainActivity.class));
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}