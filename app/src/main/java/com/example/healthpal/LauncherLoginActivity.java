package com.example.healthpal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LauncherLoginActivity extends AppCompatActivity {

    EditText edUsername, edPassword;
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_login);

        edUsername = findViewById(R.id.editTextLoginUsername);
        edPassword = findViewById(R.id.editTextLoginPassword);
        btn = findViewById(R.id.button);
        tv = findViewById(R.id.textView3);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 startActivity(new Intent(LauncherLoginActivity.this, MainActivity.class)); //comment/uncomment later
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                Database db = new Database(getApplicationContext(), "healthpal", null,1);
                if(username.length() == 0 || password.length() == 0)
                {
                    Toast.makeText(getApplicationContext(),"Please fill all details!", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.login(username, password) == 1) {
                        Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        //this is where we add username into SharedPreferences!
                        editor.putString("username", username);
                        // to save our data with key and value
                        editor.apply();
                        startActivity(new Intent(LauncherLoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        edUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Change hint text and its color
                    edUsername.setHint("");
                } else {
                    // Reset hint text and its color
                    edUsername.setHint("Username");  // Set it back to your original hint text
                    edUsername.setHintTextColor(getColor(R.color.white));
                }
            }
        });

        edPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Change hint text and its color
                    edUsername.setHint("");
                } else {
                    // Reset hint text and its color
                    edUsername.setHint("Username");  // Set it back to your original hint text
                    edUsername.setHintTextColor(getColor(R.color.white));
                }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LauncherLoginActivity.this, RegisterActivity.class));
            }
        });
    }

}