package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


public class WebActivity extends Activity {

	Boolean IsDestroy=false;
	Context ThisContext;
	TextView app_title_center,app_title_right2;
	ImageView app_title_left,app_title_right;
	WebView _WebView;
    String BarColor="";
    long exitTime=0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_web);

	    if (getIntent().hasExtra("BarColor")) BarColor=getIntent().getStringExtra("BarColor");

        Config.SetStatusBar(this,BarColor);

		IsDestroy=false;
	    ThisContext=this;
	    
	    app_title_center = (TextView)findViewById(R.id.app_title_center);
        app_title_left = (ImageView)findViewById(R.id.app_title_left);
        app_title_right = (ImageView)findViewById(R.id.app_title_right);
        app_title_right2 = (TextView)findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.GONE);
        app_title_center.setText(getIntent().getStringExtra("Title"));
        app_title_left.setImageResource(R.drawable.back);
        app_title_left.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
                finish();
			}
		});
        if (!BarColor.equals("")) {
            ((LinearLayout) ((RelativeLayout) app_title_center.getParent()).getParent()).setBackgroundColor(Color.parseColor(BarColor));
        }

        _WebView = (WebView) findViewById(R.id.widget_0);
        
        
        FunHandler.sendEmptyMessageDelayed(0, 100);
	}
	
	@Override
	protected void onDestroy() {
		IsDestroy=true;
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) < 500 || !_WebView.canGoBack()){
                finish();
            }
			else
			{
                exitTime = System.currentTimeMillis();
				_WebView.goBack();
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	
	@SuppressLint("HandlerLeak")
	Handler FunHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if (IsDestroy) return;
			switch (msg.what)
			{
				case 0:
					try
					{
                        _WebView.getSettings().setAllowFileAccess(true);
                        _WebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
                        _WebView.getSettings().setSupportZoom(false);
                        _WebView.getSettings().setBuiltInZoomControls(false);
                        _WebView.getSettings().setUseWideViewPort(true);
                        _WebView.getSettings().setSupportMultipleWindows(false);
                        _WebView.getSettings().setAppCacheEnabled(true);
                        _WebView.getSettings().setDomStorageEnabled(true);
                        _WebView.getSettings().setJavaScriptEnabled(true);
                        _WebView.getSettings().setDatabaseEnabled(true);
                        _WebView.getSettings().setGeolocationEnabled(true);
                        _WebView.getSettings().setGeolocationDatabasePath(getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath());
                        _WebView.setInitialScale(1);
                        _WebView.setWebChromeClient(new WebChromeClient(){
                            @Override
                            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissionsCallback callback) {
                                callback.invoke(origin, true, true);
                                super.onGeolocationPermissionsShowPrompt(origin, callback);
                            }
                        });
                        _WebView.setWebViewClient(new WebViewClient() {
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                if (url.contains("platformapi/startapp") || (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && url.contains("platformapi") && url.contains("startapp")) || url.startsWith("weixin://wap/pay?")){
                                    try {
                                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                        intent.setComponent(null);
                                        startActivity(intent);
                                    } catch (Exception e) { }
                                    return true;
                                }
                                if (!url.startsWith("http") && !url.startsWith("https")) return true;
                                if (!getIntent().getBooleanExtra("ClickLinkNewWeb", false)) {
                                    _WebView.loadUrl(url);
                                }
                                else {
                                    Intent _Intent = new Intent(ThisContext, WebActivity.class);
                                    _Intent.putExtra("BarColor", BarColor);
                                    _Intent.putExtra("Title", getIntent().getStringExtra("Title"));
                                    _Intent.putExtra("Url", url);
                                    startActivity(_Intent);
                                }
                                return true;
                            }
                        });
						_WebView.loadUrl(getIntent().getStringExtra("Url"));
                        CookieSyncManager.createInstance(ThisContext);
                        CookieSyncManager.getInstance().sync();
					}
					catch(Exception ex) { }
					break;
			}
		}
	};
}
