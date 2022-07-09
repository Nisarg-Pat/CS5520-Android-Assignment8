package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import edu.neu.harshit.gajjar.numadsp22_team24_a8.Utils.FirebaseDB;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;

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
//                FirebaseDB.registerUser("chriss@gmail.com", "123456", "Chris_Schelb", LoginActivity.this);

//                FirebaseDB.logIn("hrstgajjar3@gmail.com", "123456", LoginActivity.this);

                FirebaseDB.getInstanceFirebaseAuth().signInWithEmailAndPassword("hrstgajjar3@gmail.com", "123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            // Successfully Logged in
                            Intent intent = new Intent(LoginActivity.this, ChatHistory.class);
                            intent.putExtra("loginUserName", edit_login_username.getText().toString());
                            finish();
                            startActivity(intent);
                        } else {
                            // Error
                            Toast.makeText(LoginActivity.this, "Error logging IN!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



//                Intent intent = new Intent(LoginActivity.this, ChatHistory.class);
//                intent.putExtra("loginUserName", edit_login_username.getText().toString());
//                finish();
//                startActivity(intent);
            }
        });
    }
}