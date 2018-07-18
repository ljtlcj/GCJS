package com.uuzo.gcjs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TYSQ_View_Activity extends Activity {

	Boolean IsDestroy=false;
	Context ThisContext;
	Activity ThisActivity;
	
	TextView app_title_center,app_title_right2;
    ImageView app_title_left,app_title_right;

    LinearLayout widget_1,widget_2,widget_5,widget_6,widget_8,widget_9,widget_10,widget_11,widget_12,widget_13,widget_14,widget_15,widget_16,widget_17,widget_18,widget_20,widget_22,widget_23;
    PullToRefreshListView widget_19,widget_21;
    ImageView widget_24;
	ClsClass.Cls _Cls;
	List<ClsClass.Cls> CJFList=new ArrayList<>();

	int NowIndex=0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_tysq_view);
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
        app_title_right = (ImageView)findViewById(R.id.app_title_right);
        app_title_right2 = (TextView) findViewById(R.id.app_title_right2);
        app_title_right.setVisibility(View.GONE);
        app_title_right2.setVisibility(View.VISIBLE);
        app_title_right2.setText("审批");
        app_title_center.setText("立项审批");
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
                widget_22.getChildAt(0).callOnClick();
            }
        });

        widget_1 = (LinearLayout) findViewById(R.id.widget_1);
        widget_2 = (LinearLayout) findViewById(R.id.widget_2);
        widget_5 = (LinearLayout) findViewById(R.id.widget_5);
        widget_6 = (LinearLayout) findViewById(R.id.widget_6);
        widget_8 = (LinearLayout) findViewById(R.id.widget_8);
        widget_9 = (LinearLayout) findViewById(R.id.widget_9);
        widget_10 = (LinearLayout) findViewById(R.id.widget_10);
        widget_11 = (LinearLayout) findViewById(R.id.widget_11);
        widget_12 = (LinearLayout) findViewById(R.id.widget_12);
        widget_13 = (LinearLayout) findViewById(R.id.widget_13);
        widget_14 = (LinearLayout) findViewById(R.id.widget_14);
        widget_15 = (LinearLayout) findViewById(R.id.widget_15);
        widget_16 = (LinearLayout) findViewById(R.id.widget_16);
        widget_17 = (LinearLayout) findViewById(R.id.widget_17);
        widget_18 = (LinearLayout) findViewById(R.id.widget_18);
        widget_19 = (PullToRefreshListView) findViewById(R.id.widget_19);
        widget_20 = (LinearLayout) findViewById(R.id.widget_20);
        widget_21 = (PullToRefreshListView) findViewById(R.id.widget_21);
        widget_22 = (PullToRefreshListView)findViewById(R.id.widget_22);
        widget_23 = (PullToRefreshListView)findViewById(R.id.widget_23);
        widget_24 = (ImageView)findViewById(R.id.widget_24);


        widget_22.getChildAt(0).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ThisContext)
                        .setItems(new String[]{"导出该立项申请表","审批通过","审批不通过"}, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                switch (which){
                                    case 0:
                                        try {
                                            if (_Cls.Str_10==null || _Cls.Str_10.equals("")){
                                                new MessageBox().Show(ThisContext, getString(R.string.Tip), "无文件", "", getString(R.string.OK));
                                                return;
                                            }
                                            Common.WebOpen(ThisContext, Config.ServiceUrl+_Cls.Str_10);
                                        }
                                        catch (Exception e1){}
                                        break;
                                    case 1:
                                        try {
                                            new MessageBox().Show(ThisContext, "提示", "确认审批通过？", getString(R.string.Cancel), getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    if (whichButton == -1) {
                                                        ClsClass.Cls _Cls2 = _Cls.ClsList_1.get(NowIndex);
                                                        String ParmStr = "?token=" + Common.UrlEncoded(UserInfo.Token);
                                                        ParmStr += "&item_id=" + _Cls2.Int_1;
                                                        ParmStr += "&type=1";
                                                        new HttpCls2(ThisContext, HttpHandler, "check1", 0, "正在处理...", Config.ServiceUrl + "index.php/api/apply/passItem" + ParmStr, "Get", null, 10).Begin();
                                                    }
                                                }
                                            };
                                        }
                                        catch (Exception e1){}
                                        break;
                                    case 2:
                                        try {
                                            new MessageBox().Show(ThisContext, "提示", "确认审批不通过？", getString(R.string.Cancel), getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                    if (whichButton == -1) {
                                                        ClsClass.Cls _Cls2 = _Cls.ClsList_1.get(NowIndex);
                                                        String ParmStr = "?token=" + Common.UrlEncoded(UserInfo.Token);
                                                        ParmStr += "&item_id=" + _Cls2.Int_1;
                                                        ParmStr += "&type=2";
                                                        new HttpCls2(ThisContext, HttpHandler, "check2", 0, "正在处理...", Config.ServiceUrl + "index.php/api/apply/passItem" + ParmStr, "Get", null, 10).Begin();
                                                    }
                                                }
                                            };
                                        }
                                        catch (Exception e1){}
                                        break;
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        List<ClsClass.Cls> TempList=new ArrayList<>();
        for (int i=0;i<_Cls.ClsList_2.size();i++){
            ClsClass.Cls _Cls3=_Cls.ClsList_2.get(i);

            ClsClass.Cls _Cls4=new ClsClass().new Cls();
            _Cls4.Int_1=_Cls3.Int_3;
            _Cls4.Str_1= Config.GetCJFTypeName(_Cls3.Int_3);

            int _Index=-1;
            for (int j=0;j<TempList.size();j++){
                if (TempList.get(j).Int_1==_Cls3.Int_3){
                    _Index=j;
                    break;
                }
            }
            if (_Index==-1){
                _Cls4.ClsList_1.add(_Cls3);
                TempList.add(_Cls4);
            }
            else{
                _Cls4=TempList.get(_Index);
                _Cls4.ClsList_1.add(_Cls3);
            }
        }

        CJFList.clear();
        for (int i=0;i<TempList.size();i++){
            ClsClass.Cls _Cls3=TempList.get(i);
            for (int j=0;j<_Cls3.ClsList_1.size();j++){
                ClsClass.Cls _Cls4=_Cls3.ClsList_1.get(j);
                if (j==0) _Cls4.Str_10=_Cls3.Str_1;
                CJFList.add(_Cls4);
            }
        }

        widget_21.setAdapter(new MyListAdapter2());
        widget_21.setMode(PullToRefreshBase.Mode.DISABLED);
        widget_21.getAndSetTotalHeight(0);

        UpdateInfo();
	}

    @Override
    protected void onStart() {
        super.onStart();
        UpdateInfo();
    }

	void UpdateInfo(){

	    if (NowIndex>=_Cls.ClsList_1.size()) NowIndex=0;

        ((TextView)widget_1.getChildAt(1)).setText(_Cls.Str_1);
        ((TextView)widget_2.getChildAt(1)).setText(Config.GetProjectTypeName(_Cls.Int_2));
        ((TextView)widget_5.getChildAt(1)).setText(_Cls.Str_4);
        ((TextView)widget_6.getChildAt(1)).setText(_Cls.Str_9);

        ClsClass.Cls _Cls2=_Cls.ClsList_1.get(NowIndex);
        ((TextView)widget_8.getChildAt(1)).setText(_Cls2.Str_3);
        ((TextView)widget_9.getChildAt(1)).setText(_Cls2.Str_2);
        ((TextView)widget_10.getChildAt(1)).setText(_Cls2.Str_4);
        ((TextView)widget_11.getChildAt(1)).setText(_Cls2.Str_6);
        ((TextView)widget_12.getChildAt(1)).setText(_Cls2.Str_7);
        for (int i=1;i<widget_13.getChildCount();i++){
            ((ImageView)widget_13.getChildAt(i)).setVisibility(View.INVISIBLE);
        }
        for (int i=0;i<_Cls2.Int_3;i++){
            if (i+1>=widget_13.getChildCount()) break;
            ((ImageView)widget_13.getChildAt(i+1)).setVisibility(View.VISIBLE);
        }

        Date BeginTime= Common.StrToDate(_Cls2.Str_8,"yyyy-MM-dd");
        if (BeginTime==null) {
            BeginTime= Common.StrToDate("1900-01-01","yyyy-MM-dd");
            try {
                BeginTime.setTime(Long.valueOf(_Cls.Str_8) * 1000);
            } catch (Exception e1) {
                BeginTime = Common.StrToDate("1900-01-01", "yyyy-MM-dd");
            }
        }
        ((TextView)widget_14.getChildAt(1)).setText(Common.DateToStr(BeginTime,"yyyy-MM-dd").equals("1900-01-01")?"未计划": Common.DateToStr(BeginTime,"yyyy-MM-dd"));

        Date EndTime= Common.StrToDate(_Cls2.Str_9,"yyyy-MM-dd");
        if (EndTime==null) {
            EndTime= Common.StrToDate("1900-01-01","yyyy-MM-dd");
            try {
                EndTime.setTime(Long.valueOf(_Cls.Str_9) * 1000);
            } catch (Exception e1) {
                EndTime = Common.StrToDate("1900-01-01", "yyyy-MM-dd");
            }
        }
        ((TextView)widget_15.getChildAt(1)).setText(Common.DateToStr(EndTime,"yyyy-MM-dd").equals("1900-01-01")?"未计划": Common.DateToStr(EndTime,"yyyy-MM-dd"));
        ((TextView)widget_16.getChildAt(1)).setText(_Cls2.Int_4+"天");
        ((TextView)widget_17.getChildAt(1)).setText(_Cls2.Str_10);

        widget_19.setAdapter(new MyListAdapter(_Cls2.ClsList_2));
        widget_19.setMode(PullToRefreshBase.Mode.DISABLED);
        widget_19.getAndSetTotalHeight(0);

        ((TextView)widget_23.getChildAt(1)).setText(_Cls2.Str_1);

        if (Config.GetTYSQTypeName(_Cls.Int_15).equals("未审批")) {
            widget_24.setImageResource(R.drawable.sp_1);
        }
        else if (Config.GetTYSQTypeName(_Cls.Int_15).equals("审批通过")) {
            widget_24.setImageResource(R.drawable.sp_2);
        }
        else if (Config.GetTYSQTypeName(_Cls.Int_15).equals("审批不通过")) {
            widget_24.setImageResource(R.drawable.sp_3);
        }
        else{
            widget_24.setVisibility(View.GONE);
        }


        app_title_right2.setVisibility(Config.GetTYSQTypeName(_Cls.Int_15).equals("未审批")?View.VISIBLE:View.GONE);
        widget_22.setVisibility(Config.GetTYSQTypeName(_Cls.Int_15).equals("未审批")?View.VISIBLE:View.GONE);
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
            if (FunName.equals("check1")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {

                        new MessageBox().Show(ThisContext, getString(R.string.Tip), "审批成功", "", getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (whichButton == -1) {
                                    _Cls.Int_15=1;
                                    UpdateInfo();
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
            else if (FunName.equals("check2")) {
                try {
                    JSONObject _JSONObject = new JSONObject(msg.getData().getString("ReturnValue"));
                    if (_JSONObject.getBoolean("status")) {

                        new MessageBox().Show(ThisContext, getString(R.string.Tip), "审批成功", "", getString(R.string.OK)).BtnClick = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (whichButton == -1) {
                                    _Cls.Int_15=2;
                                    UpdateInfo();
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


    class ViewHolder
    {
        public LinearLayout item_widget_1,item_widget_2;
        public PullToRefreshListView item_widget_3;
    }

    class MyListAdapter extends BaseAdapter {
        LayoutInflater mInflater;
        List<ClsClass.Cls> ItemList=new ArrayList<ClsClass.Cls>();

        MyListAdapter(List<ClsClass.Cls> _ItemList) {
            mInflater = LayoutInflater.from(ThisContext);
            ItemList=_ItemList;
        }

        @Override
        public int getCount() { return ItemList.size(); }

        @Override
        public Object getItem(int arg0) { return ItemList.get(arg0); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.item_xm_view, null);
                holder = new ViewHolder();
                holder.item_widget_1 = (LinearLayout) convertView.findViewById(R.id.item_widget_1);
                holder.item_widget_2 = (LinearLayout)convertView.findViewById(R.id.item_widget_2);
                holder.item_widget_3 = (PullToRefreshListView) convertView.findViewById(R.id.item_widget_3);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            ClsClass.Cls _Cls3 = ItemList.get(position);

            ((ImageView) holder.item_widget_1.getChildAt(0)).setVisibility(View.INVISIBLE);
            ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls3.Str_1);
            ((ImageView) holder.item_widget_2.getChildAt(0)).setVisibility(View.INVISIBLE);
            ((TextView) holder.item_widget_2.getChildAt(1)).setText("类型："+_Cls3.Str_3);
            ((TextView) holder.item_widget_2.getChildAt(3)).setText("工作量："+String.valueOf(_Cls3.Int_4)+_Cls3.Str_4);
            ((ImageView) holder.item_widget_2.getChildAt(5)).setVisibility(View.GONE);
            holder.item_widget_3.setVisibility(View.GONE);

            return convertView;
        }
    }

    class ViewHolder2
    {
        public LinearLayout item_widget_1,item_widget_2,item_widget_3,item_widget_4,item_widget_5;
        public TextView item_widget_0;
    }

    class MyListAdapter2 extends BaseAdapter {
        LayoutInflater mInflater;

        MyListAdapter2() {
            mInflater = LayoutInflater.from(ThisContext);
        }

        @Override
        public int getCount() { return CJFList.size(); }

        @Override
        public Object getItem(int arg0) { return CJFList.get(arg0); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder2 holder;
            if (convertView == null)
            {
                convertView = mInflater.inflate(R.layout.item_cjf, null);
                holder = new ViewHolder2();
                holder.item_widget_0 = (TextView) convertView.findViewById(R.id.item_widget_0);
                holder.item_widget_1 = (LinearLayout)convertView.findViewById(R.id.item_widget_1);
                holder.item_widget_2 = (LinearLayout)convertView.findViewById(R.id.item_widget_2);
                holder.item_widget_3 = (LinearLayout)convertView.findViewById(R.id.item_widget_3);
                holder.item_widget_4 = (LinearLayout)convertView.findViewById(R.id.item_widget_4);
                holder.item_widget_5 = (LinearLayout)convertView.findViewById(R.id.item_widget_5);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder2)convertView.getTag();
            }
            ClsClass.Cls _Cls3 = CJFList.get(position);

            holder.item_widget_0.setVisibility(_Cls3.Str_10.equals("")?View.GONE:View.VISIBLE);
            holder.item_widget_0.setText(_Cls3.Str_10);
            ((TextView) holder.item_widget_1.getChildAt(1)).setText(_Cls3.Str_1);
            ((TextView) holder.item_widget_1.getChildAt(3)).setText(_Cls3.Str_2);
            ((TextView) holder.item_widget_2.getChildAt(1)).setText(_Cls3.Str_3);
            ((TextView) holder.item_widget_2.getChildAt(3)).setText(_Cls3.Str_4);
            ((TextView) holder.item_widget_3.getChildAt(1)).setText(_Cls3.Str_5);
            ((TextView) holder.item_widget_4.getChildAt(1)).setText(_Cls3.Str_6);
            ((TextView) holder.item_widget_5.getChildAt(1)).setText(_Cls3.Str_7);

            return convertView;
        }
    }
}
