package com.example.inventorydronedesign.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.inventorydronedesign.Adapter.ProductInformationAdapter;
import com.example.inventorydronedesign.Database.DroneSheetDB;
import com.example.inventorydronedesign.Model.Bookmark;
import com.example.inventorydronedesign.Model.Item;
import com.example.inventorydronedesign.R;

import java.util.ArrayList;
import java.util.Set;

public class AddItemActivity extends AppCompatActivity {

    // the barcode text might come from scanner activity (when you click scan)
    // or from the generate button in the ProductInformationBarcodeViewHolder (if you click generate barcode in it)
    public static String barcodeText = "";
    public static String barcodeFormat;
    public static String name = "";
    public static String description = "";
    public static int quantity;
    public static String category = "";
    public static String notes = "";
    public static String sheet;
    public static String location = "";
    RecyclerView recyclerView;

    Button saveButton;
    DroneSheetDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // holds the views which the user can input data from
        recyclerView = findViewById(R.id.product_information);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        saveButton = findViewById(R.id.save_button);
        // the database which holds the items information
        db = new DroneSheetDB(this);

        //bookmarks indicates which view to set in the ProductInformationAdapter

        //bookmark for the text above the view that the user will input data in {it only has a TextView}
        Bookmark informationText = new Bookmark(0);

        // barcodeView has a button which pops a popup window
        Bookmark informationBarcode = new Bookmark(1);

        // views to input data
        Bookmark informationName = new Bookmark(2);
        Bookmark informationDescription = new Bookmark(3);
        Bookmark informationQuantity = new Bookmark(4);
        Bookmark informationCategory = new Bookmark(5);
        Bookmark informationNotes = new Bookmark(6);
        Bookmark informationLocation = new Bookmark(7);


        ArrayList<Pair<Bookmark, String>>data = new ArrayList<>();

        // if you got to the item activity from the scanner activity don't add the barcode view
        // because the barcode will be generated previously
        // otherwise you must generate a new barcode
        if(!getIntent().getBooleanExtra("ScannerActivity", false)){
            data.add(new Pair<>(informationText, "Barcode"));
            data.add(new Pair<>(informationBarcode, ""));
        }


        data.add(new Pair<>(informationText, getString(R.string.name)));
        data.add(new Pair<>(informationName, ""));

        data.add(new Pair<>(informationText, getString(R.string.description)));
        data.add(new Pair<>(informationDescription, ""));

        data.add(new Pair<>(informationText, getString(R.string.quantity)));
        data.add(new Pair<>(informationQuantity, ""));

        data.add(new Pair<>(informationText, getString(R.string.category)));
        data.add(new Pair<>(informationCategory, ""));

        data.add(new Pair<>(informationText, getString(R.string.notes)));
        data.add(new Pair<>(informationNotes, ""));

        data.add(new Pair<>(informationText, getString(R.string.location)));
        data.add(new Pair<>(informationLocation, ""));


        DroneSheetDB db = new DroneSheetDB(this);
        // get the categories in the database to choose from
        Set<String> categories = db.getCategories();

        // initialize an intent to go to the print activity
        Intent printIntent = new Intent(this, PrintBarcodeActivity.class);

        ProductInformationAdapter adapter = new ProductInformationAdapter(this, data, categories
                , printIntent);
        recyclerView.setAdapter(adapter);

        //saveButton
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(barcodeText.isEmpty() ){
                    Toast.makeText(AddItemActivity.this,
                            R.string.scan_or_generate_barcode, Toast.LENGTH_SHORT).show();
                }
                else if(category.isEmpty()){
                    Toast.makeText(AddItemActivity.this,
                            R.string.input_category, Toast.LENGTH_SHORT).show();
                }
                else if(name.isEmpty() || description.isEmpty() ||
                         notes.isEmpty() || location.isEmpty()){
                    Toast.makeText(AddItemActivity.this,
                            R.string.all_data_are_required, Toast.LENGTH_SHORT).show();
                }
                else if(ItemsActivity.sheetSelected.isEmpty()){
                    // if there is no sheets to add to popup a message then go to the items activity again
                    Toast.makeText(AddItemActivity.this,
                            R.string.there_is_no_sheets_to_add, Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
                else{
                    // all data required are here you can add it now to the database
                    db.addNewItem(new Item(barcodeText, name, description, quantity, category
                            , notes, location, ItemsActivity.sheetSelected));
                    Toast.makeText(AddItemActivity.this,
                            R.string.item_added_successfully, Toast.LENGTH_SHORT).show();

                    category = "";
                    name = "";
                    barcodeText = "";
                    barcodeFormat = "";
                    quantity = 0;
                    startActivity(new Intent(AddItemActivity.this, ItemsActivity.class));
                }
            }
        });
        //saveButton

    }
}