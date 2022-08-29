package com.example.inventorydronedesign.Activities;
/**
 *
 * the activity is for creating a new account in the local database
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

public class Signup extends AppCompatActivity {

    EditText employeeID, name, email, password;
    Button signupButton;
    SignupDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        /// initializing
        employeeID = findViewById(R.id.employee_id_signup);
        name = findViewById(R.id.name_signup);
        email = findViewById(R.id.email_signup);
        password = findViewById(R.id.password_signup);
        signupButton = findViewById(R.id.signup);

        //the database which holds the accounts' data
        db = new SignupDB(this);
        ///

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }


    private void signup(){

        /// if one of the inputs is empty don't signup
        if(employeeID.getText().toString().isEmpty() || name.getText().toString().isEmpty() ||
                email.getText().toString().isEmpty() ||password.getText().toString().isEmpty() ){
            Toast.makeText(Signup.this,
                    "Enter missing data", Toast.LENGTH_SHORT).show();
        }
        // if the password's length is less than 9 letters don't signup
        else if(password.getText().toString().length() < 9){
            Toast.makeText(Signup.this,
                    "password is under 9 letters", Toast.LENGTH_SHORT).show();
        }
        else{
            // if the profile exists previously in the database don't signup
            if(db.profileExist(Integer.parseInt(employeeID.getText().toString())
                    , email.getText().toString())){
                Toast.makeText(Signup.this,
                        R.string.profile_is_existed, Toast.LENGTH_SHORT).show();
            }
            //else signup
            else{
                db.addNewAccount(Integer.parseInt(employeeID.getText().toString()), name.getText().toString()
                        , email.getText().toString(), password.getText().toString());
                Toast.makeText(Signup.this,
                        "Registered successfully", Toast.LENGTH_SHORT).show();
                // go to the items activity
                startActivity(new Intent(Signup.this, ItemsActivity.class));
            }

        }
    }
}