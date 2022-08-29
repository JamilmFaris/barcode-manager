package com.example.inventorydronedesign.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.inventorydronedesign.R;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class PrintBarcodeActivity extends AppCompatActivity {
    Spinner spinner;
    ImageView barcodeImage;
    Button print;
    public static String QR = "QR Code";
    public static String code128 = "Code 128";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_barcode);
        spinner = findViewById(R.id.barcode_spinner_to_print);
        barcodeImage = findViewById(R.id.barcode_image_to_print);
        print = findViewById(R.id.print);
        //

        // set the types of formats to transform the barcode text to
        ArrayList<String> formats = new ArrayList<>();
        formats.add(QR);
        formats.add(code128);
        ArrayAdapter<String > adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, formats);
        spinner.setAdapter(adapter);

        //
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // if the user selects QR
                if(adapterView.getSelectedItem().toString().equals(QR)){
                    // get the data from the scanner activity intent and transform it to qrcode
                    createQR((String)getIntent().getSerializableExtra("barcode")
                            , BarcodeFormat.QR_CODE, 400, 400);
                }
                else{
                    // get the data from the scanner activity intent and transform it to code128
                    createQR((String)getIntent().getSerializableExtra("barcode")
                            , BarcodeFormat.CODE_128, 400, 400);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPhotoPrint();
            }
        });
    }


    public void createQR(String data, BarcodeFormat format
            , int height, int width) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(data, format, width, height);
            barcodeImage.setImageBitmap(bitmap);
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "barcode error " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void doPhotoPrint() {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        barcodeImage.buildDrawingCache();
        Bitmap bitmap = barcodeImage.getDrawingCache();
        photoPrinter.printBitmap("droids.jpg - test print", bitmap);
    }
}