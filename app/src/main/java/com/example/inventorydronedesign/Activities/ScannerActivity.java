package com.example.inventorydronedesign.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.example.inventorydronedesign.R;
import com.google.zxing.Result;

public class ScannerActivity extends AppCompatActivity {

    CodeScannerView scannerView;
    CodeScanner codeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        scannerView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, scannerView);
        codeScanner.startPreview();
        codeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Throwable thrown) {
                Toast.makeText(ScannerActivity.this,
                        "error " + thrown
                        ,Toast.LENGTH_LONG ).show();
            }
        });
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        AddItemActivity.barcodeText = result.getText();
                        AddItemActivity.barcodeFormat = result.getBarcodeFormat().name();

                        Toast.makeText(ScannerActivity.this,
                                result.getText() + "\n" + result.getBarcodeFormat()
                                ,Toast.LENGTH_LONG ).show();

                        // go to Item activity and tell it that you came from ScannerActivity
                        Intent intent = new Intent(ScannerActivity.this, AddItemActivity.class);
                        intent.putExtra("ScannerActivity", true);
                        startActivity(intent);

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }
}