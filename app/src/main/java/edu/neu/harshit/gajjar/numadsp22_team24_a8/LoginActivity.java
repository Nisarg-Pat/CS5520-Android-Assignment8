package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.FirebaseDB;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.Util;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private Button button_login, button_signup;
    private TextInputEditText edit_login_username;

    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = auth.getCurrentUser();

        // checking for users existence: Saving the current user
        if(firebaseUser != null){
            startActivity(new Intent(LoginActivity.this, ChatHistory.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        button_login = findViewById(R.id.button_login);
        button_signup = findViewById(R.id.button_signup);
        edit_login_username = findViewById(R.id.edit_login_username);

        button_login.setOnClickListener(v -> {
            if (!Util.isNetworkConnected(this)) {
               Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
               return;
            }
            FirebaseDB.getInstanceFirebaseAuth().signInWithEmailAndPassword(
                    edit_login_username.getText().toString()+"@puddle.com",
                    "123456").addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    // Successfully Logged in
                    Intent intent = new Intent(LoginActivity.this, ChatHistory.class);
                    intent.putExtra("loginUserName", edit_login_username.getText().toString());
                    finish();
                    startActivity(intent);
                } else {
                    // Error
                    Toast.makeText(LoginActivity.this, "Error logging in! Please check the credentials.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}