package com.vrcorp.jetpedia;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
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

import im.delight.android.webview.AdvancedWebView;

public class MainActivity extends AppCompatActivity implements AdvancedWebView.Listener {
    private AdvancedWebView webView;
    private TextView txtLoading;
    private ProgressBar pg;
    private RelativeLayout pgLayout;
    int status =0;
    Handler handler = new Handler();
    boolean doubleBackToExitPressedOnce = false;
    private String halaman="https://jetpedia.id/auth/login", halaman_index="https://jetpedia.id/",
            halaman_dashboard="https://jetpedia.id/dashboard";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        webView = (AdvancedWebView) findViewById(R.id.webview);
        txtLoading = findViewById(R.id.txt_loading);
        pg = findViewById(R.id.loading);
        pgLayout =findViewById(R.id.loading_content);
        webView.setListener(this, (AdvancedWebView.Listener) this);
        webView.loadUrl(halaman);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                //pgLayout.setVisibility(View.GONE);
                pg.setProgress(0);
                for(int i=0;i<99;i++){
                    Handler handler=new Handler();
                    final int finalI = i;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pg.setProgress(finalI);
                            txtLoading.setText(finalI +" %");
                        }
                    },500 * i);
                }
                return true;
            }
            @Override
            public void onLoadResource(WebView view, String url) {
                pg.setProgress(0);
                for(int i=0;i<99;i++){
                    Handler handler=new Handler();
                    final int finalI = i;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pg.setProgress(finalI);
                            txtLoading.setText(finalI +" %");
                        }
                    },500 * i);
                }
                super.onLoadResource(view, url);
                Log.d("DUA", "onLoadResource: "+url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.cancel();
            }
        });
    }
    @SuppressLint("NewApi")
    @Override
    protected void onResume(){
     super.onResume();
     webView.onResume();
    }
    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        webView.onPause();
        super.onPause();
    }
    @SuppressLint("NewApi")
    @Override
    protected void onDestroy() {
        webView.onDestroy();
        super.onDestroy();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        webView.onActivityResult(requestCode,resultCode,intent);
    }
    @Override
    public void onBackPressed() {
        if(!webView.onBackPressed()){
            return;
        }
        super.onBackPressed();
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
    @Override
    public void onPageStarted(String url, Bitmap favicon){
        pgLayout.setVisibility(View.VISIBLE);
        pg.setProgress(0);
        for(int i=0;i<99;i++){
            Handler handler=new Handler();
                        final int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pg.setProgress(finalI);
                    txtLoading.setText(finalI +" %");
                }
            },500 * i);
        }
    }
    @Override
    public void onPageFinished(String url){
        pgLayout.setVisibility(View.GONE);
    }
    @Override
    public void onPageError(int errorCode, String description, String failingUrl){
        Log.d("Error", "onPageError: "+description+errorCode+failingUrl);
    }
    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimType, long contentLength, String contentDisposition, String userAgent){}
    @Override
    public void onExternalPageRequest(String url){
        pgLayout.setVisibility(View.VISIBLE);
        pg.setProgress(0);
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
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
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

            @Override
            public void onLoadResource(WebView view, String url) {
                pg.setProgress(1);
                txtLoading.setText(1+" %");
                super.onLoadResource(view, url);
                Log.d("DUA", "onLoadResource: "+url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.cancel();
            }
        });
        webView.loadUrl(halaman);
    }
}
