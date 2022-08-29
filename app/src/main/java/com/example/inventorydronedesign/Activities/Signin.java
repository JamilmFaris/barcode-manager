package com.example.inventorydronedesign.Activities;
/**
 *
 * the activity is for signing in in the local database
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorydronedesign.Database.SignupDB;
import com.example.inventorydronedesign.R;

public class Signin extends AppCompatActivity {

    EditText employeeID, email, password;
    //button to go from signin to signup activity (if you don't have an account)
    Button signinButton;
    //button to go from signin to Items activity
    Button signin_signupButton;
    SignupDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        // initialize
        employeeID = findViewById(R.id.employee_id);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signinButton = findViewById(R.id.signin);
        signin_signupButton = findViewById(R.id.signin_signup);
        // accounts database
        db = new SignupDB(this);
        //
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if one of the inputs is empty don't signin
                // else :
                if(!employeeID.getText().toString().isEmpty() &&  !email.getText().toString().isEmpty() &&
                        !password.getText().toString().isEmpty()){
                    //signin method returns true if the account exists in the database
                    // and returns false otherwise

                    // if the account exists go to the Items activity
                    if(signin()){
                        Toast.makeText(Signin.this,
                                "signed in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Signin.this, ItemsActivity.class));
                    }

                    else{
                        Toast.makeText(Signin.this,
                                "employee id or email or password is not correct", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        signin_signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to the signup activity
                startActivity(new Intent(Signin.this, Signup.class));
            }
        });

    }
    public boolean signin(){
        // if the account exists signin and return true

        if(db.profileExist(Integer.parseInt(employeeID.getText().toString())
                , email.getText().toString())){
            return true;
        }
        else{
            return false;
        }
    }
}