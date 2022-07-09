package edu.neu.harshit.gajjar.numadsp22_team24_a8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, StickerActivity.class);
        startActivity(intent);

//        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
//        db.child("sean1").child("123").setValue("Hello world");
//        db.child("sean2").child("123").setValue("Hello world");
    }
}