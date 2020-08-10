package com.vrcorp.onlineki;

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
                PrefManager prefManager = new PrefManager(getApplicationContext());
                if (!prefManager.isFirstTimeLaunch()) {

                }else{
                    prefManager.setFirstTimeLaunch(true);
                }

                Intent belumLogin = new Intent(SplashScreen.this, WelcomeActivity.class);
                //intent.putExtra(EXTRA_MESSAGE, message);
                Toast.makeText(SplashScreen.this, "Welcome!",
                        Toast.LENGTH_LONG).show();
                startActivity(belumLogin);
            }
        },3000);
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
