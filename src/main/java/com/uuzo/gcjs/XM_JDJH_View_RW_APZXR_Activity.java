package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.io.File;
import java.util.Date;

public class XM_JDJH_View_RW_APZXR_Activity extends Activity {

	Boolean IsDestroy=false;
	Context ThisContext;
	Activity ThisActivity;
	
	TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;

    TextView widget_0;
    LinearLayout widget_1,widget_2,widget_4,widget_5;
	ClsClass.Cls _Cls;


    PullToRefreshListView _ListView;
    MyListAdapter _MyListAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_xm_jdjh_view_rw_apzxr);
        Config.SetStatusBar(this);
	    
	    IsDestroy=false;
	    ThisContext=this;
	    ThisActivity=this;



	    if (Config.NowCls==null || Config.NowList.size()==0) {
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
        app_title_center.setText("安排任务");
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
        _ListView = (PullToRefreshListView) findViewById(R.id.widget_3);
        widget_4 = (LinearLayout) findViewById(R.id.widget_4);
        widget_5 = (LinearLayout)findViewById(R.id.widget_5);


        ((TextView)widget_1.getChildAt(1)).setText(_Cls.Str_1);
        ((TextView)widget_2.getChildAt(1)).setText(_Cls.Int_4+" "+_Cls.Str_4);

        int FPWorkLoad=0;
        for (int i = 0; i< Config.NowList.size(); i++){
            FPWorkLoad+= Config.NowList.get(i).Int;
        }
        ((TextView)widget_2.getChildAt(3)).setText(FPWorkLoad+" "+_Cls.Str_4);

        _MyListAdapter = new MyListAdapter();
        _ListView.setAdapter(_MyListAdapter);
        _ListView.setMode(PullToRefreshBase.Mode.DISABLED);

        widget_0.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String Value_1=((EditText)widget_4.getChildAt(1)).getText().toString().trim();
                String Tip_1=((EditText)widget_4.getChildAt(1)).getHint().toString().trim();
                String Value_2=widget_5.getTag()==null?"":widget_5.getTag().toString();
                String Tip_2=((TextView)widget_5.getChildAt(1)).getText().toString();

                String ContentStr="";
                int FPWorkLoad=0;
                for (int i = 0; i< Config.NowList.size(); i++){
                    ClsClass.Cls _Cls2= Config.NowList.get(i);
                    String WorkLoad= _Cls2.Str_9;
                    Date BeginDate= Common.StrToDate(_Cls2.Str_7,"yyyy-MM-dd");
                    Date EndDate= Common.StrToDate(_Cls2.Str_8,"yyyy-MM-dd");
                    if (BeginDate==null) BeginDate= Common.StrToDate("1900-01-01","yyyy-MM-dd");
                    if (EndDate==null) EndDate= Common.StrToDate("1900-01-01","yyyy-MM-dd");

                    if (WorkLoad.equals("")){
                        new MessageBox().Show(ThisContext, "提示", "请分配工作量给"+_Cls2.Str_3, "", getString(R.string.OK));
                        return;
                    }
                    if (Common.DateToStr(BeginDate,"yyyy-MM-dd").equals("1900-01-01") || Common.DateToStr(EndDate,"yyyy-MM-dd").equals("1900-01-01")){
                        new MessageBox().Show(ThisContext, "提示", "请分配时间给"+_Cls2.Str_3, "", getString(R.string.OK));
                        return;
                    }
                    if (EndDate.getTime()<=BeginDate.getTime()){
                        new MessageBox().Show(ThisContext, "提示", "分配给"+_Cls2.Str_3+"的截止日期必须大于开始日期", "", getString(R.string.OK));
                        return;
                    }


                    if (!ContentStr.equals("")) ContentStr+="||";
                    ContentStr+=_Cls.Int_1+","+_Cls2.Int_1+","+WorkLoad+","+ Common.DateToStr(BeginDate,"yyyy-MM-dd")+","+ Common.DateToStr(EndDate,"yyyy-MM-dd");


                    FPWorkLoad+= Common.isInteger(_Cls2.Str_9)?Integer.valueOf(_Cls2.Str_9):0;
                }
                if (ContentStr.equals("")){
                    new MessageBox().Show(ThisContext, "提示", "请分配工作给执行人", "", getString(R.string.OK));
                    return;
                }

                if (FPWorkLoad>_Cls.Int_4){
                    new MessageBox().Show(ThisContext, "提示", "分配的工作量超过总工作量", "", getString(R.string.OK));
                    return;
                }
                if (FPWorkLoad<_Cls.Int_4){
                    new MessageBox().Show(ThisContext, "提示", "分配的工作量不足总工作量", "", getString(R.string.OK));
                    return;
                }

                String FileExt=Value_2.split("\\.")[Value_2.split("\\.").length-1].toLowerCase();

                Common.HideSoftInput(ThisActivity);

                String ParmStr="?token="+ Common.UrlEncoded(UserInfo.Token);
                ParmStr+="&matters="+ Common.UrlEncoded(Value_1);
                ParmStr+="&arr="+ Common.UrlEncoded(ContentStr);
                if (Value_2.equals("")){
                    new HttpCls2(ThisContext, HttpHandler, "set", 0, "正在处理...", Config.ServiceUrl + "index.php/api/progress/toPlanExecutor" + ParmStr, "Get", null, 10).Begin();
                }
                else {
                    new UploadCls(ThisContext, HttpHandler, "set", 0, "正在处理...", Config.ServiceUrl + "index.php/api/progress/toPlanExecutor" + ParmStr, Common.DateToStr(new Date(), "yyyyMMddHHmmss")+"."+FileExt, new File(Value_2), 120, false, "file").Begin();
                }
            }
        });

        widget_5.getChildAt(1).setOnClickListener(new OnClickListener()
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
        _MyListAdapter.notifyDataSetChanged();
        _ListView.getAndSetTotalHeight(0);
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
                    widget_5.setTag(FilePath);
                    ((TextView)widget_5.getChildAt(1)).setText(FilePath.substring(FilePath.lastIndexOf("/")+1));
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

                        _Cls.ClsList_1.clear();
                        for (int i = 0; i< Config.NowList.size(); i++) {
                            ClsClass.Cls _Cls2 = Config.NowList.get(i);
                            ClsClass.Cls _Cls3 = new ClsClass().new Cls();
                            _Cls3.Str_1 = _Cls2.Str_3;
                            _Cls3.Str_2 = _Cls2.Str_5;
                            _Cls.ClsList_1.add(_Cls3);
                        }

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

    class ViewHolder
    {
        public LinearLayout item_widget_1,item_widget_2,item_widget_3;
    }

    class MyListAdapter extends BaseAdapter {
        LayoutInflater mInflater;

        MyListAdapter() { mInflater = LayoutInflater.from(ThisContext); }

        @Override
        public int getCount() { return Config.NowList.size(); }

        @Override
        public Object getItem(int arg0) { return Config.NowList.get(arg0); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.item_zxr, null);
                holder = new ViewHolder();
                holder.item_widget_1 = (LinearLayout)convertView.findViewById(R.id.item_widget_1);
                holder.item_widget_2 = (LinearLayout)convertView.findViewById(R.id.item_widget_2);
                holder.item_widget_3 = (LinearLayout)convertView.findViewById(R.id.item_widget_3);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            ClsClass.Cls _Cls2 = Config.NowList.get(position);
            ((TextView)holder.item_widget_1.getChildAt(1)).setText(_Cls2.Str_3);
            ((TextView)holder.item_widget_1.getChildAt(3)).setText(_Cls2.Str_2);
            ((EditText)holder.item_widget_2.getChildAt(1)).addTextChangedListener(new MyTextWatcher(_Cls2));
            ((TextView)holder.item_widget_2.getChildAt(2)).setText(_Cls.Str_4);
            ((TextView)holder.item_widget_3.getChildAt(1)).setTag(_Cls2);
            ((TextView)holder.item_widget_3.getChildAt(1)).setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    final TextView _TextView=(TextView)v;
                    final ClsClass.Cls _Cls2=(ClsClass.Cls)_TextView.getTag();
                    Date _Date= Common.StrToDate(_TextView.getText().toString(),"yyyy-MM-dd");
                    if (_Date==null) _Date=new Date();
                    new DatePickerDialog(ThisContext,new DatePickerDialog.OnDateSetListener()
                    {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                        {
                            _Cls2.Str_7=String.valueOf(year)+"-"+ Common.StringFillZeroToBegin(String.valueOf(monthOfYear+1), 2)+"-"+ Common.StringFillZeroToBegin(String.valueOf(dayOfMonth), 2);
                            _TextView.setText(_Cls2.Str_7);
                        }
                    },Integer.valueOf(Common.DateToStr(_Date, "yyyy")), Integer.valueOf(Common.DateToStr(_Date, "MM"))-1, Integer.valueOf(Common.DateToStr(_Date, "dd"))).show();
                }
            });
            ((TextView)holder.item_widget_3.getChildAt(3)).setTag(_Cls2);
            ((TextView)holder.item_widget_3.getChildAt(3)).setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    final TextView _TextView=(TextView)v;
                    final ClsClass.Cls _Cls2=(ClsClass.Cls)_TextView.getTag();
                    Date _Date= Common.StrToDate(_TextView.getText().toString(),"yyyy-MM-dd");
                    if (_Date==null) _Date=new Date();
                    new DatePickerDialog(ThisContext,new DatePickerDialog.OnDateSetListener()
                    {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                        {
                            _Cls2.Str_8=String.valueOf(year)+"-"+ Common.StringFillZeroToBegin(String.valueOf(monthOfYear+1), 2)+"-"+ Common.StringFillZeroToBegin(String.valueOf(dayOfMonth), 2);
                            _TextView.setText(_Cls2.Str_8);
                        }
                    },Integer.valueOf(Common.DateToStr(_Date, "yyyy")), Integer.valueOf(Common.DateToStr(_Date, "MM"))-1, Integer.valueOf(Common.DateToStr(_Date, "dd"))).show();
                }
            });
            return convertView;
        }
    }


    class MyTextWatcher implements TextWatcher {
        public MyTextWatcher(ClsClass.Cls _cls2) {
            _Cls2 = _cls2;
        }

        ClsClass.Cls _Cls2;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            _Cls2.Str_9=s.toString().trim();
            UpdateWorkLoad();
        }
    };

    void UpdateWorkLoad(){
        int FPWorkLoad=0;
        for (int i = 0; i< Config.NowList.size(); i++){
            ClsClass.Cls _Cls2= Config.NowList.get(i);

            FPWorkLoad+= Common.isInteger(_Cls2.Str_9)?Integer.valueOf(_Cls2.Str_9):0;
        }
        ((TextView)widget_2.getChildAt(3)).setText(FPWorkLoad+" "+_Cls.Str_4);
    }
}
