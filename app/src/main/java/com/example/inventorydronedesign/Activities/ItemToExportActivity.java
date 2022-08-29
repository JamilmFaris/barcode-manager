package com.example.inventorydronedesign.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inventorydronedesign.Database.DroneSheetDB;
import com.example.inventorydronedesign.Database.SQLiteToExcel;
import com.example.inventorydronedesign.Model.Item;
import com.example.inventorydronedesign.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.BarcodeView;

import org.apache.commons.collections4.BidiMap;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ItemToExportActivity extends AppCompatActivity {
    public TextView barcode, name, description, quantity, category, notes, location, sheet;
    Button exportButton;
    ImageView barcodeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_to_export);
        barcode = findViewById(R.id.item_to_export_barcode);
        name = findViewById(R.id.item_to_export_name);
        description = findViewById(R.id.item_to_export_description);
        category = findViewById(R.id.item_to_export_category);
        quantity = findViewById(R.id.item_to_export_count);
        notes = findViewById(R.id.item_to_export_notes);
        location = findViewById(R.id.item_to_export_location);
        sheet = findViewById(R.id.item_to_export_sheet);
        exportButton = findViewById(R.id.item_to_export_button);
        barcodeImage = findViewById(R.id.barcode_image);

        //
        Item item = (Item) getIntent().getSerializableExtra("item");
        String barcodeText = item.barcode;
        String nameText = item.name;
        String descriptionText = item.name;
        String categoryText = item.category;
        int quantityText = item.quantity;
        String notesText = item.notes;
        String locationText = item.location;
        String sheetText = item.sheet;
        barcode.setText(barcodeText);
        name.setText("name : " + nameText);
        description.setText("description : " + descriptionText);
        category.setText("category : " + categoryText);
        quantity.setText("quantity : " + String.valueOf(quantityText));
        notes.setText("notes : " + notesText);
        location.setText("location : " + locationText);
        sheet.setText("sheet : " + sheetText);


        //create image
        createQR(barcodeText, BarcodeFormat.QR_CODE, 400, 400);
        //create image


        exportButton.setOnClickListener(new View.OnClickListener() {
            String fileName = name.getText().toString() + ".xls" ;
            @Override
            public void onClick(View view) {
                // put the excel file in this path
                String path = Environment.getExternalStorageDirectory()
                        + "/Android/data/com.example.inventorydronedesign/files/"
                        + fileName;
                new SQLiteToExcel
                        .Builder(ItemToExportActivity.this)
                        .setDataBase(DroneSheetDB.DB_NAME)
                        .setTables(DroneSheetDB.TABLE_NAME)
                        //.setOutputPath(path)
                        .setOutputFileName(fileName)
                        .setSQL("select * from "
                                + DroneSheetDB.DB_NAME
                                + " WHERE " + DroneSheetDB.BARCODE_COL
                                + " LIKE '" + item.barcode + "'"  )
                        //set a password to the excel file
//                        .setEncryptKey("1234567")
//                        .setProtectKey("9876543")
                        .start(new SQLiteToExcel.ExportListener() {
                            @Override
                            public void onStart() {
                                Toast.makeText(ItemToExportActivity.this
                                        , "started converting to excel file", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCompleted(String filePath) {
                                Toast.makeText(ItemToExportActivity.this
                                        , "completed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(ItemToExportActivity.this
                                        , "error " + e.toString(), Toast.LENGTH_LONG).show();

                            }
                        });
                File file = new File(path);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),"application/vnd.ms-excel");
                startActivity(intent);
            }
        });
    }
    public void createQR(String data, BarcodeFormat format, int height, int width) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            // barcode to bitmap
            Bitmap bitmap = barcodeEncoder.encodeBitmap(data, format, width, height);
            // bitmap to image then view it in the screen
            barcodeImage.setImageBitmap(bitmap);
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "barcode error " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}