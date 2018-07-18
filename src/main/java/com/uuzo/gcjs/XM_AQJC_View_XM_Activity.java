package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.Date;

public class XM_AQJC_View_XM_Activity extends Activity {

	Boolean IsDestroy=false;
	Context ThisContext;
	Activity ThisActivity;
	
	TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;

    LinearLayout widget_1,widget_2,widget_3,widget_4,widget_5,widget_6,widget_7,widget_8,widget_9,widget_10,widget_11,widget_12;
	ClsClass.Cls _Cls;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_xm_aqjc_view_xm);
        Config.SetStatusBar(this);
	    
	    IsDestroy=false;
	    ThisContext=this;
	    ThisActivity=this;

	    if (Config.NowCls==null)
		{
	    	finish();
			return;
		}
        _Cls= Config.NowCls;
	    
	    app_title_center = (TextView) findViewById(R.id.app_title_center);
        app_title_left = (ImageView) findViewById(R.id.app_title_left);
        app_title_right = (ImageView) findViewById(R.id.app_title_right);
        app_title_right2 = (TextView) findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.GONE);
        app_title_center.setText("安全检查");
        app_title_left.setImageResource(R.drawable.back);
        app_title_left.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				finish();
			}
		});

        widget_1 = (LinearLayout) findViewById(R.id.widget_1);
        widget_2 = (LinearLayout)findViewById(R.id.widget_2);
        widget_3 = (LinearLayout)findViewById(R.id.widget_3);
        widget_4 = (LinearLayout)findViewById(R.id.widget_4);
        widget_5 = (LinearLayout)findViewById(R.id.widget_5);
        widget_6 = (LinearLayout)findViewById(R.id.widget_6);
        widget_7 = (LinearLayout)findViewById(R.id.widget_7);
        widget_8 = (LinearLayout)findViewById(R.id.widget_8);
        widget_9 = (LinearLayout)findViewById(R.id.widget_9);
        widget_10 = (LinearLayout)findViewById(R.id.widget_10);
        widget_11 = (LinearLayout)findViewById(R.id.widget_11);
        widget_12 = (LinearLayout)findViewById(R.id.widget_12);


        widget_11.getChildAt(0).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {

            }
        });

        UpdateInfo();
	}

    @Override
    protected void onStart() {
        super.onStart();
        UpdateInfo();
    }

	void UpdateInfo(){
        ((TextView)widget_1.getChildAt(1)).setText(_Cls.Str_2);
        ((TextView)widget_2.getChildAt(1)).setText(_Cls.Str_4);
        ((TextView)widget_3.getChildAt(1)).setText(_Cls.Cls_1.Str_1);
        widget_4.setBackgroundResource(R.drawable.itembg);
        Date BeginTime= Common.StrToDate(_Cls.Str_8,"yyyy-MM-dd");
        if (BeginTime==null) {
            BeginTime= Common.StrToDate("1900-01-01","yyyy-MM-dd");
            try {
                BeginTime.setTime(Long.valueOf(_Cls.Str_8) * 1000);
            } catch (Exception e1) {
                BeginTime = Common.StrToDate("1900-01-01", "yyyy-MM-dd");
            }
        }
        ((TextView)widget_4.getChildAt(1)).setText(Common.DateToStr(BeginTime,"yyyy-MM-dd").equals("1900-01-01")?"未计划": Common.DateToStr(BeginTime,"yyyy-MM-dd"));
        ((ImageView)widget_4.getChildAt(2)).setVisibility(View.INVISIBLE);
        widget_5.setBackgroundResource(R.drawable.itembg);
        Date EndTime= Common.StrToDate(_Cls.Str_9,"yyyy-MM-dd");
        if (EndTime==null) {
            EndTime= Common.StrToDate("1900-01-01","yyyy-MM-dd");
            try {
                EndTime.setTime(Long.valueOf(_Cls.Str_9) * 1000);
            } catch (Exception e1) {
                EndTime = Common.StrToDate("1900-01-01", "yyyy-MM-dd");
            }
        }
        ((TextView)widget_5.getChildAt(1)).setText(Common.DateToStr(EndTime,"yyyy-MM-dd").equals("1900-01-01")?"未计划": Common.DateToStr(EndTime,"yyyy-MM-dd"));
        ((ImageView)widget_5.getChildAt(2)).setVisibility(View.INVISIBLE);
        ((TextView)widget_6.getChildAt(1)).setText(String.valueOf(_Cls.Int_4)+"天");
        ((TextView)widget_7.getChildAt(1)).setText(_Cls.Str_10);
        ((TextView)widget_8.getChildAt(1)).setTag(_Cls.Str_13);
        ((TextView)widget_8.getChildAt(1)).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String ParmStr = "?token=" + Common.UrlEncoded(UserInfo.Token);
                ParmStr += "&id=" + _Cls.Int_1;
                ParmStr += "&type=0";
                new HttpCls2(ThisContext, HttpHandler, "down", 0, "正在获取...", Config.ServiceUrl + "index.php/api/progress/downloadAcc" + ParmStr, "Get", null, 10).Begin();
            }
        });
        ((TextView)widget_9.getChildAt(1)).setText(_Cls.Int_5+"%");
        String FZR="";
        for (int i=0;i<_Cls.ClsList_1.size();i++){
            if (!FZR.equals("")) FZR+="，";
            FZR+=_Cls.ClsList_1.get(i).Str_1;
        }
        ((TextView)widget_10.getChildAt(1)).setText(FZR);
        ((TextView)widget_12.getChildAt(1)).setText(Config.GetAJStatusName(_Cls.Int_9));
        ((TextView)widget_12.getChildAt(3)).setText("工程部检查");
    }
	
	@Override
	protected void onDestroy() {
		IsDestroy=true;
		super.onDestroy();
	}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState); //实现父类方法 放在最后 防止拍照后无法返回当前activity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
	
	@SuppressLint("HandlerLeak")
	Handler HttpHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if (IsDestroy || msg.obj == null) return;
			String FunName = msg.obj.toString();
			if (FunName.equals("down")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {
                        String url=_JSONObject.getString("content");

                        if (url==null || url.equals("")){
                            new MessageBox().Show(ThisContext, getString(R.string.Tip), "无文件", "", getString(R.string.OK));
                            return;
                        }
                        Common.WebOpen(ThisContext,url);

                    } else {
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), _JSONObject.getString("content"), "", getString(R.string.OK));
                    }
                    return;
                } catch (Exception e) { }
                new MessageBox().Show(ThisContext, getString(R.string.Tip), getString(R.string.NetErr), "", getString(R.string.OK));
            }
		}
	};
}
