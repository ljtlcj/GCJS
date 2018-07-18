package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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

import java.io.File;
import java.util.Date;

public class XM_Add_Upload_Activity extends Activity {

	Boolean IsDestroy=false;
	Context ThisContext;
	Activity ThisActivity;
	
	TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;

    TextView widget_0;
    LinearLayout widget_1,widget_2;
	ClsClass.Cls _Cls;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_xm_add_upload);
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
        app_title_right2 = (TextView)findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.GONE);
        app_title_center.setText("项目资料");
        app_title_left.setImageResource(R.drawable.back);
        app_title_left.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				finish();
			}
		});

        widget_0 = (TextView)findViewById(R.id.widget_0);
        widget_1 = (LinearLayout) findViewById(R.id.widget_1);
        widget_2 = (LinearLayout) findViewById(R.id.widget_2);

        ((TextView)widget_1.getChildAt(0)).setText(_Cls.Str_1);

        widget_0.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String Value_2=widget_2.getTag()==null?"":widget_2.getTag().toString();
                String Tip_2=((TextView)widget_2.getChildAt(1)).getText().toString();
                if (Value_2.equals("")){
                    new MessageBox().Show(ThisContext, getString(R.string.Tip), Tip_2, "", getString(R.string.OK));
                    return;
                }

                String FileExt=Value_2.split("\\.")[Value_2.split("\\.").length-1].toLowerCase();

                Common.HideSoftInput(ThisActivity);

                String ParmStr="?token="+ Common.UrlEncoded(UserInfo.Token);
                ParmStr+="&project_id="+_Cls.Int_1;
                new UploadCls(ThisContext, HttpHandler, "up", 0, "正在上传...", Config.ServiceUrl + "index.php/api/project/uploadProjectData" + ParmStr, Common.DateToStr(new Date(), "yyyyMMddHHmmss")+"."+FileExt, new File(Value_2), 120, false, "file").Begin();
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
			if (FunName.equals("up")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {
                        String ParmStr="?token="+ Common.UrlEncoded(UserInfo.Token);
                        ParmStr+="&project_id="+_Cls.Int_1;
                        new HttpCls2(ThisContext,HttpHandler,"set",0,"正在提交...", Config.ServiceUrl+"index.php/api/project/sure"+ParmStr,"Get",null,10).Begin();
                    } else {
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), _JSONObject.getString("content"), "", getString(R.string.OK));
                    }
                    return;
                } catch (Exception e) { }
                new MessageBox().Show(ThisContext, getString(R.string.Tip), getString(R.string.NetErr), "", getString(R.string.OK));
            }
            else if (FunName.equals("set")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {

                        new MessageBox().Show(ThisContext, getString(R.string.Tip), "提交成功，等待审核", "", getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (whichButton == -1) {
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        };
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
