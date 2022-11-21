package com.example.audioexamplemain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Maps extends AppCompatActivity {
    public WebView wikiweb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        wikiweb=findViewById(R.id.wikiweb);
        Intent intent=getIntent();
        //int position=intent.getIntExtra("position",0);
        String url="http://10.241.64.219/maps.php";
        wikiweb.getSettings().setJavaScriptEnabled(true);
        wikiweb.setWebViewClient(new WebViewClient());
        wikiweb.loadUrl(url);
    }
    //UPDATE database_tablenew SET latitude="51.892767",longitude="-8.492405" WHERE time="";
}