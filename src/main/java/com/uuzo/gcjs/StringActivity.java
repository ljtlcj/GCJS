package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;


public class StringActivity extends Activity {

	Boolean IsDestroy=false;
	Context ThisContext;
	Activity ThisActivity;
	
	TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;


    LinearLayout widget_1;
	
	int Type=0,Int=0;
	String Value="";
	String Str="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_string);
		Config.SetStatusBar(this);

	    
	    IsDestroy=false;
	    ThisContext=this;
	    ThisActivity=this;

	    Type=getIntent().getIntExtra("Type", 0);
	    Value=getIntent().getStringExtra("Value");
	    Int=getIntent().getIntExtra("Int", 0);
        Str=getIntent().hasExtra("Str")?getIntent().getStringExtra("Str"):"";

	    if (Type==0)
		{
	    	finish();
			return;
		}
	    
	    app_title_center = (TextView)findViewById(R.id.app_title_center);
        app_title_left = (ImageView)findViewById(R.id.app_title_left);
        app_title_right = (ImageView)findViewById(R.id.app_title_right);
        app_title_right2 = (TextView)findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.GONE);
        if (Type==1) app_title_center.setText("绑定身份证");
        app_title_left.setImageResource(R.drawable.back);
        app_title_left.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				finish();
			}
		});

        widget_1 = (LinearLayout)findViewById(R.id.widget_1);

        ((TextView)widget_1.getChildAt(2)).setText("确定");
		if (Type==1)
		{
            ((EditText)widget_1.getChildAt(0)).setText(Value);
            ((EditText)widget_1.getChildAt(0)).setHint("请输入身份证号码");
            ((EditText)widget_1.getChildAt(0)).setInputType(InputType.TYPE_CLASS_TEXT);
			InputFilter[] filters = { new InputFilter.LengthFilter(18) };
            ((EditText)widget_1.getChildAt(0)).setFilters(filters);
            ((TextView)widget_1.getChildAt(1)).setVisibility(View.GONE);
		}
		else
		{
			finish();
			return;
		}

        ((TextView)widget_1.getChildAt(2)).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Value=((EditText)widget_1.getChildAt(0)).getText().toString().trim();

                Common.HideSoftInput(ThisActivity);

                if (Value.equals("")){
                    new MessageBox().Show(ThisContext, "提示", ((EditText)widget_1.getChildAt(0)).getHint().toString(), "", getString(R.string.OK));
                    return;
                }

				if (Type==1)
				{
                    String ParmStr="?id_card="+ Common.UrlEncoded(Value);
                    ParmStr+="&wopen_id="+ Common.UrlEncoded(Str);
					new HttpCls2(ThisContext,HttpHandler,"bd",0,"正在设置...", Config.ServiceUrl+"index.php/api/login/bind_id_card1"+ParmStr,"Get",null,10).Begin();
				}
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		IsDestroy=true;
		super.onDestroy();
	}
	
	@SuppressLint("HandlerLeak")
	Handler HttpHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if (IsDestroy || msg.obj == null) return;
			String FunName = msg.obj.toString();
			if (FunName.equals("bd")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {
                        String ParmStr="?wopen_id="+ Common.UrlEncoded(Str);
                        new HttpCls2(ThisContext,HttpHandler,"wxlogin",0,"正在登录...", Config.ServiceUrl+"index.php/api/login/judge_weixin_login"+ParmStr,"Get",null,10).Begin();
                    } else {
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), _JSONObject.getString("content"), "", getString(R.string.OK));
                    }
                    return;
                } catch (Exception e) { }
                new MessageBox().Show(ThisContext, getString(R.string.Tip), getString(R.string.NetErr), "", getString(R.string.OK));
            }
            else if (FunName.equals("wxlogin")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {
                        UserInfo.Login(ThisContext,_JSONObject.getJSONObject("content"));
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
