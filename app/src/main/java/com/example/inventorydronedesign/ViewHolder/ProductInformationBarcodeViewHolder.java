package com.example.inventorydronedesign.ViewHolder;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorydronedesign.Activities.AddItemActivity;
import com.example.inventorydronedesign.Database.DroneSheetDB;
import com.example.inventorydronedesign.R;


public class ProductInformationBarcodeViewHolder extends RecyclerView.ViewHolder {
    Button button;
    public ProductInformationBarcodeViewHolder(@NonNull View itemView) {
        super(itemView);
        button = itemView.findViewById(R.id.barcode_button);
    }
    public void bind(Context context, Intent printIntent){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, button);
                popupMenu.getMenuInflater().inflate(R.menu.barcode_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.barcode_generate:
                                AddItemActivity.barcodeText = /*"barcode : " +*/ generateBarcode(new DroneSheetDB(context));
                                Toast.makeText(context,
                                        "Barcode generated", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.barcode_print :
                                if(AddItemActivity.barcodeText.isEmpty()){
                                    generateBarcode(new DroneSheetDB(context));
                                }
                                printIntent.putExtra("barcode", AddItemActivity.barcodeText);
                                context.startActivity(printIntent);
                                break;
                            case R.id.barcode_copy:
                                if(AddItemActivity.barcodeText.isEmpty()){
                                    Toast.makeText(context,
                                            R.string.scan_or_generate_barcode, Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    //copy barcode to clipboard
                                    ClipboardManager clipboard = (ClipboardManager) context
                                            .getSystemService(Context.CLIPBOARD_SERVICE);

                                    ClipData clip = ClipData.newPlainText("", AddItemActivity.barcodeText);
                                    clipboard.setPrimaryClip(clip);
                                    Toast.makeText(context,
                                            "Copied to clipboard!", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case R.id.cancel:
                                popupMenu.dismiss();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }
    private String generateBarcode(DroneSheetDB db){
        boolean existed = true;
        StringBuilder cur = new StringBuilder();
        while(existed){
            cur = new StringBuilder();
            for(int i = 0;i < 9;i++){
                int x = (int)(Math.random() * 10);
                cur.append(x);
            }
            existed = db.barcodeExisted(cur.toString());
        }
        button.setText("your barcode is : " + cur.toString());
        return cur.toString();
    }
}
