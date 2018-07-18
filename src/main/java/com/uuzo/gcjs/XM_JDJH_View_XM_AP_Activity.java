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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.util.Date;

public class XM_JDJH_View_XM_AP_Activity extends Activity {

	Boolean IsDestroy=false;
	Context ThisContext;
	Activity ThisActivity;
	
	TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;

    TextView widget_0;
    LinearLayout widget_1,widget_2,widget_3;
	ClsClass.Cls _Cls;

	int UserID=0;
	String UserName="";
	String Tel="";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_xm_jdjh_view_xm_ap);
        Config.SetStatusBar(this);
	    
	    IsDestroy=false;
	    ThisContext=this;
	    ThisActivity=this;

        UserID=getIntent().getIntExtra("UserID",0);
        UserName=getIntent().getStringExtra("UserName");
        Tel=getIntent().getStringExtra("Tel");

	    if (Config.NowCls==null || UserID<=0)
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
        app_title_center.setText("安排任务");
        app_title_left.setImageResource(R.drawable.back);
        app_title_left.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				finish();
			}
		});

        widget_0 = (TextView) findViewById(R.id.widget_0);
        widget_1 = (LinearLayout)findViewById(R.id.widget_1);
        widget_2 = (LinearLayout)findViewById(R.id.widget_2);
        widget_3 = (LinearLayout)findViewById(R.id.widget_3);

        ((TextView)widget_3.getChildAt(1)).setText(UserName);

        widget_0.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String Value_1=((EditText)widget_1.getChildAt(1)).getText().toString().trim();
                String Tip_1=((EditText)widget_1.getChildAt(1)).getHint().toString().trim();
                String Value_2=widget_2.getTag()==null?"":widget_2.getTag().toString();
                String Tip_2=((TextView)widget_2.getChildAt(1)).getText().toString();
                if (Value_1.equals("")){
                    new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_1, "", getString(R.string.OK));
                    return;
                }

                String FileExt=Value_2.split("\\.")[Value_2.split("\\.").length-1].toLowerCase();

                Common.HideSoftInput(ThisActivity);

                String ParmStr="?token="+ Common.UrlEncoded(UserInfo.Token);
                ParmStr+="&type=1";
                ParmStr+="&id="+_Cls.Int_1;
                ParmStr+="&matters="+ Common.UrlEncoded(Value_1);
                ParmStr+="&principal="+UserID;
                if (Value_2.equals("")){
                    new HttpCls2(ThisContext, HttpHandler, "set", 0, "正在处理...", Config.ServiceUrl + "index.php/api/progress/toPlanpri" + ParmStr, "Get", null, 10).Begin();
                }
                else {
                    new UploadCls(ThisContext, HttpHandler, "set", 0, "正在处理...", Config.ServiceUrl + "index.php/api/progress/toPlanpri" + ParmStr, Common.DateToStr(new Date(), "yyyyMMddHHmmss")+"."+FileExt, new File(Value_2), 120, false, "file").Begin();
                }
            }
        });

        widget_2.getChildAt(1).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intentFromFile = new Intent();
                intentFromFile.setType("*/*"); // 设置文件类型
                intentFromFile.addCategory(Intent.CATEGORY_OPENABLE);
                intentFromFile.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intentFromFile,1);
            }
        });
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
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState); //实现父类方法 放在最后 防止拍照后无法返回当前activity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                    String FilePath= Common.getPath(ThisContext,data.getData());
                    String FileExt=FilePath.split("\\.")[FilePath.split("\\.").length-1].toLowerCase();
                    if (!FileExt.equals("zip") && !FileExt.equals("rar")){
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), "不支持此文件格式", "", getString(R.string.OK));
                        return;
                    }
                    widget_2.setTag(FilePath);
                    ((TextView)widget_2.getChildAt(1)).setText(FilePath.substring(FilePath.lastIndexOf("/")+1));
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
	
	@SuppressLint("HandlerLeak")
	Handler HttpHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if (IsDestroy || msg.obj == null) return;
			String FunName = msg.obj.toString();
			if (FunName.equals("set")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {

                        _Cls.Int_6=2;
                        _Cls.Str_12=((EditText)widget_1.getChildAt(1)).getText().toString().trim();

                        _Cls.ClsList_1.clear();
                        ClsClass.Cls _Cls3 = new ClsClass().new Cls();
                        _Cls3.Str_1 = UserName;
                        _Cls3.Str_2 = Tel;
                        _Cls.ClsList_1.add(_Cls3);


                        Common.DisplayToast("安排成功");
                        finish();

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
