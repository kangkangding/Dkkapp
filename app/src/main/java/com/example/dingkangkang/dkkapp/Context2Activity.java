package com.example.dingkangkang.dkkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class Context2Activity extends AppCompatActivity {

    private WebView wb_context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context2);
        wb_context= (WebView) findViewById(R.id.wb_context);
        Intent intent = getIntent();
        String url=intent.getStringExtra("url");
        wb_context.setWebChromeClient(new WebChromeClient());
        WebSettings settings=wb_context.getSettings();
        settings.setJavaScriptEnabled(true);
        wb_context.loadUrl(url);
        wb_context.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(wb_context.canGoBack()){
                wb_context.goBack();
                return  true;
            }else {
                Intent intent = new Intent();
                intent.setClass(Context2Activity.this,ContextActivity.class);
                startActivity(intent);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        wb_context.destroy();
        super.onDestroy();
    }
}
