package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private Button button_login_singup;
    private TextInputEditText edit_login_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_login_singup = findViewById(R.id.button_login_singup);
        edit_login_username = findViewById(R.id.edit_login_username);

        button_login_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement Firebase login/signup logic
                Intent intent = new Intent(LoginActivity.this, ChatHistory.class);
                intent.putExtra("loginUserName", edit_login_username.getText().toString());
                finish();
                startActivity(intent);
            }
        });
    }
}