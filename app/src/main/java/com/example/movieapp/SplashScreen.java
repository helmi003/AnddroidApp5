package com.example.movieapp;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private boolean isLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        androidx.core.splashscreen.SplashScreen splashScreen = androidx.core.splashscreen.SplashScreen.installSplashScreen(this);

        new Handler().postDelayed(() -> {
            isLoading = false;
        }, 3000);

        splashScreen.setKeepOnScreenCondition(this::isDataStillLoading);
        splashScreen.setOnExitAnimationListener(screen -> {
            ObjectAnimator zoomX = ObjectAnimator.ofFloat(screen.getIconView(), View.SCALE_X, 1f, 0f);
            zoomX.setInterpolator(new OvershootInterpolator());
            zoomX.setDuration(500L);

            ObjectAnimator zoomY = ObjectAnimator.ofFloat(screen.getIconView(), View.SCALE_Y, 1f, 0f);
            zoomY.setInterpolator(new OvershootInterpolator());
            zoomY.setDuration(500L);
            zoomX.start();
            zoomY.start();
            zoomY.addListener(new android.animation.AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(android.animation.Animator animation) {
                    screen.remove();
                    currentUser = auth.getCurrentUser();
                    proceedToNextScreen();
                }
            });
        });
        EdgeToEdge.enable(this);
    }

    private boolean isDataStillLoading() {
        return isLoading;
    }

    private void proceedToNextScreen() {
        if (currentUser == null) {
            startActivity(new Intent(this, Login.class));
        } else {
            if(currentUser.isEmailVerified()){
                startActivity(new Intent(this, MainActivity.class));
            }else{
                startActivity(new Intent(this, Login.class));
            }
        }
        finish();
    }
}