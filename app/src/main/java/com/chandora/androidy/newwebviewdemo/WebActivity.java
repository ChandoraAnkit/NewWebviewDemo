package com.chandora.androidy.newwebviewdemo;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class WebActivity extends AppCompatActivity {

    WebView mWebview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebview = findViewById(R.id.webview);

        mWebview.getSettings().setJavaScriptEnabled(true);

        mWebview.addJavascriptInterface(new MyWebAppInterface(), "AndroidInterface");
        mWebview.setWebChromeClient(new MyWebChromeClient());
        mWebview.loadUrl("file:///android_asset/sample.html");
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    mWebview.evaluateJavascript("(function(){" +
//
//                            " var obj = AndroidInterface.showToast();" +
//                            " p = JSON.parse(obj);" +
//
//                            "})()", null);
//                }


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                    mWebview.evaluateJavascript("(function()" +
                            "{ KiteConnect.ready(function() {"+
                            "var kite = new KiteConnect('o3ju0z4izdbsbonp');" +
                            "var obj = AndroidInterface.showToast();"+
                            "var p = JSON.parse(obj);"+
                            "kite.add(p);"+

                            "console.log(p.exchange);"+
                            " kite.finished(function(status, request_token) {" +
                            " alert('Finished. Status is ' + status);" +
                            "});" +
                            "kite.renderButton('#default-button');"+
                            "kite.link('#custom-button');" +
                            "});}" +

                            ")()",null);
                }
            }
        });


    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.i("MESSAGE_TYPE", "onConsoleMessage: " + consoleMessage.message() + consoleMessage.lineNumber());
            return true;
        }

    }


    public class MyWebAppInterface {
        @JavascriptInterface
        public String showToast() {


            JSONObject jo = new JSONObject();
            try {

                jo.put("exchange", "NSE");
                jo.put("tradingsymbol", "20MICRONS");
                jo.put("quantity", 1);
                jo.put("transaction_type", "BUY");
                jo.put("order_type", "MARKET");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Toast.makeText(WebActivity.this, "Hello!", Toast.LENGTH_SHORT).show();


//            WebActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                    JSONObject jo = new JSONObject();
//                    try {
//                        jo.put("Ankit","Chandora");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });

            Log.i("JSON_DEMO", "showToast: " + jo.toString());
//
            return jo.toString();
        }
    }


}
