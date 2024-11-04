package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView navView;
    NavigationView navigationView;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        auth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.menuIcon);
        navigationView = findViewById(R.id.nav_view);
        navView.setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_profile) {
                Intent intent = new Intent(MainActivity.this,Profil.class);
                startActivity(intent);
            } else if (id == R.id.nav_users) {
                Intent intent = new Intent(MainActivity.this, Users.class);
                startActivity(intent);
            } else if (id == R.id.nav_reservations) {
                Intent intent = new Intent(MainActivity.this, Reservations.class);
                startActivity(intent);
            } else if (id == R.id.nav_streaming) {
                Intent intent = new Intent(MainActivity.this,Streaming.class);
                startActivity(intent);
            } else if (id == R.id.nav_series) {
                Intent intent = new Intent(MainActivity.this, Series.class);
                startActivity(intent);
            } else if (id == R.id.nav_reservation) {
                Intent intent = new Intent(MainActivity.this, SeatReservation.class);
                startActivity(intent);
            } else if (id == R.id.nav_my_reservations) {
                Intent intent = new Intent(MainActivity.this, MyReservations.class);
                startActivity(intent);
            } else if (id == R.id.nav_add_movie) {
                Intent intent = new Intent(MainActivity.this, AddMovieByHelmi.class);
                startActivity(intent);
            } else if (id == R.id.nav_logout) {
                auth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

    }
}
