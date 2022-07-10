package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.FirebaseDB;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class SignupActivity extends AppCompatActivity {
    private Button button_registration;
    private TextInputEditText edit_signup_email, edit_signup_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_signup);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        button_registration = findViewById(R.id.button_registration);
        edit_signup_email = findViewById(R.id.edit_signup_email);
        edit_signup_username = findViewById(R.id.edit_signup_username);

        button_registration.setOnClickListener(v -> {
            // Implement Firebase signup logic
            FirebaseDB.registerUser(edit_signup_username.getText().toString()+
                    "@puddle.com", "123456",edit_signup_username.getText().toString(),
                    SignupActivity.this);
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}