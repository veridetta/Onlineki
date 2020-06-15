package com.vrcorp.jetpedia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private TextView txtLoading;
    private ProgressBar pg;
    private RelativeLayout pgLayout;
    int status =0;
    Handler handler = new Handler();
    boolean doubleBackToExitPressedOnce = false;
    private String halaman="https://jetpedia.id/auth/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        webView = findViewById(R.id.webview);
        txtLoading = findViewById(R.id.txt_loading);
        pg = findViewById(R.id.loading);
        pgLayout =findViewById(R.id.loading_content);
        settings();
        load_web();
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void settings(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setEnableSmoothTransition(true);
        webSettings.setDomStorageEnabled(true);
    }
    private void load_web(){
        if(Build.VERSION.SDK_INT>=19){
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }else{
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pg.setProgress(newProgress);
                txtLoading.setText(newProgress+" %");
                if(newProgress==100){
                    pgLayout.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                pgLayout.setVisibility(View.GONE);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pgLayout.setVisibility(View.GONE);
            }
        });
        webView.loadUrl(halaman);
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            System.exit(0);
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
