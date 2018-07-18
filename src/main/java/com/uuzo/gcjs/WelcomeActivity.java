package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class WelcomeActivity extends Activity {
    Boolean IsDestroy=false;
    Context ThisContext;
    Activity ThisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_welcome);
        Config.SetStatusBar(this);

        IsDestroy=false;
        ThisContext=this;
        ThisActivity=this;

        Common.context=this;
        Common.SoftName=getString(R.string.app_name);

        UserInfo.Init(ThisContext);


        FunHandler.sendEmptyMessageDelayed(0,3000);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        IsDestroy=true;
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    void CheckUserLogin(){
        if (UserInfo.IsLogin()){
            startActivity(new Intent(ThisContext, MainActivity.class));
            finish();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler FunHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {
                        if (!UserInfo.IsLogin()){
                            startActivity(new Intent(ThisContext, LoginActivity.class));
                        }
                        else{
                            startActivity(new Intent(ThisContext, MainActivity.class));
                        }
                        finish();
                    } catch (Exception e) { }
                    break;
            }
        }
    };
}
