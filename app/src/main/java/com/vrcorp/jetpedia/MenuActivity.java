package com.vrcorp.jetpedia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    private CardView akses;
    private LinearLayout jetpedia, kontak, versi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        akses = findViewById(R.id.bukaAkses);
        jetpedia = findViewById(R.id.jetpedia);
        kontak = findViewById(R.id.kontak);
        versi = findViewById(R.id.versi);

        akses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klik("akses");
            }
        });
        jetpedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klik("jetpedia");
            }
        });
        kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klik("kontak");
            }
        });
        versi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                klik("versi");
            }
        });
    }
    public void klik(String aktif){
        if (aktif.equals("akses")){
            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(intent);
        }
        if (aktif.equals("jetpedia")){
            Intent intent = new Intent(MenuActivity.this, JetpediaActivity.class);
            startActivity(intent);
        }
        if (aktif.equals("kontak")){
            Intent intent = new Intent(MenuActivity.this, KontakActivity.class);
            startActivity(intent);
        }
        if (aktif.equals("versi")){
            Intent intent = new Intent(MenuActivity.this, VersiActivity.class);
            startActivity(intent);
        }
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
