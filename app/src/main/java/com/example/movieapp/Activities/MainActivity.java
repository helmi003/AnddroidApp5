package com.example.movieapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;

import com.example.movieapp.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        Button getInBtn =findViewById(R.id.gtInBtn);
        getInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Movies.class));

            }
        });

    }
}