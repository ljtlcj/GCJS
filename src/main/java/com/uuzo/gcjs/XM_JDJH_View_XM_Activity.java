package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.Date;

public class XM_JDJH_View_XM_Activity extends Activity {

	Boolean IsDestroy=false;
	Context ThisContext;
	Activity ThisActivity;
	
	TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;

    LinearLayout widget_1,widget_2,widget_3,widget_4,widget_5,widget_6,widget_7,widget_8,widget_9,widget_10,widget_11;
	ClsClass.Cls _Cls;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_xm_jdjh_view_xm);
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
	    
	    app_title_center =(TextView) findViewById(R.id.app_title_center);
        app_title_left = (ImageView) findViewById(R.id.app_title_left);
        app_title_right = (ImageView)findViewById(R.id.app_title_right);
        app_title_right2 = (TextView) findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.GONE);
        app_title_right2.setText("取消");
        app_title_center.setText("计划详情");
        app_title_left.setImageResource(R.drawable.back);
        app_title_left.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        app_title_right2.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                UpdateInfo();
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

        widget_4.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (((ImageView)widget_4.getChildAt(2)).getVisibility()!=View.VISIBLE) return;
                Date _Date= Common.StrToDate(((TextView)widget_4.getChildAt(1)).getText().toString(),"yyyy-MM-dd");
                if (_Date==null) _Date=new Date();
                new DatePickerDialog(ThisContext,new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        ((TextView)widget_4.getChildAt(1)).setText(String.valueOf(year)+"-"+ Common.StringFillZeroToBegin(String.valueOf(monthOfYear+1), 2)+"-"+ Common.StringFillZeroToBegin(String.valueOf(dayOfMonth), 2));


                        Date BeginDate= Common.StrToDate(((TextView)widget_4.getChildAt(1)).getText().toString(),"yyyy-MM-dd");
                        Date EndDate= Common.StrToDate(((TextView)widget_5.getChildAt(1)).getText().toString(),"yyyy-MM-dd");
                        if (BeginDate==null) BeginDate= Common.StrToDate("1900-01-01","yyyy-MM-dd");
                        if (EndDate==null) EndDate=BeginDate;
                        long WorkDay=((EndDate.getTime()-BeginDate.getTime())/1000/3600/24)+1;
                        ((TextView)widget_6.getChildAt(1)).setText((WorkDay<0?0:WorkDay)+"天");
                    }
                },Integer.valueOf(Common.DateToStr(_Date, "yyyy")), Integer.valueOf(Common.DateToStr(_Date, "MM"))-1, Integer.valueOf(Common.DateToStr(_Date, "dd"))).show();
            }
        });
        widget_5.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (((ImageView)widget_5.getChildAt(2)).getVisibility()!=View.VISIBLE) return;
                Date _Date= Common.StrToDate(((TextView)widget_5.getChildAt(1)).getText().toString(),"yyyy-MM-dd");
                if (_Date==null) _Date=new Date();
                new DatePickerDialog(ThisContext,new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        ((TextView)widget_5.getChildAt(1)).setText(String.valueOf(year)+"-"+ Common.StringFillZeroToBegin(String.valueOf(monthOfYear+1), 2)+"-"+ Common.StringFillZeroToBegin(String.valueOf(dayOfMonth), 2));

                        Date BeginDate= Common.StrToDate(((TextView)widget_4.getChildAt(1)).getText().toString(),"yyyy-MM-dd");
                        Date EndDate= Common.StrToDate(((TextView)widget_5.getChildAt(1)).getText().toString(),"yyyy-MM-dd");
                        if (EndDate==null) EndDate= Common.StrToDate("1900-01-01","yyyy-MM-dd");
                        if (BeginDate==null) BeginDate=EndDate;
                        long WorkDay=((EndDate.getTime()-BeginDate.getTime())/1000/3600/24)+1;
                        ((TextView)widget_6.getChildAt(1)).setText((WorkDay<0?0:WorkDay)+"天");
                    }
                },Integer.valueOf(Common.DateToStr(_Date, "yyyy")), Integer.valueOf(Common.DateToStr(_Date, "MM"))-1, Integer.valueOf(Common.DateToStr(_Date, "dd"))).show();
            }
        });
        widget_11.getChildAt(0).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                TextView _TextView=(TextView)v;
                if (_TextView.getText().toString().equals("编制计划")){
                    _TextView.setText("完成编制");
                    app_title_right2.setVisibility(View.VISIBLE);

                    widget_4.setBackgroundResource(R.drawable.itembg_gray);
                    widget_5.setBackgroundResource(R.drawable.itembg_gray);
                    ((ImageView)widget_4.getChildAt(2)).setVisibility(View.VISIBLE);
                    ((ImageView)widget_5.getChildAt(2)).setVisibility(View.VISIBLE);
                }
                else if (_TextView.getText().toString().equals("变更计划")){
                    _TextView.setText("完成变更");
                    app_title_right2.setVisibility(View.VISIBLE);

                    widget_4.setBackgroundResource(R.drawable.itembg_gray);
                    widget_5.setBackgroundResource(R.drawable.itembg_gray);
                    ((ImageView)widget_4.getChildAt(2)).setVisibility(View.VISIBLE);
                    ((ImageView)widget_5.getChildAt(2)).setVisibility(View.VISIBLE);
                }
                else if (_TextView.getText().toString().equals("完成编制") || _TextView.getText().toString().equals("完成变更")){
                    Date BeginDate= Common.StrToDate(((TextView)widget_4.getChildAt(1)).getText().toString(),"yyyy-MM-dd");
                    Date EndDate= Common.StrToDate(((TextView)widget_5.getChildAt(1)).getText().toString(),"yyyy-MM-dd");
                    if (BeginDate==null) BeginDate= Common.StrToDate("1900-01-01","yyyy-MM-dd");
                    if (EndDate==null) EndDate= Common.StrToDate("1900-01-01","yyyy-MM-dd");
                    if (Common.DateToStr(BeginDate,"yyyy-MM-dd").equals("1900-01-01")){
                        new MessageBox().Show(ThisContext, "提示", "请选择开始日期", "", getString(R.string.OK));
                        return;
                    }
                    if (Common.DateToStr(EndDate,"yyyy-MM-dd").equals("1900-01-01")){
                        new MessageBox().Show(ThisContext, "提示", "请选择截止日期", "", getString(R.string.OK));
                        return;
                    }
                    if (EndDate.getTime()<=BeginDate.getTime()){
                        new MessageBox().Show(ThisContext, "提示", "截止日期必须大于开始日期", "", getString(R.string.OK));
                        return;
                    }

                    String ParmStr = "?token=" + Common.UrlEncoded(UserInfo.Token);
                    ParmStr += "&type=1";
                    ParmStr += "&start_time=" + Common.UrlEncoded(Common.DateToStr(BeginDate,"yyyy-MM-dd"));
                    ParmStr += "&end_time=" + Common.UrlEncoded(Common.DateToStr(EndDate,"yyyy-MM-dd"));
                    ParmStr += "&work_day="+(((EndDate.getTime()-BeginDate.getTime())/1000/3600/24)+1);
                    ParmStr += "&note=";
                    ParmStr += "&id=" + _Cls.Int_1;
                    new HttpCls2(ThisContext, HttpHandler, "toPlan", 0, "正在处理...", Config.ServiceUrl + "index.php/api/progress/toPlan" + ParmStr, "Get", null, 10).Begin();
                }
            }
        });
        widget_11.getChildAt(1).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (_Cls.Int_6!=1){
                    new MessageBox().Show(ThisContext, "提示", "请先"+((TextView)widget_11.getChildAt(0)).getText().toString(), "", getString(R.string.OK));
                    return;
                }

                startActivity(new Intent(ThisContext, XM_JDJH_View_XM_SelectMember_Activity.class));
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

        app_title_right2.setVisibility(View.GONE);

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

        ((TextView)widget_11.getChildAt(0)).setText(_Cls.Int_6==0?"编制计划":"变更计划");
        ((TextView)widget_11.getChildAt(1)).setBackgroundResource(_Cls.Int_6==1? R.drawable.btnbg_blue: R.drawable.btnbg_gray);
    }
	
	@Override
	protected void onDestroy() {
		IsDestroy=true;
		super.onDestroy();


        Config.DeleteTempFile(ThisContext);
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
			if (FunName.equals("toPlan")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {

                        Common.DisplayToast(_Cls.Int_6==0?"编制成功":"变更成功");


                        Date BeginDate= Common.StrToDate(((TextView)widget_4.getChildAt(1)).getText().toString(),"yyyy-MM-dd");
                        Date EndDate= Common.StrToDate(((TextView)widget_5.getChildAt(1)).getText().toString(),"yyyy-MM-dd");


                        _Cls.Int_6=1;
                        _Cls.Str_8=String.valueOf(BeginDate.getTime()/1000);
                        _Cls.Str_9=String.valueOf(EndDate.getTime()/1000);
                        _Cls.Int_4=Long.valueOf(((EndDate.getTime()-BeginDate.getTime())/1000/3600/24)+1).intValue();

                        UpdateInfo();

                    } else {
                        new MessageBox().Show(ThisContext, getString(R.string.Tip), _JSONObject.getString("content"), "", getString(R.string.OK));
                    }
                    return;
                } catch (Exception e) { }
                new MessageBox().Show(ThisContext, getString(R.string.Tip), getString(R.string.NetErr), "", getString(R.string.OK));
            }
            else if (FunName.equals("down")) {
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
