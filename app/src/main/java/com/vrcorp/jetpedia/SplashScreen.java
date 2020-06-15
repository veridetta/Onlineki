package com.vrcorp.jetpedia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent belumLogin = new Intent(SplashScreen.this, MainActivity.class);
                //intent.putExtra(EXTRA_MESSAGE, message);
                belumLogin.putExtra("CEK_LOGIN", "tidak");
                Toast.makeText(SplashScreen.this, "Harap Login Dahulu!",
                        Toast.LENGTH_LONG).show();
                startActivity(belumLogin);
            }
        },2000);
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
