package com.example.inventorydronedesign;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.inventorydronedesign.Activities.ScannerActivity;
import com.example.inventorydronedesign.Activities.Signin;
import com.example.inventorydronedesign.Activities.Signup;

public class MainActivity extends AppCompatActivity {
    Button signin, signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize
        signin = findViewById(R.id.go_signin);
        signup = findViewById(R.id.go_signup);


        ///remove

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when the button is clicked this is the functionality

                //go to Signin activity
                startActivity(new Intent(MainActivity.this, Signin.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //when the button is clicked this is the functionality

                //go to Signup activity
                startActivity(new Intent(MainActivity.this, Signup.class));
            }
        });
    }

}