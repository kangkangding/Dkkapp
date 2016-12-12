package com.example.dingkangkang.dkkapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webview;
    private TextView tv_getmore;
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar= (SeekBar) findViewById(R.id.seekBar);
        tv_getmore= (TextView) findViewById(R.id.tv_getmore);
        webview = (WebView) findViewById(R.id.webview);
        seekBar.setMax(100);
        tv_getmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopu();
            }
        });
        WebSettings setting= webview.getSettings();
        setting.setJavaScriptEnabled(true);
        webview.loadUrl("http://tt.worose.com");
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Toast.makeText(getApplicationContext(), "网络连接失败 ,请检查网络。", Toast.LENGTH_SHORT).show();
            }
        });
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                seekBar.setProgress(newProgress);
                if(newProgress>=100){
                    seekBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showPopu() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("请选择")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setSingleChoiceItems(new String[] {this.getString(R.string.values_name1),this.getString(R.string.values_name2),this.getString(R.string.values_name3)}, 0,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                switch (which){
                                    case 0:

                                    break;
                                    case 1:
                                        intent.setClass(MainActivity.this,Main2Activity.class);
                                        startActivity(intent);
                                        webview.destroy();
                                        finish();
                                        break;
                                    case 2:
                                        intent.setClass(MainActivity.this,ContextActivity.class);
                                        startActivity(intent);
                                        webview.destroy();
                                        finish();
                                        break;

                                }
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton("取消", null)
                .show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(webview.canGoBack()){
                webview.goBack();
                return  true;
            }else {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
